package com.glovoupgraded.controller;

import com.glovoupgraded.model.Order;
import com.glovoupgraded.model.Product;
import com.glovoupgraded.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Integer id) {
        return this.orderService.get(id);
    }

    @GetMapping
    public List<Order> getAll() {
        return this.orderService.getAll();
    }

    @GetMapping("/{id}/products")
    public List<Product> getProductsByOrderId(@PathVariable int id) {
        return orderService.getProductsByOrderId(id);
    }

    @PostMapping
    public Order save(@RequestBody List<Integer> productsId) {
        return this.orderService.save(productsId);
    }

    @PatchMapping("/{id}")
    public Order addProducts(@PathVariable Integer id, @RequestBody List<Integer> productsId) {
        return this.orderService.addProducts(id, productsId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.orderService.delete(id);
    }

    @DeleteMapping("/{id}/products/{productId}")
    public void deleteProduct(@PathVariable Integer id, @PathVariable Integer productId) {
        this.orderService.deleteProduct(id, productId);
    }
}
