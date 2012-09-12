package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 15/04/2011
 */
@Entity
@Table(name = "PROVINCIAS")
public class PROVINCIAS extends EntityBean {

    @Id
    @Column(name="ID_PROVINCIA")
    private String id_provincia;

    @Id
    @Column(name="ID_PAIS")
    private String id_pais;

    @Column(name = "NOMBRE")
    private String nombre;


    /*
     * Set/Get Methods
     */

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(String id_provincia) {
        this.id_provincia = id_provincia;
    }

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "id_provincia", "id_pais"
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
            "nombre", "id_provincia", "id_pais"
        };
    }

}



