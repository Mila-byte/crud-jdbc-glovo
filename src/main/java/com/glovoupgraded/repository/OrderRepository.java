package com.glovoupgraded.repository;

import com.glovoupgraded.entity.OrderEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAll();

    @Query(value = "insert into order_product (order_id, product_id) select :orderId, product.id from product where product.id IN (:productIds) returning product_id")
    List<Integer> addProductsToOrder(@Param("orderId") int orderId, @Param("productIds") List<Integer> productIds);

    @Modifying
    @Query("DELETE FROM order_product WHERE order_id = :orderId AND product_id = :id")
    void deleteProductFromOrder(@Param("orderId") int orderId, @Param("id") int productId);
}
