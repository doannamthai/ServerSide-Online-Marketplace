package com.osapi.osserverapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class PurchasingProductOut extends PurchasingProduct{

    private Long timestamp;
    private Boolean purchased;
    private BigDecimal price;

    public PurchasingProductOut(Long id, Long count){
       super.item_id = id;
        super.count = count;
        this.purchased = false;
    }

    public PurchasingProductOut(PurchasingProduct product){
        super.item_id = product.getItem_id();
        super.count = product.getCount();
        this.purchased = false;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonProperty("purchased")
    public Boolean getPurchased(){
        return purchased;
    }

    public void setPurchased(Boolean purchased){
        this.purchased = purchased;
    }
}
