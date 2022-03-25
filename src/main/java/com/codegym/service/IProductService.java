package com.codegym.service;

import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductService {
    Iterable<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    Product save(Product product);

    void removeById(Long id);

    Iterable<Product> findProductByNameContaining(String name);

    Iterable<Product> findProductPriceBetween(Double min, Double max);
}
