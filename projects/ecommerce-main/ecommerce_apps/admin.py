from django.contrib import admin, messages
from django.db.models import Count, QuerySet
from django.urls import reverse
from django.contrib.contenttypes.admin import GenericTabularInline
from django.utils.html import format_html, urlencode

from . import models


class InventoryFilter(admin.SimpleListFilter):
    title = 'inventory'
    parameter_name = 'inventory'

    def lookups(self, request, model_admin):
        return [
            ('<10', 'Low')
        ]

    def queryset(self, request, queryset: QuerySet):
        if self.value() == '<10':
            return queryset.filter(inventory__lt=10)


@admin.register(models.Product)
class ProductAdmin(admin.ModelAdmin):
    search_fields = ['OrderItemInline']
    prepopulated_fields = {
        'slug': ['title']
    }
    actions = ['clear_inventory']
    list_display = ['title', 'unit_price', 'inventory_status', 'collection_title']
    list_editable = ['unit_price']
    list_filter = ['collection', 'last_update', InventoryFilter]
    list_per_page = 10
    list_select_related = ['collection']

    def collection_title(self, product):
        return product.collection.title

    @admin.display(ordering='inventory')
    def inventory_status(self, product):
        if product.inventory < 10:
            return "Low"
        return "Ok"

    @admin.action(description='Clear Inventory')
    def clear_inventory(self, request, queryset):
        update_count = queryset.update(inventory=0)
        self.message_user(
            request,
            f'{update_count} products were successfully updated.',
            messages.ERROR,
        ),


@admin.register(models.Collection)
class CollectionAdmin(admin.ModelAdmin):
    list_display = ['title', 'products_count']
    search_fields = ['title']

    @admin.display(ordering='products_count')
    def products_count(self, collection):
        url = (
                reverse('admin:ecommerce_apps_product_changelist')
                + '?'
                + urlencode({
            'collection_id': str(collection.id)
        }))
        return format_html('<a href="{}">{}</a>', url, collection.products_count)

    def get_queryset(self, request):
        return super().get_queryset(request).annotate(
            products_count=Count('products')
        )


@admin.register(models.Customer)
class Customer(admin.ModelAdmin):
    list_display = ['first_name', 'last_name', 'membership', 'orders']
    list_editable = ['membership']
    list_per_page = 10
    ordering = ['first_name', 'last_name']
    search_fields = ['first_name__istartswith', 'last_name__istartswith']

    @admin.display(ordering='orders_count')
    def orders(self, customer):
        url = (
                reverse('admin:ecommerce_apps_order_changelist')
                + '?'
                + urlencode({
            'customer_id': str(customer.id)
        }))
        return format_html('<a href="{}">{} Orders</a>', url, customer.orders_count)

    def get_queryset(self, request):
        return super().get_queryset(request).annotate(
            orders_count=Count('order')
        )


class OrderItemInline(admin.TabularInline):
    autocomplete_fields = ['product']
    model = models.OrderItem


@admin.register(models.Order)
class OrderAdmin(admin.ModelAdmin):
    autocomplete_fields = ['customer']
    inlines = [OrderItemInline]
    list_display = ['id', 'placed_at', 'customer']
