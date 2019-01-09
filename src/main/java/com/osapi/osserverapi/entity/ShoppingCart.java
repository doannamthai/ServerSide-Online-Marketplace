package com.osapi.osserverapi.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="ShoppingCart")
public class ShoppingCart {
    @Column(name="customer_id")
    private Long customer_id;

    @Column(name="total_price")
    private BigDecimal total_price;

    @Column(name="item_count")
    private Long item_count;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "ShoppingCart"))
    private Map<Long, LineProduct> items = new HashMap<>();

    @Id @Column(name="cart_id")
    private Long cart_id;

    public ShoppingCart(){ }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Long getCustomemr_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public Long getItem_count() {
        return item_count;
    }

    public void setItem_count(Long item_count) {
        this.item_count = item_count;
    }

    public Map<Long, LineProduct> getMapLineProduct(){
        return items;
    }

    public List<LineProduct> getItems() {
        return (new ArrayList<>(items.values()));
    }

    public void setItems(Map<Long, LineProduct> items) {
        this.items.clear();
        this.items.putAll(items);
    }
}
