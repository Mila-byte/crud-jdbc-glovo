package com.glovoupgraded.controller;

import com.glovoupgraded.model.Product;
import com.glovoupgraded.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Integer id) {
        return productService.get(id);
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Integer id, @RequestBody Product product) {
        return this.productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.productService.delete(id);
    }
}
