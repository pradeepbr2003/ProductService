package com.accenture.product;


import com.accenture.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {
    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @PostConstruct
    public void loadData() {
        productService.loadProducts();
    }
}