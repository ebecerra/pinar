package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 31/10/2011
 */
@Entity
@Table(name = "APPBS_USUARIOS_LOG_LEGEND")
public class APPBS_USUARIOS_LOG_LEGEND extends EntityBean {

    @Id
    @Column(name = "ID_TIPO")
    private Long id_tipo;


    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TEXTO")
    private String texto;


    /*
     * Set/Get Methods
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_tipo"
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
            "nombre", "texto", "id_tipo"
        };
    }

}

