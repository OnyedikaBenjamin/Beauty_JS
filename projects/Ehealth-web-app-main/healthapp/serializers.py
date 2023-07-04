from .models import Appointment, MedicalRecord, Patient, CustomUser, Doctor, Payment
from rest_framework import serializers


class CustomUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = CustomUser
        fields = ('id', 'first_name', 'last_name', 'email', 'username', 'password')
        extra_kwargs = {
            'password': {'write_only': True},
        }

    def create(self, validated_data):
        user = CustomUser.objects.create_user(
            first_name=validated_data['first_name'],
            last_name=validated_data['last_name'],
            email=validated_data['email'],
            username=validated_data['username'],
            password=validated_data['password'],
        )
        return user

    def validate(self, data):
        # Check that the email and password are valid
        user = CustomUser.objects.filter(email=data['email']).first()
        if user and user.check_password(data['password']):
            return data
        raise serializers.ValidationError('Invalid email or password')


class AppointmentSerializer(serializers.ModelSerializer):
    patient = serializers.StringRelatedField()
    doctor = serializers.StringRelatedField()

    class Meta:
        model = Appointment
        fields = '__all__'


class PatientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Patient
        fields = '__all__'


class DoctorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Doctor
        fields = '__all__'


class MedicalRecordSerializer(serializers.ModelSerializer):
    patient = PatientSerializer(read_only=True)
    doctor = DoctorSerializer(read_only=True)

    class Meta:
        model = MedicalRecord
        fields = '__all__'


class PaymentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Payment
        fields = '__all__'
