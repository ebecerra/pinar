package com.befasoft.common.model.business;

import com.befasoft.common.model.appbs.APPEX_LICENCIA;
import com.befasoft.common.model.appbs.APPEX_LICENCIA_ITEMS;
import com.befasoft.common.tools.Converter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "licencia", namespace = "", propOrder = {
        "id_licencia",
        "id_aplicacion",
        "id_distribuidor",
        "id_cliente",
        "fecha_inicio",
        "fecha_final",
        "fecha_actualizacion",
        "trial_tiempo",
        "trial_terminado",
        "activa",
        "hash_code",
        "hash_modulo",
        "hash_exponente",
        "hash_frase",
        "dias_invalida",
        "param_1",
        "param_2",
        "param_3",
        "param_4",
        "param_5",
        "items"
})
public class LICENCIA implements Serializable {
    @XmlElement(required = true)
    private Long id_licencia;
    @XmlElement(required = true)
    private String id_aplicacion;
    @XmlElement(required = true)
    private Long id_distribuidor;
    @XmlElement(required = true)
    private Long id_cliente;
    @XmlElement(required = true)
    private String fecha_inicio;
    @XmlElement(required = true)
    private String fecha_final;
    private String fecha_actualizacion;
    @XmlElement(required = true)
    private Long trial_tiempo;
    @XmlElement(required = true)
    private Boolean trial_terminado;
    @XmlElement(required = true)
    private Boolean activa;
    @XmlElement(required = true)
    private String hash_code;
    @XmlElement(required = true)
    private String hash_modulo;
    @XmlElement(required = true)
    private String hash_exponente;
    @XmlElement(required = true)
    private String hash_frase;
    @XmlElement(required = true)
    private Long dias_invalida;
    private String param_1;
    private String param_2;
    private String param_3;
    private String param_4;
    private String param_5;

    @XmlElement(required = true)
    private List<LICENCIA_ITEMS> items;

    public LICENCIA(APPEX_LICENCIA licencia) {
        this.id_licencia = licencia.getId_licencia();
        this.id_aplicacion = licencia.getId_aplicacion();
        this.id_distribuidor = licencia.getId_distribuidor();
        this.id_cliente = licencia.getId_cliente();
        this.fecha_inicio = Converter.formatDate(licencia.getFecha_inicio(), "dd/MM/yyyy");
        this.fecha_final = Converter.formatDate(licencia.getFecha_final(), "dd/MM/yyyy");
        this.fecha_actualizacion = Converter.formatDate(licencia.getFecha_actualizacion(), "dd/MM/yyyy");
        this.trial_tiempo = licencia.getTrial_tiempo();
        this.trial_terminado = licencia.getTrial_terminado();
        this.activa = licencia.getActiva();
        this.hash_code = licencia.getHash_code();
        this.hash_modulo = licencia.getHash_modulo();
        this.hash_exponente = licencia.getHash_exponente();
        this.hash_frase = licencia.getHash_frase();
        this.dias_invalida = licencia.getDias_invalida();
        this.param_1 = licencia.getParam_1();
        this.param_2 = licencia.getParam_2();
        this.param_3 = licencia.getParam_3();
        this.param_4 = licencia.getParam_4();
        this.param_5 = licencia.getParam_5();
    }

    public LICENCIA() {
    }

    /**
     * Retorna un objecto APPEX_LICENCIA
     *
     * @return APPEX_LICENCIA
     */
    public APPEX_LICENCIA getAppex_licencia() {
        APPEX_LICENCIA licencia = new APPEX_LICENCIA();
        licencia.setId_licencia(id_licencia);
        licencia.setId_aplicacion(id_aplicacion);
        licencia.setId_distribuidor(id_distribuidor);
        licencia.setId_cliente(id_cliente);
        licencia.setFecha_inicio(Converter.getDate(fecha_inicio, "dd/MM/yyyy"));
        licencia.setFecha_final(Converter.getDate(fecha_final, "dd/MM/yyyy"));
        licencia.setFecha_actualizacion(Converter.getDate(fecha_actualizacion, "dd/MM/yyyy"));
        licencia.setTrial_tiempo(trial_tiempo);
        licencia.setTrial_terminado(trial_terminado);
        licencia.setActiva(activa);
        licencia.setHash_code(hash_code);
        licencia.setHash_modulo(hash_modulo);
        licencia.setHash_exponente(hash_exponente);
        licencia.setHash_frase(hash_frase);
        licencia.setParam_1(param_1);
        licencia.setParam_2(param_2);
        licencia.setParam_3(param_3);
        licencia.setParam_4(param_4);
        licencia.setParam_5(param_5);
        List<APPEX_LICENCIA_ITEMS> licItems = new ArrayList<APPEX_LICENCIA_ITEMS>();
        for (LICENCIA_ITEMS item : items) {
            licItems.add(item.getAppex_licencia_items());
        }
        licencia.setItems(licItems);
        return licencia;
    }

    /**
     * Adiciona los valores a la licencia
     *
     * @param itemsList Lista de valores
     */
    public void addItems(List<APPEX_LICENCIA_ITEMS> itemsList) {
        items = new ArrayList<LICENCIA_ITEMS>();
        for (APPEX_LICENCIA_ITEMS licencia_items : itemsList) {
            if (licencia_items.getActivo())
                items.add(new LICENCIA_ITEMS(licencia_items));
        }
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

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public Long getId_distribuidor() {
        return id_distribuidor;
    }

    public void setId_distribuidor(Long id_distribuidor) {
        this.id_distribuidor = id_distribuidor;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Long getTrial_tiempo() {
        return trial_tiempo;
    }

    public void setTrial_tiempo(Long trial_tiempo) {
        this.trial_tiempo = trial_tiempo;
    }

    public Boolean getTrial_terminado() {
        return trial_terminado;
    }

    public void setTrial_terminado(Boolean trial_terminado) {
        this.trial_terminado = trial_terminado;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getHash_code() {
        return hash_code;
    }

    public void setHash_code(String hash_code) {
        this.hash_code = hash_code;
    }

    public String getHash_modulo() {
        return hash_modulo;
    }

    public void setHash_modulo(String hash_modulo) {
        this.hash_modulo = hash_modulo;
    }

    public String getHash_exponente() {
        return hash_exponente;
    }

    public void setHash_exponente(String hash_exponente) {
        this.hash_exponente = hash_exponente;
    }

    public String getHash_frase() {
        return hash_frase;
    }

    public void setHash_frase(String hash_frase) {
        this.hash_frase = hash_frase;
    }

    public Long getDias_invalida() {
        return dias_invalida;
    }

    public void setDias_invalida(Long dias_invalida) {
        this.dias_invalida = dias_invalida;
    }

    public String getParam_1() {
        return param_1;
    }

    public void setParam_1(String param_1) {
        this.param_1 = param_1;
    }

    public String getParam_2() {
        return param_2;
    }

    public void setParam_2(String param_2) {
        this.param_2 = param_2;
    }

    public String getParam_3() {
        return param_3;
    }

    public void setParam_3(String param_3) {
        this.param_3 = param_3;
    }

    public String getParam_4() {
        return param_4;
    }

    public void setParam_4(String param_4) {
        this.param_4 = param_4;
    }

    public String getParam_5() {
        return param_5;
    }

    public void setParam_5(String param_5) {
        this.param_5 = param_5;
    }

    public List<LICENCIA_ITEMS> getItems() {
        return items;
    }

    public void setItems(List<LICENCIA_ITEMS> items) {
        this.items = items;
    }

}
