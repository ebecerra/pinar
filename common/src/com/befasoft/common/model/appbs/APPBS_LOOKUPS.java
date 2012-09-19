package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 07/10/2011
 */
@Entity
@Table(name = "APPBS_LOOKUPS")
public class APPBS_LOOKUPS extends EntityBean {

    @Id
    @Column(name = "ID_LOOKUP")
    private String id_lookup;

  
    @Column(name = "ACTIVO")
    private String activo;

    @Column(name = "EDITABLE")
    private String editable;

    @Column(name = "NOMBRE")
    private String nombre;

    @OneToMany
    @JoinColumn(name = "ID_LOOKUP", referencedColumnName = "ID_LOOKUP", insertable = false, updatable = false)
    private List<APPBS_LOOKUPS_VALUES> valores = new ArrayList<APPBS_LOOKUPS_VALUES>();

    /*
     * Set/Get Methods
     */
    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getId_lookup() {
        return id_lookup;
    }

    public void setId_lookup(String id_lookup) {
        this.id_lookup = id_lookup;
    }

    public List<APPBS_LOOKUPS_VALUES> getValores() {
        return valores;
    }

    public void setValores(List<APPBS_LOOKUPS_VALUES> valores) {
        this.valores = valores;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_lookup"
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
            "activo", "editable", "nombre", "id_lookup"
        };
    }

}

