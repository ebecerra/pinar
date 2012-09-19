package com.befasoft.common.model.business;

import com.befasoft.common.model.appbs.APPEX_LICENCIA_ITEMS;

import java.io.Serializable;
import java.util.Date;

public class LICENCIA_ITEMS implements Serializable {

    private Long id_licencia;
    private String id_item;
    private String nombre;
    private String tipo;
    private Long importe;
    private String imp_tipo;
    private Boolean activo;
    private Long int_valor;
    private Long int_actual;
    private String str_valor;
    private String str_actual;
    private Date date_valor;
    private Date date_actual;

    public LICENCIA_ITEMS(APPEX_LICENCIA_ITEMS item) {
        this.id_licencia = item.getId_licencia();
        this.id_item = item.getId_item();
        this.nombre = item.getNombre();
        this.tipo = item.getTipo();
        this.importe = item.getImporte();
        this.imp_tipo = item.getImp_tipo();
        this.activo = item.getActivo();
        this.int_valor = item.getInt_valor();
        this.int_actual = item.getInt_actual();
        this.str_valor = item.getStr_valor();
        this.str_actual = item.getStr_actual();
        this.date_valor = item.getDate_valor();
        this.date_actual = item.getDate_actual();
    }

    /**
     * Retorna un objecto APPEX_LICENCIA_ITEMS
     *
     * @return APPEX_LICENCIA_ITEMS
     */
    public APPEX_LICENCIA_ITEMS getAppex_licencia_items() {
        APPEX_LICENCIA_ITEMS item = new APPEX_LICENCIA_ITEMS();
        item.setId_licencia(id_licencia);
        item.setId_item(id_item);
        item.setNombre(nombre);
        item.setTipo(tipo);
        item.setImporte(importe);
        item.setImp_tipo(imp_tipo);
        item.setActivo(activo);
        item.setInt_valor(int_valor);
        item.setInt_actual(int_actual);
        item.setStr_valor(str_valor);
        item.setStr_actual(str_valor);
        item.setDate_valor(date_valor);
        item.setDate_actual(date_actual);
        return item;
    }

    /*
     * Metodos Get/Set
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

}
