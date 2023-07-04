from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, PermissionsMixin, Group, Permission
from django.conf import settings
from django.db import models


# from . models import Patient, Doctor


class CustomUserManager(BaseUserManager):
    def create_user(self, email, username, password=None):
        if not email:
            raise ValueError('Users must have an email address')

        user = self.model(
            email=self.normalize_email(email),
            username=username,
        )

        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, username, password):
        user = self.create_user(
            email=email,
            username=username,
            password=password,
        )
        user.is_staff = True
        user.is_superuser = True
        user.save(using=self._db)
        return user


class CustomUser(AbstractBaseUser, PermissionsMixin):
    first_name = models.CharField(max_length=250)
    last_name = models.CharField(max_length=250)
    email = models.EmailField(unique=True)
    username = models.CharField(max_length=30, unique=True)
    is_staff = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    groups = models.ManyToManyField(
        Group,
        verbose_name='groups',
        blank=True,
        related_name='customuser_set',
        related_query_name='user',
    )
    user_permissions = models.ManyToManyField(
        Permission,
        verbose_name='user permissions',
        blank=True,
        related_name='customuser_set',
        related_query_name='user',
    )

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username']

    objects = CustomUserManager()

    def __str__(self):
        return self.email


class Appointment(models.Model):
    STATUS_CHOICES = [
        ('PENDING', 'Pending'),
        ('CONFIRMED', 'Confirmed'),
        ('CANCELLED', 'Cancelled'),
    ]

    patient = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='patient_appointments')
    doctor = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='doctor_appointments')
    date_time = models.DateTimeField()
    duration = models.IntegerField()  # in minutes
    status = models.CharField(max_length=10, choices=STATUS_CHOICES, default='PENDING')

    def __str__(self):
        return f'{self.patient} - {self.doctor} - {self.date_time}'


class Patient(models.Model):
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=50)
    email = models.EmailField()
    phone_number = models.CharField(max_length=20)
    date_of_birth = models.DateField()
    gender = models.CharField(max_length=10)

    def __str__(self):
        return f"{self.first_name} {self.last_name}"


from django.db import models


class Doctor(models.Model):
    first_name = models.CharField(max_length=100)
    last_name = models.CharField(max_length=100)
    email = models.EmailField()
    phone_number = models.CharField(max_length=20)
    specialty = models.CharField(max_length=100)
    available_days = models.CharField(max_length=200)


class MedicalRecord(models.Model):
    patient = models.ForeignKey(Patient, on_delete=models.CASCADE)
    doctor = models.ForeignKey(Doctor, on_delete=models.CASCADE)
    diagnosis = models.CharField(max_length=255)
    treatment = models.CharField(max_length=255)
    created_at = models.DateTimeField(auto_now_add=True)


class Payment(models.Model):
    PAYMENT_METHODS = (
        ('card', 'Card'),
        ('bank', 'Bank Transfer'),
        ('ussd', 'USSD'),
        ('mobile_money', 'Mobile Money'),
    )

    patient = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    amount = models.DecimalField(max_digits=10, decimal_places=2)
    method = models.CharField(max_length=20, choices=PAYMENT_METHODS)
    created_at = models.DateTimeField(auto_now_add=True)
    status = models.CharField(max_length=20, default='pending')

    class Meta:
        ordering = ['-created_at']

    def __str__(self):
        return f'{self.patient} - {self.amount}'
