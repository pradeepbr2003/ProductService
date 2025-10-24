package com.accenture.product.config;

import com.accenture.product.config.factory.YamlPropSourceFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:service_url.yaml", factory = YamlPropSourceFactory.class)
@ConfigurationProperties(prefix = "inventory.service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryServiceUrlConfig {
    private String url;
}
