package com.befasoft.common.model.appbs;

import com.befasoft.common.business.webservices.license.LicenciaITEMS;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Datetool;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
@Entity
@Table(name = "APPEX_LICENCIA_ITEMS")
public class APPEX_LICENCIA_ITEMS extends EntityBean {

    @Id
    @Column(name = "ID_LICENCIA")
    private Long id_licencia;

    @Id
    @Column(name = "ID_ITEM")
    private String id_item;


    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "IMPORTE")
    private Long importe;

    @Column(name = "IMP_TIPO")
    private String imp_tipo;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "INT_VALOR")
    private Long int_valor;

    @Column(name = "INT_ACTUAL")
    private Long int_actual;

    @Column(name = "STR_VALOR")
    private String str_valor;

    @Column(name = "STR_ACTUAL")
    private String str_actual;

    @Column(name = "DATE_VALOR")
    private Date date_valor;

    @Column(name = "DATE_ACTUAL")
    private Date date_actual;

    @Transient
    private boolean updated = false;

    /**
     * Actualiza la información de la licencia
     *
     * @param licencia Informacion de licencia
     */
    public void updateLicencia(LicenciaITEMS licencia) {
        this.nombre = licencia.getNombre();
        this.tipo = licencia.getTipo();
        this.importe = licencia.getImporte();
        this.imp_tipo = licencia.getImpTipo();
        this.activo = licencia.isActivo();
        this.int_valor = licencia.getIntValor();
        //this.int_actual = licencia.getIntActual();
        this.str_valor = licencia.getStrValor();
        //this.str_actual = licencia.getStrActual();
        this.date_valor = Datetool.getDate(licencia.getDateValor());
        //this.date_actual = Datetool.getDate(licencia.getDateActual());
        this.updated = true;
    }

    /*
     * Set/Get Methods
     */
    public Long getId_licencia() {
        return id_licencia;
    }

    public void setId_licencia(Long id_licencia) {
        this.id_licencia = id_licencia;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getImporte() {
        return importe;
    }

    public void setImporte(Long importe) {
        this.importe = importe;
    }

    public String getImp_tipo() {
        return imp_tipo;
    }

    public void setImp_tipo(String imp_tipo) {
        this.imp_tipo = imp_tipo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getInt_valor() {
        return int_valor;
    }

    public void setInt_valor(Long int_valor) {
        this.int_valor = int_valor;
    }

    public Long getInt_actual() {
        return int_actual;
    }

    public void setInt_actual(Long int_actual) {
        this.int_actual = int_actual;
    }

    public String getStr_valor() {
        return str_valor;
    }

    public void setStr_valor(String str_valor) {
        this.str_valor = str_valor;
    }

    public String getStr_actual() {
        return str_actual;
    }

    public void setStr_actual(String str_actual) {
        this.str_actual = str_actual;
    }

    public Date getDate_valor() {
        return date_valor;
    }

    public void setDate_valor(Date date_valor) {
        this.date_valor = date_valor;
    }

    public Date getDate_actual() {
        return date_actual;
    }

    public void setDate_actual(Date date_actual) {
        this.date_actual = date_actual;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "id_licencia", "id_item"
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
                "id_licencia", "id_item", "nombre", "tipo", "importe", "imp_tipo", "activo", "int_valor", "int_actual", "str_valor", "str_actual", "date_valor", "date_actual"
        };
    }

}

