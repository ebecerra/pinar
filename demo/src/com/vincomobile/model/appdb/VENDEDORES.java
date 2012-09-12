package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 24/08/2011
 */
@Entity
@Table(name = "VENDEDORES")
public class VENDEDORES extends EntityBean {

    @Id
    @Column(name = "ID_VENDEDOR")
    private Long id_vendedor;


    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "PREFIJO_PEDIDOS")
    private Long prefijo_pedidos;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ID_ZONA")
    private String id_zona;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "CIF")
    private String cif;


    /*
     * Set/Get Methods
     */
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getPrefijo_pedidos() {
        return prefijo_pedidos;
    }

    public void setPrefijo_pedidos(Long prefijo_pedidos) {
        this.prefijo_pedidos = prefijo_pedidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_zona() {
        return id_zona;
    }

    public void setId_zona(String id_zona) {
        this.id_zona = id_zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(Long id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_vendedor"
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
            "telefono", "prefijo_pedidos", "email", "id_zona", "nombre", "id_vendedor", "fax", "cif"
        };
    }

}

