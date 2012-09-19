package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
@Entity
@Table(name = "APPEX_PROVINCIAS")
public class APPEX_PROVINCIAS extends EntityBean {

    @Id
    @Column(name = "ID_PROVINCIA")
    private Long id_provincia;

    @Id
    @Column(name = "ID_PAIS")
    private Long id_pais;

    @Column(name = "ID_COMUNIDAD")
    private Long id_comunidad;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CP_PREFIX")
    private String cp_prefix;

    @Column(name = "G_CODE")
    private String g_code;

    @ManyToOne
    @JoinColumn(name = "ID_PAIS", insertable = false, updatable = false)
    @Immutable
    private APPEX_PAISES pais = null;

    /*
     * Set/Get Methods
     */
    public Long getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(Long id_provincia) {
        this.id_provincia = id_provincia;
    }
    
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
    
    public String getCp_prefix() {
        return cp_prefix;
    }

    public void setCp_prefix(String cp_prefix) {
        this.cp_prefix = cp_prefix;
    }

    public APPEX_PAISES getPais() {
        return pais;
    }

    public void setPais(APPEX_PAISES pais) {
        this.pais = pais;
    }

    public String getG_code() {
        return g_code;
    }

    public void setG_code(String g_code) {
        this.g_code = g_code;
    }

    public Long getId_comunidad() {
        return id_comunidad;
    }

    public void setId_comunidad(Long id_comunidad) {
        this.id_comunidad = id_comunidad;
    }

    public void setID_PROVINCIA(Integer id_provincia){
        this.id_provincia = id_provincia.longValue();
    }

    public Integer getID_PROVINCIA(){
        return id_provincia.intValue();
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_provincia", "id_pais"
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
            "id_provincia", "nombre", "id_pais", "cp_prefix", "g_code", "id_comunidad"
        };
    }

}

