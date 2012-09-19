package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 28/12/2011
 */
@Entity
@Table(name = "APPBS_FOREINGKEYS")
public class APPBS_FOREINGKEYS extends EntityBean {

    @Id
    @Column(name = "ID_KEY")
    private Long id_key;

    @Column(name = "ID_TABLA")
    private Long id_tabla;

    @Column(name = "EXPORTABLE")
    private Boolean exportable;

    @Column(name = "NOMBRE")
    private String nombre;

    @OneToMany
    @JoinColumn(name = "ID_KEY", referencedColumnName = "ID_KEY", insertable = false, updatable = false)
    private List<APPBS_FOREINGKEYS_VALUES> valores = new ArrayList<APPBS_FOREINGKEYS_VALUES>();


    /*
     * Set/Get Methods
     */
    public Boolean getExportable() {
        return exportable;
    }

    public void setExportable(Boolean exportable) {
        this.exportable = exportable;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Long getId_key() {
        return id_key;
    }

    public void setId_key(Long id_key) {
        this.id_key = id_key;
    }

    public List<APPBS_FOREINGKEYS_VALUES> getValores() {
        return valores;
    }

    public void setValores(List<APPBS_FOREINGKEYS_VALUES> valores) {
        this.valores = valores;
    }

    public Long getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(Long id_tabla) {
        this.id_tabla = id_tabla;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_key"
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
            "exportable", "nombre", "id_key", "id_tabla"
        };
    }

}

