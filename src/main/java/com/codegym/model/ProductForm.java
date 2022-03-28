package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

public class ProductForm {
    private Long id;

    @NotEmpty(message = "Không được phép để trống")
    @Size(min = 5, max = 20, message = "Tên sản phẩm phải từ 5 -> 20 ký tự")
    private String name;

    @NotNull
    private double price;

    @NotBlank
    private String description;

    private MultipartFile image;

    private Category category;

    public ProductForm() {
    }

    public ProductForm(Long id, String name, double price, String description, MultipartFile image, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
