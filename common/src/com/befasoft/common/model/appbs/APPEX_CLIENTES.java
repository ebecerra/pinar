package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
@Entity
@Table(name = "APPEX_CLIENTES")
public class APPEX_CLIENTES extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CLIENTE")
    private Long id_cliente;


    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "RAZON_SOCIAL")
    private String razon_social;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "MOVIL")
    private String movil;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "INT_PARAM_1")
    private Long int_param_1;

    @Column(name = "INT_PARAM_2")
    private Long int_param_2;

    @Column(name = "INT_PARAM_3")
    private Long int_param_3;

    @Column(name = "INT_PARAM_4")
    private Long int_param_4;

    @Column(name = "STR_PARAM_1")
    private String str_param_1;

    @Column(name = "STR_PARAM_2")
    private String str_param_2;

    @Column(name = "STR_PARAM_3")
    private String str_param_3;

    @Column(name = "STR_PARAM_4")
    private String str_param_4;


    /*
     * Set/Get Methods
     */
    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getInt_param_1() {
        return int_param_1;
    }

    public void setInt_param_1(Long int_param_1) {
        this.int_param_1 = int_param_1;
    }

    public Long getInt_param_2() {
        return int_param_2;
    }

    public void setInt_param_2(Long int_param_2) {
        this.int_param_2 = int_param_2;
    }

    public Long getInt_param_3() {
        return int_param_3;
    }

    public void setInt_param_3(Long int_param_3) {
        this.int_param_3 = int_param_3;
    }

    public Long getInt_param_4() {
        return int_param_4;
    }

    public void setInt_param_4(Long int_param_4) {
        this.int_param_4 = int_param_4;
    }

    public String getStr_param_1() {
        return str_param_1;
    }

    public void setStr_param_1(String str_param_1) {
        this.str_param_1 = str_param_1;
    }

    public String getStr_param_2() {
        return str_param_2;
    }

    public void setStr_param_2(String str_param_2) {
        this.str_param_2 = str_param_2;
    }

    public String getStr_param_3() {
        return str_param_3;
    }

    public void setStr_param_3(String str_param_3) {
        this.str_param_3 = str_param_3;
    }

    public String getStr_param_4() {
        return str_param_4;
    }

    public void setStr_param_4(String str_param_4) {
        this.str_param_4 = str_param_4;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "id_cliente"
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
                "id_cliente", "nombre", "razon_social", "cif", "email", "telefono", "movil", "activo", "int_param_1", "int_param_2", "int_param_3", "int_param_4", "str_param_1", "str_param_2", "str_param_3", "str_param_4"
        };
    }

}























