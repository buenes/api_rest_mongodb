/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme;

import com.acme.model.Planet;
import com.acme.model.Result;
import com.acme.restfullapi.StarWarsResource;
import static com.acme.util.Constants.APP_URI;
import static com.acme.util.Constants.MONGODB_COLLECTION_STARWARS;
import static com.acme.util.Constants.MONGODB_CONNECTION_STRING;
import static com.acme.util.Constants.MONGODB_MOVIES;
import static com.acme.util.Constants.SWAPI_URI;
import static com.acme.util.Msgs.OBJECT_NOT_FOUND;
import static com.acme.util.Msgs.SUCESS;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.net.URI;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
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
public class StarWarsResourceJUnitTest {

    private static Client client;
    private static WebTarget serviceSWAPI;
    private static WebTarget serviceAPP;
    private static MongoClient mongoClient;

    public StarWarsResourceJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        ClientConfig config = new ClientConfig();
        client = ClientBuilder.newClient(config).register(JacksonFeature.class);

        URI uri = UriBuilder.fromUri(APP_URI).build();
        serviceAPP = client.target(uri);

        uri = UriBuilder.fromUri(SWAPI_URI).build();
        serviceSWAPI = client.target(uri);

        MongoClientURI connectionString = new MongoClientURI(MONGODB_CONNECTION_STRING);
        mongoClient = new MongoClient(connectionString);

    }

    @AfterClass
    public static void tearDownClass() {

        client.close();
        mongoClient.close();

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
    public void aGetFilmsNum() {

        Planet[] lista = TestesHelper.lista;

        Integer ret = StarWarsResource.getFilmsNum(serviceSWAPI, lista[0]);

        assertEquals(lista[0].getFilmsNum(), ret);

    }

    @Test
    public void bInsertPlanet() {

        Planet[] lista = TestesHelper.lista;

        for (Planet p : lista) {

            p.setFilmsNum(-1);

            Result<Boolean> ret = serviceAPP.path("rest").path("starwars/insertplanet").request().post(Entity.entity(p, MediaType.APPLICATION_JSON), new GenericType<Result<Boolean>>() {
            });

            assertEquals(true, ret.getCode() == SUCESS);

        }

    }

    @Test
    public void cGetPlanets() {

        Result<List<Planet>> ret = serviceAPP.path("rest").path("starwars/getplanets").request().accept(MediaType.APPLICATION_JSON).get(new GenericType<Result<List<Planet>>>() {
        });

        assertEquals(true, ret.getCode() == SUCESS && ret.getData().size() > 0);

    }

    @Test
    public void dListPlanetsById() {

        MongoDatabase db = mongoClient.getDatabase(MONGODB_MOVIES);

        MongoCollection<Document> c = db.getCollection(MONGODB_COLLECTION_STARWARS);

        Planet[] lista = TestesHelper.lista;

        Document d = c.find(eq("name", lista[0].getName())).first();

        assertEquals(true, d != null);

        ObjectId objId = (ObjectId) d.get("_id");

        Result<Planet> ret = serviceAPP.path("rest").path("starwars/planet/" + objId.toString()).request().accept(MediaType.APPLICATION_JSON).get(new GenericType<Result<Planet>>() {
        });

        assertEquals(true, ret.getCode() == SUCESS && ret.getData().getName().equals(lista[0].getName()));

    }

    @Test
    public void eListPlanetsByName() {

        MongoDatabase db = mongoClient.getDatabase(MONGODB_MOVIES);

        MongoCollection<Document> c = db.getCollection(MONGODB_COLLECTION_STARWARS);

        Planet[] lista = TestesHelper.lista;

        Document d = c.find(eq("name", lista[0].getName())).first();

        assertEquals(true, d != null);

        Result<Planet> ret = serviceAPP.path("rest").path("starwars/planet").queryParam("name", lista[0].getName()).request().accept(MediaType.APPLICATION_JSON).get(new GenericType<Result<Planet>>() {
        });

        assertEquals(true, ret.getCode() == SUCESS && ret.getData().getName().equals(lista[0].getName()));

    }

    @Test
    public void fDeletePlanet() {

        MongoDatabase db = mongoClient.getDatabase(MONGODB_MOVIES);

        MongoCollection<Document> c = db.getCollection(MONGODB_COLLECTION_STARWARS);

        Planet[] lista = TestesHelper.lista;

        for (Planet aux : lista) {

            Document d = c.find(eq("name", aux.getName())).first();

            assertEquals(true, d != null);

            ObjectId objId = (ObjectId) d.get("_id");

            Result<Boolean> ret1 = serviceAPP.path("rest").path("starwars/deleteplanet/" + objId.toString()).request().accept(MediaType.APPLICATION_JSON).get(new GenericType<Result<Boolean>>() {
            });

            assertEquals(true, ret1.getCode() == SUCESS && ret1.getData());

             Result<Planet> ret2 = serviceAPP.path("rest").path("starwars/planet/" + objId.toString()).request().accept(MediaType.APPLICATION_JSON).get(new GenericType<Result<Planet>>() {
            });

            assertEquals(true, ret2.getCode() == OBJECT_NOT_FOUND && ret2.getData() == null);
        }

    }
}
