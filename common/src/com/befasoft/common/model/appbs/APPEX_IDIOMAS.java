package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 22/09/2011
 */
@Entity
@Table(name = "APPEX_IDIOMAS")
public class APPEX_IDIOMAS extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_IDIOMA")
    private Long id_idioma;


    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "ISO_3")
    private String iso_3;

    @Column(name = "ISO_2")
    private String iso_2;

    @Column(name = "ABREVIATURA")
    private String abreviatura;


    /*
     * Set/Get Methods
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(Long id_idioma) {
        this.id_idioma = id_idioma;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIso_3() {
        return iso_3;
    }

    public void setIso_3(String iso_3) {
        this.iso_3 = iso_3;
    }

    public String getIso_2() {
        return iso_2;
    }

    public void setIso_2(String iso_2) {
        this.iso_2 = iso_2;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_idioma"
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
            "nombre", "activo", "id_idioma", "logo", "iso_3", "iso_2", "abreviatura"
        };
    }

}

