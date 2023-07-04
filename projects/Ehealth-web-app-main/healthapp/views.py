from rest_framework import permissions
import paystackapi
from rest_framework.generics import get_object_or_404

from rest_framework_simplejwt.authentication import JWTAuthentication
from .models import CustomUser, MedicalRecord, Doctor, Patient, Appointment, Payment
from .serializers import CustomUserSerializer, MedicalRecordSerializer, DoctorSerializer, PatientSerializer, \
    PaymentSerializer
from rest_framework.permissions import IsAuthenticated
from .serializers import AppointmentSerializer
from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
import paystack


class PaymentView(APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request, pk=None):
        if pk:
            payment = get_object_or_404(Payment, pk=pk)
            serializer = PaymentSerializer(payment)
            return Response(serializer.data)
        else:
            payments = Payment.objects.filter(patient=request.user.customuser)
            serializer = PaymentSerializer(payments, many=True)
            return Response(serializer.data)

    def post(self, request):
        serializer = PaymentSerializer(data=request.data)
        if serializer.is_valid():
            amount = serializer.validated_data['amount']
            email = serializer.validated_data['email']
            reference = serializer.validated_data['reference']
            method = serializer.validated_data['method']
            status = serializer.validated_data['status']

            # Call Paystack API to verify transaction
            response = paystack.verify_transaction(reference)

            # Verify the transaction status
            if response['status'] == 'success' and response['data']['amount'] == amount*100:
                # Create a Payment object
                payment = Payment.objects.create(
                    patient=request.user.customuser,
                    amount=amount,
                    method=method,
                    status=status
                )
                serializer = PaymentSerializer(payment)
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                return Response({'error': 'Payment verification failed.'}, status=status.HTTP_400_BAD_REQUEST)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class CustomUserList(generics.ListCreateAPIView):
    queryset = CustomUser.objects.all()
    serializer_class = CustomUserSerializer
    authentication_classes = [JWTAuthentication]
    permission_classes = [permissions.IsAdminUser]


class CustomUserDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = CustomUser.objects.all()
    serializer_class = CustomUserSerializer
    authentication_classes = [JWTAuthentication]
    permission_classes = [permissions.IsAdminUser]


class AppointmentList(generics.ListCreateAPIView):
    permission_classes = [IsAuthenticated]
    queryset = Appointment.objects.all()
    serializer_class = AppointmentSerializer


class AppointmentDetail(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = [IsAuthenticated]
    queryset = Appointment.objects.all()
    serializer_class = AppointmentSerializer


class PatientListCreateView(generics.ListCreateAPIView):
    permission_classes = [IsAuthenticated]
    queryset = Patient.objects.all()
    serializer_class = PatientSerializer


class PatientRetrieveUpdateDestroyView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = [IsAuthenticated]
    queryset = Patient.objects.all()
    serializer_class = PatientSerializer


class DoctorList(generics.ListCreateAPIView):
    queryset = Doctor.objects.all()
    serializer_class = DoctorSerializer
    permission_classes = [IsAuthenticated]


class DoctorDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Doctor.objects.all()
    serializer_class = DoctorSerializer
    permission_classes = [IsAuthenticated]


class MedicalRecordList(generics.ListCreateAPIView):
    queryset = MedicalRecord.objects.all()
    serializer_class = MedicalRecordSerializer
    permission_classes = [IsAuthenticated]


class MedicalRecordDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = MedicalRecord.objects.all()
    serializer_class = MedicalRecordSerializer
    permission_classes = [IsAuthenticated]


class PaymentUpdate(generics.UpdateAPIView):
    queryset = Payment.objects.all()
    serializer_class = PaymentSerializer
    permission_classes = [IsAuthenticated]


class PaymentDelete(generics.DestroyAPIView):
    queryset = Payment.objects.all()
    serializer_class = PaymentSerializer
    permission_classes = [IsAuthenticated]


class PaymentVerify(generics.UpdateAPIView):
    queryset = Payment.objects.all()
    serializer_class = PaymentSerializer
    permission_classes = [IsAuthenticated]
