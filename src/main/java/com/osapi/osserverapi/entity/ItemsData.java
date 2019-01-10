package com.osapi.osserverapi.entity;

import java.util.List;

public class ItemsData {
    private Long count;

    private List<Product> products;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
