/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme;

import static com.acme.util.Constants.MONGODB_COLLECTION_STARWARS;
import static com.acme.util.Constants.MONGODB_CONNECTION_STRING;
import static com.acme.util.Constants.MONGODB_MOVIES;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import org.bson.Document;

/**
 *
 * @author buenes
 */
public class MongoDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MongoClientURI connectionString = new MongoClientURI(MONGODB_CONNECTION_STRING);
        MongoClient mongoClient = new MongoClient(connectionString);
        
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        
        MongoDatabase database = mongoClient.getDatabase(MONGODB_MOVIES);
        
        MongoCollection<Document> collection = database.getCollection(MONGODB_COLLECTION_STARWARS);
        
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));  
        
        collection.insertOne(doc);
        
        /*
        
        múltiplos documentos
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }   
        collection.insertMany(documents);
        
        */
        
        System.out.println(collection.count());
        

    }
    
}
/*
  {
   "name" : "MongoDB",
   "type" : "database",
   "count" : 1,
   "versions": [ "v3.2", "v3.0", "v2.6" ],
   "info" : { x : 203, y : 102 }
  }
*/