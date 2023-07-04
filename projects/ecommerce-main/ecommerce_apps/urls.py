from django.urls import path
from ecommerce_apps import views

app_name = 'ecommerce_apps'

urlpatterns = [
    path('products', views.product_list),
    path('products/<int:id>/', views.product_detail),
    path('collections/', views.collection_list),
    path('collections/<int:pk>/', views.collection_detail, name='collection_detail'),
]
