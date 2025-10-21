package com.accenture.product.service;

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

    public List<ProductDTO> saveProducts(List<Product> products) {
        List<ProductDTO> prods = prodRepository.saveAll(products).stream().map(productUtil::convertDTO).toList();
        loadInventories(prods);
        return prods;
    }

    public ProductDTO saveProduct(Product product) {
        ProductDTO productDto = productUtil.convertDTO(prodRepository.save(product));
        loadInventory(productDto);
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

    private void loadInventory(ProductDTO product) {
        LOG.info("Invoking loadInventory method.....");
        InventoryDTO inventory = productUtil.loadInventory(product);
        LOG.info("loadInventory method : inventory :  {}", inventory);
        productRemoteService.invokePostInventoryService(List.of(inventory));
    }

    public List<ProductDTO> getProducts() {
        return prodRepository.findAll().stream().map(productUtil::convertDTO).toList();
    }

    public ProductDTO getProduct(String code) {
        Product product = prodRepository.findById(code).orElseThrow(() -> new RuntimeException(String.format("%n Product %d not found %n", code)));
        return productUtil.convertDTO(product);
    }

    public String removeProduct(String code) {
        Product product = prodRepository.findById(code).orElseThrow(() -> new RuntimeException(String.format("Product with code %d not found", code)));
        prodRepository.delete(product);
        return String.format("%n Successfully deleted product %s", product.getName());
    }

    public ProductDTO updateProduct(String code, Double price) {
        Product product = prodRepository.findById(code).orElseThrow(() -> new RuntimeException(String.format("Product with code %d not found", code)));
        product.setPrice(price);
        return productUtil.convertDTO(prodRepository.save(product));
    }

    public ProductDTO findProductByName(String name) {
        Product product = prodRepository.findByName(name).orElseThrow(() -> new RuntimeException("No such products found"));
        return productUtil.convertDTO(product);
    }
}
