/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.restfullapi;

import static com.acme.util.Constants.SWAPI_WEB_TARGET;
import static com.acme.util.Msgs.FAILURE;
import static com.acme.util.Msgs.OBJECT_NOT_FOUND;
import com.acme.model.Planet;
import com.acme.dao.StarWarsDAO;
import com.acme.model.Result;
import com.acme.model.SWAPIQueryResults;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author buenes
 */
@Path("/starwars")
public class StarWarsResource {

    @Context
    private ServletContext servletContext;

    @POST
    @Path("/insertplanet")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Boolean> insertPlanet(Planet planet) {

        Result<Boolean> ret = new Result<>();

        try {

            getFilmsNum(planet);

            ret.setData(StarWarsDAO.insertPlanet(planet));

        } catch (NamingException ex) {

            ret.setCode(FAILURE);
            Logger.getLogger(StarWarsResource.class.getName()).log(Level.SEVERE, null, ex);

        }

        return ret;

    }

    @GET
    @Path("/getplanets")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<List<Planet>> getPlanets() {

        Result<List<Planet>> ret = new Result<>();

        try {

            ret.setData(StarWarsDAO.getPlanets());

        } catch (NamingException ex) {

            ret.setCode(FAILURE);
            Logger.getLogger(StarWarsResource.class.getName()).log(Level.SEVERE, null, ex);

        }

        return ret;

    }

    @GET
    @Path("/planet/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Planet> getPlanetById(@PathParam("id") String id) {

        Result<Planet> ret = new Result<>();

        try {

            Planet p = StarWarsDAO.getPlanetById(id);

            if (p != null) {
                ret.setData(p);
            } else {
                ret.setCode(OBJECT_NOT_FOUND);
            }

        } catch (NamingException ex) {

            ret.setCode(FAILURE);
            Logger.getLogger(StarWarsResource.class.getName()).log(Level.SEVERE, null, ex);

        }

        return ret;

    }

    @GET
    @Path("/planet")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Planet> getPlanetByName(@QueryParam("name") String name) {

        Result<Planet> ret = new Result<>();

        try {

            Planet p = StarWarsDAO.getPlanet(name);

            if (p != null) {
                ret.setData(p);
            } else {
                ret.setCode(OBJECT_NOT_FOUND);
            }

        } catch (NamingException ex) {

            ret.setCode(FAILURE);
            Logger.getLogger(StarWarsResource.class.getName()).log(Level.SEVERE, null, ex);

        }

        return ret;

    }

    @GET
    @Path("/deleteplanet/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Result<Boolean> deletePlanet(@PathParam("id") String id) {

        Result<Boolean> ret = new Result<>();

        try {

            ret.setData(StarWarsDAO.deletePlanet(id));

        } catch (NamingException ex) {

            ret.setCode(FAILURE);
            Logger.getLogger(StarWarsResource.class.getName()).log(Level.SEVERE, null, ex);

        }

        return ret;

    }

    private void getFilmsNum(Planet planet) {

        WebTarget swapi = (WebTarget) servletContext.getAttribute(SWAPI_WEB_TARGET);

        planet.setFilmsNum(getFilmsNum(swapi, planet));

    }

    public static Integer getFilmsNum(WebTarget swapi, Planet planet) {

        Integer ret = 0;

        SWAPIQueryResults res = swapi.path("api").path("planets/").queryParam("search", planet.getName()).request().accept(MediaType.APPLICATION_JSON).get(new GenericType<SWAPIQueryResults>() {
        });

        if (res != null) {

            if (res.getResults() != null && res.getResults().length > 0) {
                ret = res.getResults()[0].getFilms().size();

            }

        }

        return ret;

    }

}
