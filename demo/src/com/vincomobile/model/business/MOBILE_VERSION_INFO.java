package com.vincomobile.model.business;

import com.vincomobile.model.appdb.MOBILE_VERSION;

public class MOBILE_VERSION_INFO {
    private Long version;
    private String sincro, mensaje, url;
    private Long id_idioma;

    public MOBILE_VERSION_INFO(MOBILE_VERSION mv) {
        version = mv.getVersion();
        id_idioma = mv.getId_idioma();
        sincro = mv.getSincro();
        mensaje = mv.getMensaje();
        url = mv.getUrl();
    }

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
}
