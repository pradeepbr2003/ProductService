package com.accenture.product.util;

import com.accenture.product.dto.InventoryDTO;
import com.accenture.product.dto.ProductDTO;
import com.accenture.product.entity.Product;
import com.accenture.product.enums.ProductEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class ProductUtil {
    private ProductEnum[] products = ProductEnum.values();

    private Random random = new Random();

    public List<Product> loadProducts() {
        List<Product> productList = IntStream.range(0, products.length).mapToObj(this::getProduct).toList();
        return productList;
    }

    public InventoryDTO loadInventory(ProductDTO prod) {
        return InventoryDTO.builder().product(prod).quantity(random.nextInt(5, 15)).build();
    }

    public List<ProductDTO> convertDTO(List<Product> productList) {
        return productList.stream().map(this::convertDTO).toList();
    }

    public ProductDTO convertDTO(Product p) {
        return ProductDTO.builder().code(p.getCode()).name(p.getName()).price(p.getPrice()).build();
    }

    private Product getProduct(int index) {
        return Product.builder().name(products[index].name())
                .price(products[index].price()).build();
    }
}
