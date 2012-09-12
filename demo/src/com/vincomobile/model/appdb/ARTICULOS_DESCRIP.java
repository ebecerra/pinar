package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 06/04/2011
 */
@Entity
@Table(name = "ARTICULOS_DESCRIP")
public class ARTICULOS_DESCRIP extends EntityBean {

    @Id
    @Column(name="ITEM_ID")
    private Long item_id;

    @Id
    @Column(name="ID_IDIOMA")
    private String id_idioma;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    /*
     * Set/Get Methods
     */

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public String getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(String id_idioma) {
        this.id_idioma = id_idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "item_id", "id_idioma"
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
            "item_id", "id_idioma", "descripcion"
        };
    }

}


