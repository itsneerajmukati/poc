package com.mongo.rest;

import org.springframework.stereotype.Component;

import com.mongo.dao.DBConnection;
import com.mongo.pojo.Category;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
public class CategoryRest {

    private final MongoCollection<Category> categoryCollection;

    public CategoryRest() {
        MongoClient mongoClient = DBConnection.getMongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mydb");
        categoryCollection = mongoDatabase.getCollection("category", Category.class);
    }

    public String createCategory(Category category) {
        categoryCollection.insertOne(category);
        System.out.println("Category added successfully");
        return "Category added successfully";
    }
    
}
