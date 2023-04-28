package com.glovoupgraded.converter;

import com.glovoupgraded.entity.OrderEntity;
import com.glovoupgraded.model.Order;
import com.glovoupgraded.model.Product;

import java.util.List;

public class OrderConverter {
    public static Order orderEntityToOrder(OrderEntity orderEntity, List<Product> products) {
        return Order.builder()
                .id(orderEntity.getOrderId())
                .cost(orderEntity.getCost())
                .products(products)
                .build();
    }
}
