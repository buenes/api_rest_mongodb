/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.util;

/**
 *
 * @author buenes
 */
public class Constants {

    static public final String APP_URI = "http://localhost:8180/movies";
    static public final String SWAPI_URI = "https://swapi.co/";

    static public final String MONGODB_CONNECTION_STRING = "mongodb://localhost:27017";

    static public final String JNDI_JAVA_CTX = "java:comp/env/";
    static public final String MONGODB_MOVIES = "movies";
    static public final String MONGODB_JNDI_PATH = JNDI_JAVA_CTX + "mongodb/" + MONGODB_MOVIES;

    static public final String MONGODB_COLLECTION_STARWARS = "starwars";
    static public final String PLANETS = "planets";

    static public final String SWAPI_WEB_TARGET = "swapi_web_target";

    static public final String SWAPI_WEB_TARGET_PATH = JNDI_JAVA_CTX + "swapi_web_target";

}
