package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 11/04/2011
 */
@Entity
@Table(name = "PEDIDOS_ORIGEN")
public class PEDIDOS_ORIGEN extends EntityBean {

    @Id
    @Column(name = "ID_ORIGEN")
    private Double id_origen;

    @Column(name = "NOMBRE")
    private String nombre;


    /*
     * Set/Get Methods
     */

    public Double getId_origen() {
        return id_origen;
    }

    public void setId_origen(Double id_origen) {
        this.id_origen = id_origen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "id_origen"
        };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista con todos los campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[]{
            "id_origen", "nombre"
        };
    }

}

