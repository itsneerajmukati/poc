package com.mongo.pojo;

import java.io.Serializable;

public class Category implements Serializable {
    String name;
    String desc;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
