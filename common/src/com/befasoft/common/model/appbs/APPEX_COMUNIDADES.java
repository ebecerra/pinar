package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 07/05/2012
 */
@Entity
@Table(name = "APPEX_COMUNIDADES")
public class APPEX_COMUNIDADES extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_COMUNIDAD")
    private Long id_comunidad;


    @Column(name = "ID_PAIS")
    private Long id_pais;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "G_CODE")
    private String g_code;

    @ManyToOne
    @JoinColumn(name = "ID_PAIS", insertable = false, updatable = false)
    @Immutable
    private APPEX_PAISES pais = null;


    /*
     * Set/Get Methods
     */
    public Long getId_comunidad() {
        return id_comunidad;
    }

    public void setId_comunidad(Long id_comunidad) {
        this.id_comunidad = id_comunidad;
    }

    public Long getId_pais() {
        return id_pais;
    }

    public void setId_pais(Long id_pais) {
        this.id_pais = id_pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getG_code() {
        return g_code;
    }

    public void setG_code(String g_code) {
        this.g_code = g_code;
    }

    public APPEX_PAISES getPais() {
        return pais;
    }

    public void setPais(APPEX_PAISES pais) {
        this.pais = pais;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "id_comunidad"
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
                "id_comunidad", "id_pais", "nombre", "g_code"
        };
    }

}

