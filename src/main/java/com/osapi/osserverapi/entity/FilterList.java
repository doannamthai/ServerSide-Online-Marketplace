package com.osapi.osserverapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * FilterList Class
 */
public class FilterList {

    private String titleContains;
    private BigDecimal priceGte;
    private BigDecimal priceLte;
    private Long countGte;
    private Long countLte;

    @JsonProperty("title_contains")
    public String getTitleContains() {
        return titleContains;
    }

    @JsonProperty("price_gte")
    public BigDecimal getPriceGte(){
        return priceGte;
    }

    @JsonProperty("price_lte")
    public BigDecimal getPriceLte(){
        return priceLte;
    }

    @JsonProperty("count_gte")
    public Long getCountGte() {
        return countGte;
    }

    @JsonProperty("count_lte")
    public Long getCountLte() {
        return countLte;
    }

    public void setTitleContains(String titleContains) {
        this.titleContains = titleContains;
    }

    public void setPriceGte(BigDecimal priceGte) {
        this.priceGte = priceGte;
    }


    public void setPriceLte(BigDecimal priceLte) {
        this.priceLte = priceLte;
    }


    public void setCountGte(Long countGte) {
        this.countGte = countGte;
    }


    public void setCountLte(Long countLte) {
        this.countLte = countLte;
    }
}
