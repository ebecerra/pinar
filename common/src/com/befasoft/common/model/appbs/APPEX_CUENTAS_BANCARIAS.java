package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
@Entity
@Table(name = "APPEX_CUENTAS_BANCARIAS")
public class APPEX_CUENTAS_BANCARIAS extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CUENTA")
    private Long id_cuenta;

    @Column(name = "DC")
    private String dc;

    @Column(name = "ACTIVA")
    private Boolean activa;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "SUCURSAL")
    private String sucursal;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ENTIDAD")
    private String entidad;

    @Column(name = "CUENTA")
    private String cuenta;


    /*
     * Set/Get Methods
     */
    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
    
    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    public Long getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(Long id_cuenta) {
        this.id_cuenta = id_cuenta;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }
    
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    public String getFullCuenta() {
        return entidad+"-"+sucursal+"-"+dc+"-"+cuenta;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_cuenta"
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
            "dc", "activa", "iban", "sucursal", "id_cuenta", "nombre", "entidad", "cuenta"
        };
    }

}

