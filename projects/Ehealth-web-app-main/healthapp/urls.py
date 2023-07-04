from django.urls import path
from .views import CustomUserDetail, AppointmentList, AppointmentDetail, PatientListCreateView, \
    PatientRetrieveUpdateDestroyView, MedicalRecordList, MedicalRecordDetail, PaymentView
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

urlpatterns = [
    # ...
    path('api/token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('users/', CustomUserDetail.as_view()),
    path('appointments/', AppointmentList.as_view()),
    path('appointments/<int:pk>/', AppointmentDetail.as_view()),
    path('patients/', PatientListCreateView.as_view(), name='patient-list-create'),
    path('patients/<int:pk>/', PatientRetrieveUpdateDestroyView.as_view(), name='patient-retrieve-update-destroy'),
    path('medical-records/', MedicalRecordList.as_view(), name='medical_record_list'),
    path('medical-records/<int:pk>/', MedicalRecordDetail.as_view(), name='medical_record_detail'),
    path('payments/', PaymentView.as_view()),
    path('payments/<int:pk>/', PaymentView.as_view()),
]
