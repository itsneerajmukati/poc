package com.mongo.pojo;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable{
    String name;
    String desc;
    String brand;
    String SKU;
    Integer price;
    Category category;
    ProductAttribute productAttribute;
    List<ProductVariant> variants;
    
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
    public String getSKU() {
        return SKU;
    }
    public void setSKU(String sKU) {
        SKU = sKU;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }
    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }
    public List<ProductVariant> getVariants() {
        return variants;
    }
    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }
    

    
    @Override
    public String toString() {
        return "Product [name=" + name + ", desc=" + desc + ", SKU=" + SKU + ", price=" + price + ", category="
                + category + ", productAttribute=" + productAttribute + ", variants=" + variants + "]";
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
}

/**
 *  Shirt
 *  VariantSet - Color, Size, Material, Unit
 *  variant_set
 *  name id
 *  color - 1
 *  size - 2
 *  material - 3
 *  unit - 4
 *  variant_set_value
 *  id variant-set_id value
 *   1  1   Black
 *   2  1   White
 *   3  2   Small
 *   4  3   Large
 *  product_variant_set
 *  id  product_id variant_set 
 *  1   1 shirt         1     color    
 *  2   1          2          
 * 
 *  variant_sku
 *   
 *  shirt black small 5
 *  shirt black medium 5
 *  shirt black large 5
 *  shirt white small 5
 *  shirt white medium 5
 *  shirt white large 5
 *  phone black 64gb 5
 *  phone black 128gb 5
 *  phone blue 64gb 5
 *  phone blue 128gb 5
 *  
 */
