/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.dao;

import com.acme.model.Planet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import org.bson.types.ObjectId;

/**
 *
 * @author buenes
 */
public class StarWarsDAO {

    public static Boolean insertPlanet(Planet planet) throws NamingException {

        MongoCollection<Planet> c = DB.getStarWarsCollection();

        c.insertOne(planet);

        return Boolean.TRUE;

    }
    
    public static List<Planet> getPlanets() throws NamingException {

        List<Planet> ret = new LinkedList<>();

        MongoCollection<Planet> c = DB.getStarWarsCollection();

        MongoCursor<Planet> cursor = null;

        try {

            cursor = c.find().iterator();

            while (cursor.hasNext()) {
                ret.add(cursor.next());
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ret;

    }

    public static Planet getPlanet(String name) throws NamingException {

        MongoCollection<Planet> c = DB.getStarWarsCollection();

        return c.find(eq("name", name)).first();

    }

    public static Planet getPlanetById(String id) throws NamingException {

        MongoCollection<Planet> c = DB.getStarWarsCollection();

        return c.find(eq("_id", new ObjectId(id))).first();

    }

    public static Boolean deletePlanet(String id) throws NamingException {

        MongoCollection<Planet> c = DB.getStarWarsCollection();

        c.deleteOne(eq("_id", new ObjectId(id)));

        return Boolean.TRUE;

    }

}
