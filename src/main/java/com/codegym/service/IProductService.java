package com.codegym.service;

import com.codegym.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    Product findById(Long id);

    void create(Product product);

    void updateById(Long id, Product product);

    void removeById(Long id);
}
