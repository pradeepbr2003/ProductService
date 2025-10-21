package com.accenture.product.controller;

import com.accenture.product.dto.ProductDTO;
import com.accenture.product.entity.Product;
import com.accenture.product.service.ProductService;
import com.accenture.product.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService prodService;

    @Autowired
    private ProductUtil productUtil;


    @PostMapping
    public ProductDTO addProduct(@RequestBody Product product) {
        return prodService.saveProduct(product);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestParam(required = false) String code) {
        return (code == null) ? prodService.getProducts() : List.of(prodService.getProduct(code));
    }

    @GetMapping("/find")
    public ProductDTO findProductByName(@RequestParam String name) {
        return prodService.findProductByName(name);
    }

    @GetMapping("/load")
    public List<ProductDTO> addProduct() {
        return prodService.loadProducts();
    }

    @DeleteMapping
    public String removeProduct(@RequestParam String code) {
        return prodService.removeProduct(code);
    }
}
