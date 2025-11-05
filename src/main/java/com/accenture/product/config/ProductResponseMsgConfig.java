package com.accenture.product.config;

import com.accenture.product.config.factory.YamlPropSourceFactory;
import com.accenture.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:response_message.yaml", factory = YamlPropSourceFactory.class)
@ConfigurationProperties(prefix = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseMsgConfig {
    private String deletedMessage;
    private String notFound;
    private String noSuch;

    public String productDeleted(Product product) {
        return String.format(deletedMessage, product);
    }

    public String productNotFound(String code) {
        return String.format(notFound, code);
    }
}
