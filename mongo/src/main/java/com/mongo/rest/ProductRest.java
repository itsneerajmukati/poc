package com.mongo.rest;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongo.dao.DBConnection;
import com.mongo.pojo.Product;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

@Component
public class ProductRest {

    private final MongoCollection<Product> productCollection;

    public ProductRest() {
        MongoClient mongoClient = DBConnection.getMongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mydb");
        productCollection = mongoDatabase.getCollection("product", Product.class);
    }

    public Product getProductByName(String name) {
        return productCollection.find(new Document("name", name)).first();
    }
    
    public String createProduct(Product product) {
        productCollection.insertOne(product);
        System.out.println("Product added successfully");
        return "Product added successfully";
    }

    public String updateProduct(Product product) {
        Document query = new Document().append("name",  product.getName());
        Bson updates  = Updates.combine(
            Updates.set("name", product.getName()),
            Updates.set("desc", product.getDesc()),
            Updates.set("SKU", product.getSKU()),
            Updates.set("price", product.getPrice()),
            Updates.set("category", product.getCategory()),
            Updates.set("productAttribute", product.getProductAttribute()),
            Updates.set("variants", product.getVariants())
        );
        productCollection.updateOne(query,updates,new UpdateOptions());
        System.out.println("Product updated successfully");
        return "Product updated successfully";
    }
}
