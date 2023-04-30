package com.glovoupgraded.service;

import com.glovoupgraded.entity.ProductEntity;
import com.glovoupgraded.model.Product;
import com.glovoupgraded.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductServiceTest {
    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private ProductService productService = new ProductService(productRepository);

    @Test
    public void getProduct() {
        ProductEntity meat = ProductEntity.builder().id(1).name("meat").cost(11.1F).build();
        Product expected = Product.builder().id(1).name("meat").cost(11.1F).build();

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(meat));

        Product product = productService.get(1);

        Assertions.assertEquals(expected, product);
    }

    @Test
    public void getAllProduct() {
        ProductEntity meat = ProductEntity.builder().id(1).name("meat").cost(11.1F).build();
        ProductEntity potato = ProductEntity.builder().id(2).name("potato").cost(4.3F).build();

        List<Product> expected = new ArrayList<>();
        expected.add(Product.builder().id(1).name("meat").cost(11.1F).build());
        expected.add(Product.builder().id(2).name("potato").cost(4.3F).build());

        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(meat, potato));

        List<Product> products = productService.getAll();

        Assertions.assertEquals(expected, products);
    }

    @Test
    public void saveProduct() {
        Product meat = Product.builder().id(1).name("meat").cost(11.1F).build();
        ProductEntity meatEntity = ProductEntity.builder().id(1).name("meat").cost(11.1F).build();

        Product expected = Product.builder().id(1).name("meat").cost(11.1F).build();

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(meatEntity);

        Product product = productService.save(meat);

        Assertions.assertEquals(expected, product);
    }

    @Test
    public void updateProduct() {
        Product changedMeat = Product.builder().id(1).name("gold-meat").cost(10.1F).build();
        ProductEntity meatEntity = ProductEntity.builder().id(1).name("meat").cost(11.1F).build();

        Product expected = Product.builder().id(1).name("gold-meat").cost(10.1F).build();

        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(meatEntity));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(meatEntity);

        Product product = productService.update(1, changedMeat);

        Assertions.assertEquals(expected, product);
    }

    @Test
    public void deleteProduct() {
        int id = 1;

        productService.delete(id);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(id);
    }
}
