package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
@Entity
@Table(name = "APPEX_BANCOS")
public class APPEX_BANCOS extends EntityBean {

    @Id
    @Column(name = "ID_BANCO")
    private String id_banco;

  
    @Column(name = "ACTIVO")
    private String activo;

    @Column(name = "NOMBRE")
    private String nombre;


    /*
     * Set/Get Methods
     */
    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getId_banco() {
        return id_banco;
    }

    public void setId_banco(String id_banco) {
        this.id_banco = id_banco;
    }
    

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_banco"
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
            "activo", "nombre", "id_banco"
        };
    }

}

