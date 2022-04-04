package com.codegym.controller;

import com.codegym.exception.NotFoundException;
import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Value("${file-upload}")
    private String uploadPath;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundPage(){
        return new ModelAndView("error-404");
    }

    @GetMapping("/product/list")
    public ModelAndView showListProduct(@RequestParam(name = "q") Optional<String> q) {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        Iterable<Product> products = productService.findAll();
        if (q.isPresent()) {
            products = productService.findProductByNameContaining(q.get());
        }
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/product/delete/{id}")
    public ModelAndView showDeleteProduct(@PathVariable Long id) throws NotFoundException {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            throw new NotFoundException();
        }
        ModelAndView modelAndView = new ModelAndView("/product/delete");
        modelAndView.addObject("product", productOptional.get());
        return modelAndView;
    }

    @PostMapping("/product/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id) throws NotFoundException {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            File file = new File(uploadPath + product.get().getImage());
            if (file.exists()) {
                file.delete();
            }
            productService.removeById(id);
            return new ModelAndView("redirect:/products/list");
        }
        throw new NotFoundException();
    }

    @GetMapping("/product/create")
    public ModelAndView showCreateProduct() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new ProductForm());//Gửi 1 đối tượng product rỗng sang file view để tạo mới
        return modelAndView;
    }

    @PostMapping("/product/create")
    public ModelAndView createProduct(@Valid @ModelAttribute("product") ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {//Trả về true nếu dữ liệu người dùng nhập vào không hợp lệ
            return new ModelAndView("/product/create");
        }
        String fileName = productForm.getImage().getOriginalFilename();
        long currentTime = System.currentTimeMillis(); //Xử lý lấy thời gian hiện tại
        fileName = currentTime + fileName;
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(uploadPath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = new Product(productForm.getId(), productForm.getName(), productForm.getPrice(), productForm.getDescription(), fileName);
        product.setCategory(productForm.getCategory());
        productService.save(product);
        return new ModelAndView("redirect:/products/list");
    }

    @GetMapping("/product/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) throws NotFoundException {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            throw new NotFoundException();
        }
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", productOptional.get());
        return modelAndView;
    }

    @PostMapping("/product/edit/{id}")
    public ModelAndView editProduct(@PathVariable Long id, @ModelAttribute ProductForm productForm) throws NotFoundException {
        MultipartFile img = productForm.getImage();
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            Product oldProduct = product.get();
            if (img.getSize() != 0) {
                String fileName = productForm.getImage().getOriginalFilename();
                long currentTime = System.currentTimeMillis(); //Xử lý lấy thời gian hiện tại
                fileName = currentTime + fileName;
                oldProduct.setImage(fileName);
                try {
                    FileCopyUtils.copy(img.getBytes(), new File(uploadPath + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            oldProduct.setPrice(productForm.getPrice());
            oldProduct.setDescription(productForm.getDescription());
            oldProduct.setName(productForm.getName());
            productService.save(oldProduct);
            return new ModelAndView("redirect:/products/list");
        }
        throw new NotFoundException();
    }

    @GetMapping("/product/{id}")
    public ModelAndView showProductDetail(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ModelAndView("error-404");
        }
        ModelAndView modelAndView = new ModelAndView("/product/view");
        modelAndView.addObject("product", productOptional.get());
        return modelAndView;
    }

    @GetMapping("/product/search")
    public ModelAndView showProductSearch(@RequestParam("min") Double min, @RequestParam("max") Double max) {
        Iterable<Product> products = productService.findProductPriceBetween(min, max);
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/not-found")
    public String notFound(){
        return "error-404";
    }
}
