package com.osapi.osserverapi.entity;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="Product")
public class Product {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long item_id;
    @Column(name = "title")
    private String title;
    @Column(name = "inventory_count")
    private Long inventory_count;

    @Column(name = "price")
    private BigDecimal price;


    public Product(Long id, String title, BigDecimal price, Long inventory_count){
        this.title = title;
        this.item_id = id;
        this.price = price;
        this.inventory_count = inventory_count;
    }

    public Product(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getInventory_count() {
        return inventory_count;
    }

    public void setInventory_count(Long inventory_count) {
        this.inventory_count = inventory_count;
    }
}
