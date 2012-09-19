package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 05/06/2012
 */
@Entity
@Table(name = "APPEX_DISTRIBUIDORES")
public class APPEX_DISTRIBUIDORES extends EntityBean {

    @Id
    @Column(name = "ID_DISTRIBUIDOR")
    private Long id_distribuidor;


    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ACTIVO")
    private Boolean activo;


    /*
     * Set/Get Methods
     */
    public Long getId_distribuidor() {
        return id_distribuidor;
    }

    public void setId_distribuidor(Long id_distribuidor) {
        this.id_distribuidor = id_distribuidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "id_distribuidor"
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
                "id_distribuidor", "nombre", "descripcion", "activo"
        };
    }

}

