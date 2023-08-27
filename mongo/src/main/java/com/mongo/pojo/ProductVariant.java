package com.mongo.pojo;

import java.io.Serializable;
import java.util.List;

public class ProductVariant implements Serializable {

    Integer id;
    String combination;
    Double price;
    String sku;
    Integer stock;
    List<ProductVariantAttribute> variantAttributes;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCombination() {
        return combination;
    }
    public void setCombination(String combination) {
        this.combination = combination;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public List<ProductVariantAttribute> getVariantAttributes() {
        return variantAttributes;
    }
    public void setVariantAttributes(List<ProductVariantAttribute> variantAttributes) {
        this.variantAttributes = variantAttributes;
    }

    
}
