package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 07/10/2011
 */
@Entity
@Table(name = "APPBS_LOOKUPS_VALUES")
public class APPBS_LOOKUPS_VALUES extends EntityBean {

    @Id
    @Column(name = "ID_LOOKUP")
    private String id_lookup;

    @Id
    @Column(name = "ID_VALOR")
    private String id_valor;


    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ACTIVO")
    private String activo;


    /*
     * Set/Get Methods
     */
    public String getId_lookup() {
        return id_lookup;
    }

    public void setId_lookup(String id_lookup) {
        this.id_lookup = id_lookup;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getId_valor() {
        return id_valor;
    }

    public void setId_valor(String id_valor) {
        this.id_valor = id_valor;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_lookup", "id_valor"
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
            "id_lookup", "nombre", "activo", "id_valor"
        };
    }

}

