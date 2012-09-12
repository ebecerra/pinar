package com.vincomobile.model.business;

import com.befasoft.common.beans.LOGIN_INFO;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.tools.Converter;

/*
 * Descripcion de los parametros
 *
 *      int_param_1 : Id. del cliente o vendedor
 *      int_param_2 :
 *      param_1: Fecha de la penultima conexion
 *      param_2: Ultimo visualizador de precios
 *      param_3: Ultimo idioma
 */

public class USER_DEMO extends LOGIN_INFO {
    Long id_lista_prec, id_dir_fact, id_dir_env, numero;
    String id_moneda, email, email_comercial, moneda_simbolo, id_iva;
    String oper_comercial, oper_pp;
    String cliente_nombre;
    long pp_id_condpago, id_condpago, id_cliente, id_vendedor;
    double desc_comercial, desc_pp, precio_punto;
    boolean cliente_select;
    double multiplicador;

    public USER_DEMO(APPBS_USUARIOS user) {
        super(user);
        this.moneda_simbolo = "";
        this.id_vendedor = 0;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(long id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public Long getId_lista_prec() {
        return id_lista_prec;
    }

    public void setId_lista_prec(Long id_lista_prec) {
        this.id_lista_prec = id_lista_prec;
    }

    public Long getIdioma() {
        return Converter.getLong(getParam_3());
    }

    public String getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(String id_moneda) {
        this.id_moneda = id_moneda;
    }

    public Long getId_dir_fact() {
        return id_dir_fact;
    }

    public void setId_dir_fact(Long id_dir_fact) {
        this.id_dir_fact = id_dir_fact;
    }

    public Long getId_dir_env() {
        return id_dir_env;
    }

    public void setId_dir_env(Long id_dir_env) {
        this.id_dir_env = id_dir_env;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_comercial() {
        return email_comercial;
    }

    public void setEmail_comercial(String email_comercial) {
        this.email_comercial = email_comercial;
    }

    public String getOper_comercial() {
        return oper_comercial;
    }

    public void setOper_comercial(String oper_comercial) {
        this.oper_comercial = oper_comercial;
    }

    public String getOper_pp() {
        return oper_pp;
    }

    public void setOper_pp(String oper_pp) {
        this.oper_pp = oper_pp;
    }

    public void setDesc_pp(Long desc_pp) {
        this.desc_pp = desc_pp;
    }

    public long getPp_id_condpago() {
        return pp_id_condpago;
    }

    public void setPp_id_condpago(long pp_id_condpago) {
        this.pp_id_condpago = pp_id_condpago;
    }

    public long getId_condpago() {
        return id_condpago;
    }

    public void setId_condpago(long id_condpago) {
        this.id_condpago = id_condpago;
    }

    public double getDesc_comercial() {
        return desc_comercial;
    }

    public void setDesc_comercial(double desc_comercial) {
        this.desc_comercial = desc_comercial;
    }

    public double getDesc_pp() {
        return desc_pp;
    }

    public void setDesc_pp(double desc_pp) {
        this.desc_pp = desc_pp;
    }

    public String getMoneda_simbolo() {
        return moneda_simbolo;
    }

    public void setMoneda_simbolo(String moneda_simbolo) {
        this.moneda_simbolo = moneda_simbolo;
    }

    public double getPrecio_punto() {
        return precio_punto;
    }

    public void setPrecio_punto(double precio_punto) {
        this.precio_punto = precio_punto;
    }

    public String getId_iva() {
        return id_iva;
    }

    public void setId_iva(String id_iva) {
        this.id_iva = id_iva;
    }

    public boolean isCliente_select() {
        return cliente_select;
    }

    public void setCliente_select(boolean cliente_select) {
        this.cliente_select = cliente_select;
    }

    public String getCliente_nombre() {
        return cliente_nombre;
    }

    public void setCliente_nombre(String cliente_nombre) {
        this.cliente_nombre = cliente_nombre;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }
}
