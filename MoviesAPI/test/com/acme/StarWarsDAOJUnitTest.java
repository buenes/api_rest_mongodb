/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme;

import com.acme.dao.DB;
import com.acme.dao.StarWarsDAO;
import com.acme.model.Planet;
import static com.acme.util.Constants.MONGODB_COLLECTION_STARWARS;
import static com.acme.util.Constants.MONGODB_CONNECTION_STRING;
import static com.acme.util.Constants.MONGODB_JNDI_PATH;
import static com.acme.util.Constants.MONGODB_MOVIES;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author buenes
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StarWarsDAOJUnitTest {

    private static MongoClient mongoClient;
    static InitialContext ctx;

    public StarWarsDAOJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        try {

            MongoClientURI connectionString = new MongoClientURI(MONGODB_CONNECTION_STRING);
            mongoClient = new MongoClient(connectionString);

            ctx = TestesHelper.setupJNDI();

            ctx.bind(MONGODB_JNDI_PATH, mongoClient);

        } catch (NamingException ex) {

            Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @AfterClass
    public static void tearDownClass() {

        mongoClient.close();

        if (ctx != null) {
            try {
                ctx.unbind(MONGODB_JNDI_PATH);
                ctx.close();
            } catch (NamingException ex) {
                Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void aGetDB() {

        try {

            MongoCollection<Planet> c = DB.getStarWarsCollection();

            assertEquals(true, c != null);

        } catch (NamingException ex) {
            Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void bInsertPlanet() {

        Planet[] lista = TestesHelper.lista;

        for (Planet p : lista) {

            try {

                boolean ret = StarWarsDAO.insertPlanet(p);

                assertEquals(true, ret);

            } catch (NamingException ex) {
                Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Test
    public void cGetPlanets() {

        try {

            List<Planet> list = StarWarsDAO.getPlanets();

            assertEquals(true, list.size() > 0);

        } catch (NamingException ex) {
            Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void dGetPlanet() {

        try {

            Planet p = StarWarsDAO.getPlanet(TestesHelper.lista[0].getName());

            assertEquals(TestesHelper.lista[0].getName(), p.getName());

        } catch (NamingException ex) {
            Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void eGetPlanetById() {

        try {

            MongoDatabase db = DB.getDataBase(MONGODB_MOVIES);

            MongoCollection<Document> c = db.getCollection(MONGODB_COLLECTION_STARWARS);

            Planet[] lista = TestesHelper.lista;

            Document d = c.find(eq("name", lista[0].getName())).first();

            assertEquals(true, d != null);

            ObjectId objId = (ObjectId) d.get("_id");

            Planet p = StarWarsDAO.getPlanetById(objId.toString());

            assertEquals(lista[0].getName(), p.getName());

        } catch (NamingException ex) {
            Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void fDeletePlanet() {

        try {

            MongoDatabase db = DB.getDataBase(MONGODB_MOVIES);

            MongoCollection<Document> c = db.getCollection(MONGODB_COLLECTION_STARWARS);

            Planet[] lista = TestesHelper.lista;

            for (Planet p : lista) {

                Document d = c.find(eq("name", p.getName())).first();

                assertEquals(true, d != null);

                ObjectId objId = (ObjectId) d.get("_id");

                Planet ret = StarWarsDAO.getPlanetById(objId.toString());

                assertEquals(true, ret != null);

                StarWarsDAO.deletePlanet(objId.toString());

                ret = StarWarsDAO.getPlanetById(objId.toString());

                assertEquals(true, ret == null);

            }

        } catch (NamingException ex) {
            Logger.getLogger(StarWarsDAOJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
