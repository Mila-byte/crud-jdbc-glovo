package com.glovoupgraded.service;

import com.glovoupgraded.converter.OrderConverter;
import com.glovoupgraded.converter.ProductConverter;
import com.glovoupgraded.entity.OrderEntity;
import com.glovoupgraded.entity.ProductEntity;
import com.glovoupgraded.model.Order;
import com.glovoupgraded.model.Product;
import com.glovoupgraded.repository.OrderRepository;
import com.glovoupgraded.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

public class OrderServiceTest {
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private OrderService orderService = new OrderService(orderRepository, productRepository);
    private ProductEntity meat = ProductEntity.builder().id(1).name("meat").cost(11.1F).build();
    private ProductEntity potato = ProductEntity.builder().id(2).name("potato").cost(4.3F).build();

    @Test
    public void getOrder() {
        OrderEntity orderEntity = OrderEntity.builder().orderId(1).date(new Date()).cost(15.4F).build();

        Order expected = OrderConverter.orderEntityToOrder(orderEntity, products());

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(orderEntity));
        Mockito.when(productRepository.findByOrderId(Mockito.anyInt())).thenReturn(productEntities());

        Order result = orderService.get(1);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getProductsByOrderId() {
        int orderId = 1;

        Mockito.when(productRepository.findByOrderId(Mockito.anyInt())).thenReturn(productEntities());

        List<Product> expected = products();

        List<Product> result = orderService.getProductsByOrderId(orderId);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getAllOrders() {
        List<OrderEntity> orderEntities = new ArrayList<>();

        OrderEntity orderEntity1 = OrderEntity.builder().orderId(1).date(new Date()).cost(15.4F).build();
        OrderEntity orderEntity2 = OrderEntity.builder().orderId(1).date(new Date()).cost(15.4F).build();

        orderEntities.add(orderEntity1);
        orderEntities.add(orderEntity2);

        List<Order> expected = new ArrayList<>();
        expected.add(Order.builder().id(1).cost(15.4F).products(products()).build());
        expected.add(Order.builder().id(2).cost(15.4F).products(products()).build());

        Mockito.when(orderRepository.findAll()).thenReturn(orderEntities);
        Mockito.when(productRepository.findByOrderId(Mockito.anyInt())).thenReturn(productEntities());

        List<Order> result = orderService.getAll();

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void addProductsToOrder() {
        OrderEntity orderEntity = new OrderEntity(1, new Date(), 0);
        List<Integer> productsId = new ArrayList<>();
        productsId.add(1);
        productsId.add(2);

        Order expected = OrderConverter.orderEntityToOrder(orderEntity, products());
        expected.setCost(expectedCost());

        Mockito.when(orderRepository.addProductsToOrder(Mockito.anyInt(), Mockito.any())).thenReturn(productsId);
        Mockito.when(productRepository.findAllByIdIn(Mockito.any())).thenReturn(productEntities());
        Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(orderEntity));

        Order result = orderService.addProducts(1, productsId);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void saveOrder() {
        List<Integer> productsId = new ArrayList<>();
        productsId.add(1);
        productsId.add(2);

        OrderEntity orderEntity = OrderEntity.builder().orderId(1).cost(15.4F).date(new Date()).build();

        Order expected = Order.builder().id(1).cost(Math.round(expectedCost() * 100) / 100.0F).products(products()).build();

        Mockito.when(productRepository.findAllByIdIn(productsId)).thenReturn(productEntities());
        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class))).thenReturn(orderEntity);
        Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(orderEntity));

        Order result = orderService.save(productsId);
        result.setProducts(products());

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void deleteProductByOrderId() {
        int orderId = 1;
        int productId = 2;

        OrderEntity orderEntity = OrderEntity.builder().orderId(orderId).cost(15.4F).build();
        ProductEntity productEntity = ProductEntity.builder().id(productId).name("meat").cost(11.1F).build();

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        orderService.deleteProduct(orderId, productId);

        Mockito.verify(orderRepository, Mockito.times(1)).deleteProductFromOrder(orderId, productId);
        Mockito.verify(orderRepository, Mockito.times(1)).save(orderEntity);

        float expectedCost = 4.3F;

        Assertions.assertEquals(expectedCost, Math.round(orderEntity.getCost() * 100) / 100.0F);
    }

    @Test
    public void deleteOrder() {
        int id = 1;

        orderService.delete(id);

        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(id);
    }

    private List<ProductEntity> productEntities() {
        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(meat);
        productEntities.add(potato);
        List<Product> products = new ArrayList<>();
        products.add(ProductConverter.productEntityToProduct(meat));
        products.add(ProductConverter.productEntityToProduct(potato));
        return productEntities;
    }

    private List<Product> products() {
        List<Product> products = new ArrayList<>();
        products.add(ProductConverter.productEntityToProduct(meat));
        products.add(ProductConverter.productEntityToProduct(potato));
        return products;
    }

    private float expectedCost() {
        float expectedCost = 0.0F;
        for (Product product : products()) {
            expectedCost += product.getCost();
        }
        return expectedCost;
    }
}
