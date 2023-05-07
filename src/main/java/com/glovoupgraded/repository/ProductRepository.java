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

    @Query("select \"products\".* from \"products\" inner join \"order_products\" on \"products\".id = \"order_products\".product_id where \"order_products\".order_id = :orderId")
    List<ProductEntity> findByOrderId(int orderId);

}
