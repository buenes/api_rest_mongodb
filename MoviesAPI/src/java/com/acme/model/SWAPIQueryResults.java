/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author buenes
 */
@JsonIgnoreProperties(value = { "count", "next", "previous"})
public class SWAPIQueryResults {
    
   private PlanetFilms[] results;

    public PlanetFilms[] getResults() {
        return results;
    }

    public void setResults(PlanetFilms[] results) {
        this.results = results;
    }
   
}
