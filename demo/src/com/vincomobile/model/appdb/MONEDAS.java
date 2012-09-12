package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 25/07/2011
 */
@Entity
@Table(name = "MONEDAS")
public class MONEDAS extends EntityBean {

    @Id
    @Column(name = "ID_MONEDA")
    private String id_moneda;


    @Column(name = "TIPO_CAMBIO")
    private Double tipo_cambio;

    @Column(name = "SIMBOLO")
    private String simbolo;

    @Column(name = "NOMBRE")
    private String nombre;


    /*
     * Set/Get Methods
     */
    public Double getTipo_cambio() {
        return tipo_cambio;
    }

    public void setTipo_cambio(Double tipo_cambio) {
        this.tipo_cambio = tipo_cambio;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(String id_moneda) {
        this.id_moneda = id_moneda;
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
        return new String[] {
            "id_moneda"
        };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista con todos los campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[] {
            "tipo_cambio", "simbolo", "id_moneda", "nombre"
        };
    }

}

