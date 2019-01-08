package com.osapi.osserverapi.entity;

import io.leangen.graphql.annotations.GraphQLQuery;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id @GeneratedValue
    @GraphQLQuery(name = "id")
    private Long id;
    @GraphQLQuery(name = "title")
    private String title;

    private Long inventory_count;

    @GraphQLQuery(name = "price")
    private BigDecimal price;


    public Product(Long id, String title, BigDecimal price, Long inventory_count){
        this.title = title;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
