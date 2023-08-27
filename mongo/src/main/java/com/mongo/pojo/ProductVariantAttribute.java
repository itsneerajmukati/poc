package com.mongo.pojo;

import java.io.Serializable;

public class ProductVariantAttribute implements Serializable {
    String attributeType;
    String attributeValue;
    
    public String getAttributeType() {
        return attributeType;
    }
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
    public String getAttributeValue() {
        return attributeValue;
    }
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
    
}
