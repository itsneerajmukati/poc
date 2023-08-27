package com.mongo.dao;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


public class DBConnection {
    
    private static MongoClient mongoClient;
    private static final ConnectionString connectionString = new ConnectionString("mongodb://localhost/mydb");
    
    public static MongoClient getMongoClient() {
        if(mongoClient==null) {
            synchronized(DBConnection.class) {
                CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
                CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),pojoCodecRegistry);
                MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
                mongoClient = MongoClients.create(mongoClientSettings);                    
            }
        }
        return mongoClient;
    }
}
