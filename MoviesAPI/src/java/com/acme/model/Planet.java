/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.model;

/**
 *
 * @author buenes
 */

public class Planet {

    private String name;
    private String climate;
    private String terrain;
    private Integer filmsNum = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Integer getFilmsNum() {
        return filmsNum;
    }

    public void setFilmsNum(Integer filmsNum) {
        this.filmsNum = filmsNum;
    }

    @Override
    public String toString() {
        return "nome " + name + ", clima " + climate + ", terreno " + terrain;
    }
}
