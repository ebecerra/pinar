package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 07/07/2011
 */
@Entity
@Table(name = "CLIENTES_CONTACTOS")
public class CLIENTES_CONTACTOS extends EntityBean {

    @Id
    @Column(name = "ID_CLIENTE")
    private Long id_cliente;

    @Id
    @Column(name = "ID_CONTACTO")
    private Long id_contacto;

    @Id
    @Column(name = "ID_DIRECCION")
    private Long id_direccion;


    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "EXPORTABLE")
    private String exportable;

    @Column(name = "REF_DIR_WEB")
    private String ref_dir_web;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "REF_CONTACTO_WEB")
    private String ref_contacto_web;

    @Column(name = "REF_CLI_WEB")
    private String ref_cli_web;

    @Column(name = "FUENTE")
    private String fuente;

    @Column(name = "MOBILE_ID_CLIENTE")
    private Long mobile_id_cliente;

    @Column(name = "MOBILE_ID_DIRECCION")
    private Long mobile_id_direccion;

    @Column(name = "MOBILE_ID_CONTACTO")
    private Long mobile_id_contacto;

    @Column(name = "MOBILE_ID")
    private Long mobile_id;

    /*
     * Set/Get Methods
     */
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExportable() {
        return exportable;
    }

    public void setExportable(String exportable) {
        this.exportable = exportable;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Long getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(Long id_contacto) {
        this.id_contacto = id_contacto;
    }

    public String getRef_dir_web() {
        return ref_dir_web;
    }

    public void setRef_dir_web(String ref_dir_web) {
        this.ref_dir_web = ref_dir_web;
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

    public Long getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(Long id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRef_contacto_web() {
        return ref_contacto_web;
    }

    public void setRef_contacto_web(String ref_contacto_web) {
        this.ref_contacto_web = ref_contacto_web;
    }

    public String getRef_cli_web() {
        return ref_cli_web;
    }

    public void setRef_cli_web(String ref_cli_web) {
        this.ref_cli_web = ref_cli_web;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Long getMobile_id_cliente() {
        return mobile_id_cliente;
    }

    public void setMobile_id_cliente(Long mobile_id_cliente) {
        this.mobile_id_cliente = mobile_id_cliente;
    }

    public Long getMobile_id_direccion() {
        return mobile_id_direccion;
    }

    public void setMobile_id_direccion(Long mobile_id_direccion) {
        this.mobile_id_direccion = mobile_id_direccion;
    }

    public Long getMobile_id_contacto() {
        return mobile_id_contacto;
    }

    public void setMobile_id_contacto(Long mobile_id_contacto) {
        this.mobile_id_contacto = mobile_id_contacto;
    }

    public Long getMobile_id() {
        return mobile_id;
    }

    public void setMobile_id(Long mobile_id) {
        this.mobile_id = mobile_id;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_cliente", "id_contacto", "id_direccion"
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
            "telefono", "exportable", "id_cliente", "id_contacto", "ref_dir_web", "email", "nombre",
            "id_direccion", "fax", "ref_contacto_web", "ref_cli_web", "fuente",
            "mobile_id_cliente", "mobile_id_direccion", "mobile_id_contacto", "mobile_id"
        };
    }

}

