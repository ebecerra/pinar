package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 22/08/2011
 */
@Entity
@Table(name = "IVA")
public class IVA extends EntityBean {

    @Id
    @Column(name = "ID_IVA")
    private String id_iva;

    @Column(name = "PORC_IVA")
    private Long porc_iva;

    @Column(name = "PORC_REP")
    private Long porc_rep;

    @Column(name = "NOMBRE")
    private String nombre;


    /*
     * Set/Get Methods
     */
    public Long getPorc_iva() {
        return porc_iva;
    }

    public void setPorc_iva(Long porc_iva) {
        this.porc_iva = porc_iva;
    }

    public String getId_iva() {
        return id_iva;
    }

    public void setId_iva(String id_iva) {
        this.id_iva = id_iva;
    }

    public Long getPorc_rep() {
        return porc_rep;
    }

    public void setPorc_rep(Long porc_rep) {
        this.porc_rep = porc_rep;
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
            "id_iva"
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
            "porc_iva", "id_iva", "porc_rep", "nombre"
        };
    }

}

