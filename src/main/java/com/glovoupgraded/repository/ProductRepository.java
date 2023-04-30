package com.glovoupgraded.repository;

import com.glovoupgraded.entity.ProductEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    List<ProductEntity> findAllByIdIn(List<Integer> productIds);
    List<ProductEntity> findAll();

    @Query("select product.* from product inner join order_product on product.id = order_product.product_id where order_product.order_id = :orderId")
    List<ProductEntity> findByOrderId(int orderId);

}
