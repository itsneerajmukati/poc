package com.mongo.pojo;

import java.io.Serializable;
import java.util.List;

public class Attribute implements Serializable {
    Integer id;
    String name;
    List<String> values;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getValues() {
        return values;
    }
    public void setValues(List<String> values) {
        this.values = values;
    }

    
}
