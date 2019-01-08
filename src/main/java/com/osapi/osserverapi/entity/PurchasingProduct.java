package com.osapi.osserverapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PurchasingProduct class
 * When user pays and purchases from the cart
 */
public class PurchasingProduct {
    protected Long id;
    protected Long quantity;


    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("quantity")
    public Long getQuantity() {
        return quantity;
    }

}
