package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;
import com.sun.jmx.snmp.Timestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Devtools.
 * Date: 06/04/2011
 */
@Entity
@Table(name = "ARTICULOS_FOTOS")
public class ARTICULOS_FOTOS extends EntityBean {

    @Id
    @Column(name="ITEM_ID")
    private Long item_id;

    @Id
    @Column(name="NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ANCHO")
    private Long ancho;

    @Column(name = "ALTO")
    private Long alto;

    @Column(name = "FORMATO")
    private String formato;

    @Column(name = "ULT_ACTUALIZACION")
    private Date ult_actualizacion;

    /*
     * Set/Get Methods
     */

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getAncho() {
        return ancho;
    }

    public void setAncho(Long ancho) {
        this.ancho = ancho;
    }

    public Long getAlto() {
        return alto;
    }

    public void setAlto(Long alto) {
        this.alto = alto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Date getUlt_actualizacion() {
        return ult_actualizacion;
    }

    public void setUlt_actualizacion(Date ult_actualizacion) {
        this.ult_actualizacion = ult_actualizacion;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "item_id", "nombre"
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
            "orden", "item_id", "ancho", "alto", "nombre", "formato", "ult_actualizacion"
        };
    }

}


