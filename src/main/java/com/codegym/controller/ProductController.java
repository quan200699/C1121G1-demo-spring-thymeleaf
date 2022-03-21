package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/products/list")
    public ModelAndView showListProduct() {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/products/delete/{id}")
    public ModelAndView showDeleteProduct(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("/product/delete");
        Product product = productService.findById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/products/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable Integer id) {
        productService.removeById(id);
        return new ModelAndView("redirect:/products/list");
    }

    @GetMapping("/products/create")
    public ModelAndView showCreateProduct() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());//Gửi 1 đối tượng product rỗng sang file view để tạo mới
        return modelAndView;
    }

    @PostMapping("/products/create")
    public ModelAndView createProduct(@ModelAttribute Product product) {
        productService.create(product);
        return new ModelAndView("redirect:/products/list");
    }

    @GetMapping("/products/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        Product product = productService.findById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/products/edit/{id}")
    public ModelAndView editProduct(@PathVariable Integer id, @ModelAttribute Product product) {
        productService.updateById(id, product);
        return new ModelAndView("redirect:/products/list");
    }

    @GetMapping("/products/{id}")
    public ModelAndView showProductDetail(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return new ModelAndView("/product/view", "product", product);
    }
}
