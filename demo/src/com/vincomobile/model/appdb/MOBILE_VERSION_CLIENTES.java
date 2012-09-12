package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Devtools.
 * Date: 24/04/2012
 */
@Entity
@Table(name = "MOBILE_VERSION_CLIENTES")
public class MOBILE_VERSION_CLIENTES extends EntityBean {

    @Id
    @Column(name = "USUARIO")
    private String usuario;

    @Id
    @Column(name = "MOBILE_ID")
    private Long mobile_id;

    @Id
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "ID_IDIOMA")
    private Long id_idioma;


    /*
     * Set/Get Methods
     */
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getMobile_id() {
        return mobile_id;
    }

    public void setMobile_id(Long mobile_id) {
        this.mobile_id = mobile_id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(Long id_idioma) {
        this.id_idioma = id_idioma;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "usuario", "mobile_id", "version"
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
                "usuario", "mobile_id", "version", "fecha", "id_idioma"
        };
    }

}

