/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author buenes
 */
@JsonIgnoreProperties(value = {"name", "climate", "terrain", "rotation_period", "orbital_period", "diameter", "gravity", "surface_water", "population", "residents", "created", "edited", "url"})
public class PlanetFilms {

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    private List<String> films;

}
