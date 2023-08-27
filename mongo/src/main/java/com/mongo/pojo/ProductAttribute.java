package com.mongo.pojo;

import java.io.Serializable;
import java.util.List;

public class ProductAttribute implements Serializable {
    Integer id;
    List<Attribute> attributes;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public List<Attribute> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
    
}
