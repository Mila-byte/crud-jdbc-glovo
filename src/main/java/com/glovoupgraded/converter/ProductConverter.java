package com.glovoupgraded.converter;

import com.glovoupgraded.entity.ProductEntity;
import com.glovoupgraded.model.Product;

public class ProductConverter {
    public static Product productEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .cost(productEntity.getCost())
                .build();
    }

    public static ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .name(product.getName())
                .cost(product.getCost())
                .build();
    }
}
