package com.osapi.osserverapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="PurchasingProduct")
/**
 * PurchasingProduct class
 * When user pays and purchases from the cart
 */
public class PurchasingProduct {
    @Id
    @Column(name="item_id")
    protected Long item_id;
    @Column(name="count")
    protected Long count;
    @Column(name="title")
    protected String title;

    @JsonProperty("title")
    public String getTitle(){ return title;}

    @JsonProperty("item_id")
    public Long getItem_id() {
        return item_id;
    }

    @JsonProperty("count")
    public Long getCount() {
        return count;
    }

}
