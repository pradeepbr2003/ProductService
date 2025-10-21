package com.accenture.product.enums;

public enum ProductEnum {

    TV(30000.00), LAPTOP(25000.00), MOBILE(5000.00), WATCH(2000);

    private double price;

    ProductEnum(double price) {
        this.price = price;
    }

    public double price() {
        return price;
    }
}
