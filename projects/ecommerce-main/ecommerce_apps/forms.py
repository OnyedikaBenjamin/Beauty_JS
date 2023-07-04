from django import forms
from ecommerce_apps.models import Customer, Order, OrderItem, Product


class CheckoutForm(forms.Form):
    name = forms.CharField(max_length=255)
    email = forms.EmailField()
    phone = forms.CharField(max_length=20)
    shipping_addresses = forms.CharField()
    items = forms.ModelMultipleChoiceField(queryset=Product.objects.all())
    total_price = forms.DecimalField(max_digits=10, decimal_places=2)

    def clean(self):
        cleaned_data = super().clean()
        # validate form data
        return cleaned_data


class ContactForm(forms.Form):
    name = forms.CharField(max_length=255)
    email = forms.EmailField()
    message = forms.Textarea()

    def clean(self):
        cleaned_data = super().clean()
        # validate form data
        return cleaned_data
