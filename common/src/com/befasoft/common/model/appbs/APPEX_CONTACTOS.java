package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
@Entity
@Table(name = "APPEX_CONTACTOS")
public class APPEX_CONTACTOS extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CONTACTO")
    private Long id_contacto;

  
    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "MOVIL")
    private String movil;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "OBSERVACIONES")
    private String observaciones;


    /*
     * Set/Get Methods
     */
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public Long getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(Long id_contacto) {
        this.id_contacto = id_contacto;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_contacto"
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
            "telefono", "movil", "email", "nombre", "activo", "fax", "id_contacto", "observaciones"
        };
    }

}

