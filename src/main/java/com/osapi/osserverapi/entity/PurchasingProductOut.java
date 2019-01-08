package com.osapi.osserverapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchasingProductOut extends PurchasingProduct{

    private Long timestamp;
    private Boolean purchased;

    public PurchasingProductOut(Long id, Long quantity){
        super.id = id;
        super.quantity = quantity;
        this.purchased = false;
    }

    public PurchasingProductOut(PurchasingProduct product){
        super.id = product.getId();
        super.quantity = product.getQuantity();
        this.purchased = false;
    }


    @JsonProperty("purchased")
    public Boolean getPurchased(){
        return purchased;
    }

    public void setPurchased(Boolean purchased){
        this.purchased = purchased;
    }
}
