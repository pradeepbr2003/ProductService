package com.accenture.product.service;

import com.accenture.product.config.InventoryServiceUrlConfig;
import com.accenture.product.dto.InventoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductRemoteService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryServiceUrlConfig prodPropConfig;

    @Autowired
    private RestTemplate restTemplate;

    public String invokePostInventoryService(List<InventoryDTO> inventories) {
        return restTemplate.postForObject(prodPropConfig.getUrl(), inventories, String.class);
    }
}
