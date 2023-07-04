from decimal import Decimal
from rest_framework import serializers
from ecommerce_apps.models import Product, Collection


class CollectionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Collection
        fields = ['id', 'title', 'products_count']

    products_count = serializers.IntegerField()

    def create(self, validated_data):
        collection = Collection(**validated_data)
        collection.other = 1
        collection.save()
        return collection


class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = ['id', 'title', 'slug', 'description', 'inventory', 'unit_price', 'price_with_tax', 'collection',
                  'price_with_tax']

    # price = serializers.DecimalField(max_digits=6, decimal_places=2, source='unit_price')
    # description = serializers.StringRelatedField()
    price_with_tax = serializers.SerializerMethodField(method_name='calculate_tax')
    collection = serializers.StringRelatedField()

    # collection = serializers.HyperlinkedRelatedField(
    #     queryset=Collection.objects.all(),
    #     view_name='ecommerce_apps:collection_detail'
    # )

    def calculate_tax(self, product: Product):
        return product.unit_price * Decimal(1.1)

    def create(self, validated_data):
        product = Product(**validated_data)
        product.other = 1
        product.save()
        return product
