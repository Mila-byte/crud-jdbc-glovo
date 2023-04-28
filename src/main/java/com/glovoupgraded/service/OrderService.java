package com.glovoupgraded.service;

import com.glovoupgraded.converter.OrderConverter;
import com.glovoupgraded.converter.ProductConverter;
import com.glovoupgraded.entity.OrderEntity;
import com.glovoupgraded.entity.ProductEntity;
import com.glovoupgraded.model.Order;
import com.glovoupgraded.model.Product;
import com.glovoupgraded.repository.OrderRepository;
import com.glovoupgraded.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByOrderId(int id) {
        List<ProductEntity> productEntities = productRepository.findByOrderId(id);
        return convertProduct(productEntities);
    }

    public Order get(int id) {
        OrderEntity orderEntity = this.orderRepository.findById(id).orElseThrow();
        List<Product> products = getProductsByOrderId(orderEntity.getOrderId());

        return OrderConverter.orderEntityToOrder(orderEntity, products);
    }

    public List<Order> getAll() {
        List<OrderEntity> orderEntities = this.orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        for (OrderEntity entity : orderEntities) {
            Order order = OrderConverter.orderEntityToOrder(entity, getProductsByOrderId(entity.getOrderId()));
            orders.add(order);
        }
        return orders;
    }

    public Order addProducts(int orderId, List<Integer> productsId) {
        List<Integer> ids = this.orderRepository.addProductsToOrder(orderId, productsId);
        List<ProductEntity> productEntities = productRepository.findAllByIdIn(ids);
        updateCost(orderId, ids);
        return OrderConverter.orderEntityToOrder(this.orderRepository.findById(orderId).orElseThrow(), convertProduct(productEntities));
    }

    private void updateCost(int orderId, int productId) {
        OrderEntity orderEntity = this.orderRepository.findById(orderId).orElseThrow();
        ProductEntity productEntity = this.productRepository.findById(productId).orElseThrow();
        float newCost = orderEntity.getCost() - productEntity.getCost();
        orderEntity.setCost(newCost);
        orderRepository.save(orderEntity);
    }

    public Order save(List<Integer> productIds) {
        float totalCost = calculateCost(productIds);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(new Date());
        orderEntity.setCost(totalCost);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        return addProducts(savedOrderEntity.getOrderId(), productIds);
    }

    public void delete(int id) {
        this.orderRepository.deleteById(id);
    }

    public void deleteProduct(int orderId, int productId) {
        this.orderRepository.deleteProductFromOrder(orderId, productId);
        updateCost(orderId, productId);
    }

    private List<Product> convertProduct(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductConverter::productEntityToProduct)
                .toList();
    }

    private float calculateCost(List<Integer> productIds) {
        float totalCost = 0;
        List<ProductEntity> products = productRepository.findAllByIdIn(productIds);
        for (ProductEntity product : products) {
            totalCost += product.getCost();
        }
        return totalCost;
    }

    private void updateCost(int orderId, List<Integer> ids) {
        OrderEntity orderEntity = this.orderRepository.findById(orderId).orElseThrow();
        float newCost = calculateCost(ids) + orderEntity.getCost();
        orderEntity.setCost(newCost);
        orderRepository.save(orderEntity);
    }
}
