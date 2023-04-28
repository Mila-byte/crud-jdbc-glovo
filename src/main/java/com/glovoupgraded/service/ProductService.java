package com.glovoupgraded.service;

import com.glovoupgraded.converter.ProductConverter;
import com.glovoupgraded.entity.ProductEntity;
import com.glovoupgraded.model.Product;
import com.glovoupgraded.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product get(int id) {
        ProductEntity productEntity = this.productRepository.findById(id).orElseThrow();
        return ProductConverter.productEntityToProduct(productEntity);
    }

    public List<Product> getAll() {
        List<ProductEntity> productEntities = this.productRepository.findAll();
        List<Product> products = new ArrayList<>();
        for (ProductEntity entity : productEntities) {
            Product product = ProductConverter.productEntityToProduct(entity);
            products.add(product);
        }
        return products;
    }

    public Product save(Product product) {
        ProductEntity productEntity = this.productRepository.save(ProductConverter.productToProductEntity(product));
        return ProductConverter.productEntityToProduct(productEntity);
    }

    public Product update(int id, Product product) {
        ProductEntity productEntity = this.productRepository.findById(id).orElseThrow();
        if (productEntity == null) {
            return null;
        }

        if (product.getName() != null) {
            productEntity.setName(product.getName());
        }
        if (product.getCost() != 0) {
            productEntity.setCost(product.getCost());
        }
        return ProductConverter.productEntityToProduct(this.productRepository.save(productEntity));
    }

    public void delete(int id) {
        this.productRepository.deleteById(id);
    }
}
