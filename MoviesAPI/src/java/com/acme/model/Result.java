/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.model;

import static com.acme.util.Msgs.SUCESS;

/**
 *
 * @author buenes
 * @param <D>
 */
public class Result<D> {

    private Integer code = SUCESS;

    private D data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

}
