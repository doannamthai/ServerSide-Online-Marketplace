package com.osapi.osserverapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="Order")
public class Order {
    @Column(name="order_id")
    @Id private Long order_id;

    @Column(name="customer_id")
    private Long customer_id;

    @Column(name="purchased_cart")
    private ShoppingCart purchased_cart;

    @Column(name="order_date")
    private String order_date;

    @Column(name="order_time")
    private String order_time;

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public ShoppingCart getPurchased_cart() {
        return purchased_cart;
    }

    public void setPurchased_cart(ShoppingCart purchased_cart) {
        this.purchased_cart = purchased_cart;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
}
