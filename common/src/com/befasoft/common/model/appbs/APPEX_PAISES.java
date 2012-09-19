package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
@Entity
@Table(name = "APPEX_PAISES")
public class APPEX_PAISES extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PAIS")
    private Long id_pais;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ISO_CODE_2")
    private String iso_code_2;

    @Column(name = "ISO_CODE_3")
    private String iso_code_3;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "G_CODE")
    private String g_code;

    @Column(name = "GEOLOCALIZACION")
    private String geolocalizacion;

    /*
     * Set/Get Methods
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Long getId_pais() {
        return id_pais;
    }

    public void setId_pais(Long id_pais) {
        this.id_pais = id_pais;
    }
    public void setId_pais(BigDecimal id_pais) {
        this.id_pais = id_pais.longValue();
    }
    public void setId_pais(Integer id_pais) {
        this.id_pais = id_pais.longValue();
    }

    public String getIso_code_2() {
        return iso_code_2;
    }

    public void setIso_code_2(String iso_code_2) {
        this.iso_code_2 = iso_code_2;
    }
    
    public String getIso_code_3() {
        return iso_code_3;
    }

    public void setIso_code_3(String iso_code_3) {
        this.iso_code_3 = iso_code_3;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public void setActivo(BigDecimal activo) {
        this.activo = activo != null ? activo.intValue() == 1 : false;
    }
    public void setActivo(Integer activo) {
        this.activo = activo != null ? activo.intValue() == 1 : false;
    }

    public String getG_code() {
        return g_code;
    }

    public void setG_code(String g_code) {
        this.g_code = g_code;
    }

    public String getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(String geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_pais"
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
            "nombre", "id_pais", "iso_code_2", "iso_code_3", "activo", "g_code", "geolocalizacion"
        };
    }

}

