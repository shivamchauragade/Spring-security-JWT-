package com.product.management.controller;

import com.product.management.entity.Product;
import com.product.management.payload.ProductRequest;
import com.product.management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProducts(@RequestBody ProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        productService.addProducts(productRequest);
        return ResponseEntity.ok("Products added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}

