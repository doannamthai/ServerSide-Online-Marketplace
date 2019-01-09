package com.osapi.osserverapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Embeddable
/**
 * LineProduct class
 * When users add items to the cart
 */
public class LineProduct {
    private Long item_id;
    private Long count;
    private String title;
    private BigDecimal price;
    private BigDecimal total_price;
    private Boolean added_to_cart;

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public Long getItem_id() {
        return item_id;
    }

    public Long getCount() {
        return count;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public Boolean getAdded_to_cart(){
        return added_to_cart;
    }

    public void setAdded_to_cart(Boolean added_to_cart){
        this.added_to_cart = added_to_cart;
    }
}

