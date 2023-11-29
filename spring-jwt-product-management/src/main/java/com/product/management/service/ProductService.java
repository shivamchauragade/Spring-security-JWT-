package com.product.management.service;

import com.product.management.entity.Product;
import com.product.management.payload.ProductRequest;

import java.util.List;

public interface ProductService {
    public void addProducts(ProductRequest productRequest);

    public List<Product> getAllProducts();
}
