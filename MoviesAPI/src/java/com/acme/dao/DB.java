/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.dao;

import com.acme.model.Planet;
import static com.acme.util.Constants.MONGODB_COLLECTION_STARWARS;
import static com.acme.util.Constants.MONGODB_JNDI_PATH;
import static com.acme.util.Constants.MONGODB_MOVIES;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author buenes
 */
public class DB {

    public static final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    public static MongoDatabase getDataBase(String database) throws NamingException {

        MongoClient mongoClient = (MongoClient) InitialContext.doLookup(MONGODB_JNDI_PATH);

        return mongoClient.getDatabase(database);

    }

    public static MongoCollection<Planet> getStarWarsCollection() throws NamingException {

        MongoDatabase db = DB.getDataBase(MONGODB_MOVIES);

        return db.getCollection(MONGODB_COLLECTION_STARWARS, Planet.class).withCodecRegistry(DB.pojoCodecRegistry);

    }

}
