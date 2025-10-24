package com.accenture.product.service;

import com.accenture.product.config.ProductResponseMsgConfig;
import com.accenture.product.dto.InventoryDTO;
import com.accenture.product.dto.ProductDTO;
import com.accenture.product.entity.Product;
import com.accenture.product.jpa.ProductRepository;
import com.accenture.product.util.ProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductRemoteService productRemoteService;

    @Autowired
    private ProductRepository prodRepository;

    @Autowired
    private ProductUtil productUtil;

    @Autowired
    private ProductResponseMsgConfig prodSvcMessage;

    public List<ProductDTO> saveProducts(List<Product> products) {
        List<ProductDTO> prods = prodRepository.saveAll(products).stream().map(productUtil::convertDTO).toList();
        loadInventories(prods);
        return prods;
    }

    public ProductDTO saveProduct(Product product) {
        ProductDTO productDto = productUtil.convertDTO(prodRepository.save(product));
        loadInventories(List.of(productDto));
        return productDto;
    }

    public List<ProductDTO> loadProducts() {
        LOG.info("Invoking loadProducts method.....");
        List<ProductDTO> products = saveProducts(productUtil.loadProducts());
        return products;
    }

    private void loadInventories(List<ProductDTO> products) {
        LOG.info("Invoking loadInventories method.....");
        List<InventoryDTO> inventories = products.stream().map(productUtil::loadInventory).collect(Collectors.toList());
        LOG.info("loadInventories method : inventories : {}", inventories);
        productRemoteService.invokePostInventoryService(inventories);
    }

    public List<ProductDTO> getProducts() {
        return prodRepository.findAll().stream().map(productUtil::convertDTO).toList();
    }

    public ProductDTO getProduct(String code) {
        Product product = prodRepository.findById(code)
                .orElseThrow(() -> new RuntimeException(prodSvcMessage.productNotFound(code)));
        return productUtil.convertDTO(product);
    }

    public String removeProduct(String code) {
        Product product = prodRepository.findById(code).orElseThrow(() -> new RuntimeException(prodSvcMessage.productNotFound(code)));
        prodRepository.delete(product);
        return prodSvcMessage.productDeleted(product.getName());
    }

    public ProductDTO updateProduct(String code, Double price) {
        Product product = prodRepository.findById(code).orElseThrow(() -> new RuntimeException(prodSvcMessage.productNotFound(code)));
        product.setPrice(price);
        return productUtil.convertDTO(prodRepository.save(product));
    }

    public ProductDTO findProductByName(String name) {
        Product product = prodRepository.findByName(name).orElseThrow(() -> new RuntimeException(prodSvcMessage.getNoSuch()));
        return productUtil.convertDTO(product);
    }
}
