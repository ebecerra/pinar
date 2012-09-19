package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "APPBS_APLICACIONES")
public class APPBS_APLICACIONES extends EntityBean {

    @Id
    @Column(name = "ID_APLICACION")
    private String id_aplicacion;


    @Column(name = "NOMBRE")
    private String nombre;

    @Transient
    private Integer id_perfil;
    @Transient
    private String nombre_perfil;

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }
    public void setID_APLICACION(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setNOMBRE(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Integer id_perfil) {
        this.id_perfil = id_perfil;
    }
    public void setID_PERFIL(Integer id_perfil) {
        this.id_perfil = id_perfil;
    }
    public void setID_PERFIL(BigDecimal id_perfil) {
        this.id_perfil = id_perfil.intValue();
    }

    public String getNombre_perfil() {
        return nombre_perfil;
    }

    public void setNombre_perfil(String nombre_perfil) {
        this.nombre_perfil = nombre_perfil;
    }
    public void setNOMBRE_PERFIL(String nombre_perfil) {
        this.nombre_perfil = nombre_perfil;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "id_aplicacion"
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
                "id_aplicacion", "nombre"
        };
    }

    @Override
    public String toString() {
        return "APPBS_APLICACIONES{" +
                "id_aplicacion='" + id_aplicacion + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
