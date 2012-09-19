package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Devtools.
 * Date: 05/05/2011
 */
@Entity
@Table(name = "APPBS_USUARIOS_HIST_CLAVES")
public class APPBS_USUARIOS_HIST_CLAVES extends EntityBean {

    @Id
    @Column(name="ID_USUARIO")
    private Long id_usuario;

    @Id
    @Column(name="CLAVE")
    private String clave;

    @Column(name = "FECHA")
    private Date fecha;

    public APPBS_USUARIOS_HIST_CLAVES(Long id_usuario, String clave) {
        this.id_usuario = id_usuario;
        this.clave = clave;
        this.fecha = new Date();
    }

    public APPBS_USUARIOS_HIST_CLAVES() {
    }
    
    /*
     * Set/Get Methods
     */

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "id_usuario", "clave"
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
            "id_usuario", "fecha", "clave"
        };
    }

}




