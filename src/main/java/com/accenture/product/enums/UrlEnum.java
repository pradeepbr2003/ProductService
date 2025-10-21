package com.accenture.product.enums;

public enum UrlEnum {

    INVENTORY_URL("http://localhost:8081/inventory");
    private String url;

    UrlEnum(String url) {
        this.url = url;
    }

    public String value() {
        return url;
    }
}
