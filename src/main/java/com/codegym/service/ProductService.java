package com.codegym.service;

import com.codegym.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {
    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1, "IPhone 13", 320000, "New", "https://bizweb.dktcdn.net/100/116/615/products/iphone-13-pink-select-2021-08d93148-74cf-4b7d-83b9-3f7baa431c9e.png?v=1646816034187"));
        products.add(new Product(2, "IPhone 12", 220000, "New", "https://cdn.tgdd.vn/Products/Images/42/228736/iphone-12-violet-1-600x600.jpg"));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    public int findProductById(int id) {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public Product findById(int id) {
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
    public void updateById(int id, Product product) {
        int index = findProductById(id);
        products.set(index, product);
    }

    @Override
    public void removeById(int id) {
        int index = findProductById(id);
        products.remove(index);
    }
}
