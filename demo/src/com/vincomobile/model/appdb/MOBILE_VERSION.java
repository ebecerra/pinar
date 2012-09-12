package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 24/04/2012
 */
@Entity
@Table(name = "MOBILE_VERSION")
public class MOBILE_VERSION extends EntityBean {

    @Id
    @Column(name = "VERSION")
    private Long version;

    @Id
    @Column(name = "ID_IDIOMA")
    private Long id_idioma;

    @Column(name = "SINCRO")
    private String sincro;

    @Column(name = "MENSAJE")
    private String mensaje;

    @Column(name = "URL")
    private String url;


    /*
     * Set/Get Methods
     */
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(Long id_idioma) {
        this.id_idioma = id_idioma;
    }

    public String getSincro() {
        return sincro;
    }

    public void setSincro(String sincro) {
        this.sincro = sincro;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "version", "id_idioma"
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
                "version", "id_idioma", "sincro", "mensaje", "url"
        };
    }

}

