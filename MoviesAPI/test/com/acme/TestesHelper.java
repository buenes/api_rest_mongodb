/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme;

import com.acme.model.Planet;
import static com.acme.util.Constants.APP_URI;
import static com.acme.util.Constants.SWAPI_URI;
import java.net.URI;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author buenes
 */
public class TestesHelper {
    
    public static Planet[] lista = new Planet[]{new Planet(), new Planet()};
    
    static{
        
        Planet planet = lista[0];
        planet.setName("Tatooine");
        planet.setClimate("seco");
        planet.setTerrain("dunas");
        planet.setFilmsNum(5);

        planet = lista[1];
        planet.setName("Naboo");
        planet.setClimate("úmido");
        planet.setTerrain("água"); 
        planet.setFilmsNum(4);
        
    }

    static enum TesteCase {
        movieApi, SWApi, insertPlanets, listPlanets, listPlanetsByName, listPlanetsById, deletePlanet
    } 
    
    public static URI getBaseURI() {

        return UriBuilder.fromUri(APP_URI).build();

    }

    public static URI getSWAPIURI() {

        return UriBuilder.fromUri(SWAPI_URI).build();

    }  
     
    public static InitialContext setupJNDI() throws NamingException{

        // Use Apache Tomcat's Directory
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");   
        
        // Standard hook
        InitialContext initialContext = new InitialContext();
        // Create binding
        initialContext.createSubcontext("java:");
        initialContext.createSubcontext("java:comp");
        initialContext.createSubcontext("java:comp/env");
        initialContext.createSubcontext("java:comp/env/jdbc");
        initialContext.createSubcontext("java:comp/env/mongodb");
        
/*
        static public final String JNDI_JAVA_CTX = "java:comp/env/";
        
        // Construct DataSource
        OracleConnectionPoolDataSource dataSource = new OracleConnectionPoolDataSource();
        dataSource.setURL("jdbc:oracle:thin:@myserver:1521:MYSID");
        dataSource.setUser("username");
        dataSource.setPassword("password");

        initialContext.bind("java:comp/env/jdbc/mydatabase", dataSource);
      
*/

        return initialContext;
        
    }
}
