package com.codegym.dao;

import com.codegym.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDAO implements IProductDAO {

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }
}
