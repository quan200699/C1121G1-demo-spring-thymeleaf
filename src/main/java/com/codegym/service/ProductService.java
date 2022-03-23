package com.codegym.service;

import com.codegym.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return products;
    }

    public int findProductById(Long id) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public Product findById(Long id) {
        int index = findProductById(id);
        if (index != -1) {
            return products.get(index);
        }
        return null;
    }

    @Override
    public void create(Product product) {
        products.add(product);
    }

    @Override
    public void updateById(Long id, Product product) {
        int index = findProductById(id);
        products.set(index, product);
    }

    @Override
    public void removeById(Long id) {
        int index = findProductById(id);
        products.remove(index);
    }
}
