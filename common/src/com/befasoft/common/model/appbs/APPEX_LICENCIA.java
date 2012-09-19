package com.befasoft.common.model.appbs;

import com.befasoft.common.business.webservices.license.Licencia;
import com.befasoft.common.business.webservices.license.LicenciaITEMS;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Converter;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
@Entity
@Table(name = "APPEX_LICENCIA")
public class APPEX_LICENCIA extends EntityBean {

    @Id
    @Column(name = "ID_LICENCIA")
    private Long id_licencia;

    @Column(name = "ID_APLICACION")
    private String id_aplicacion;

    @Column(name = "ID_DISTRIBUIDOR")
    private Long id_distribuidor;

    @Column(name = "ID_CLIENTE")
    private Long id_cliente;

    @Column(name = "FECHA_INICIO")
    private Date fecha_inicio;

    @Column(name = "FECHA_FINAL")
    private Date fecha_final;

    @Column(name = "FECHA_ACTUALIZACION")
    private Date fecha_actualizacion;

    @Column(name = "TRIAL_TIEMPO")
    private Long trial_tiempo;

    @Column(name = "TRIAL_TERMINADO")
    private Boolean trial_terminado;

    @Column(name = "ACTIVA")
    private Boolean activa;

    @Column(name = "HASH_CODE")
    private String hash_code;

    @Column(name = "HASH_MODULO")
    private String hash_modulo;

    @Column(name = "HASH_EXPONENTE")
    private String hash_exponente;

    @Column(name = "HASH_FRASE")
    private String hash_frase;

    @Column(name = "DIAS_INVALIDA")
    private Long dias_invalida;

    @Column(name = "PARAM_1")
    private String param_1;

    @Column(name = "PARAM_2")
    private String param_2;

    @Column(name = "PARAM_3")
    private String param_3;

    @Column(name = "PARAM_4")
    private String param_4;

    @Column(name = "PARAM_5")
    private String param_5;

    @OneToMany
    @JoinColumn(name = "ID_LICENCIA", insertable = false, updatable = false)
    private List<APPEX_LICENCIA_ITEMS> items;
    
    @Transient
    private String cliente;

    /**
     * Obtiene una cadena con los valores que representan la licencia
     * 
     * @param sep Separador de Items
     * @param lineSep Separador de lineas
     * @return Cadena con el contenido de la licencia
     */
    public String getLicencia(String sep, String lineSep) {
        StringBuilder text = new StringBuilder();
        text.append(id_licencia).append(sep).append(id_aplicacion).append(sep).append(id_distribuidor).append(sep).append(id_cliente).append(sep);
        text.append(Converter.formatDate(fecha_inicio, "dd/MM/yyyy")).append(sep).append(Converter.formatDate(fecha_final, "dd/MM/yyyy")).append(sep);
        text.append(Converter.formatDate(fecha_actualizacion, "dd/MM/yyyy")).append(sep);
        text.append(trial_tiempo).append(sep).append(trial_terminado ? "S" : "N").append(sep).append(activa ? "S" : "N").append(sep);
        text.append(dias_invalida).append(sep).append(Converter.isEmpty(param_1) ? "" : param_1).append(sep).append(Converter.isEmpty(param_2) ? "" : param_2).append(sep);
        text.append(Converter.isEmpty(param_3) ? "" : param_3).append(sep).append(Converter.isEmpty(param_4) ? "" : param_4).append(sep);
        text.append(Converter.isEmpty(param_5) ? "" : param_5).append(lineSep);
        if(!Converter.isEmpty(items)) {
            for (APPEX_LICENCIA_ITEMS licencia_item : items) {
                if (licencia_item.getActivo()) {
                    text.append(licencia_item.getId_item()).append(sep).append(licencia_item.getTipo()).append(sep);
                    if ("I".equals(licencia_item.getTipo()))
                        text.append(licencia_item.getInt_valor());
                    else if ("S".equals(licencia_item.getTipo()))
                        text.append(licencia_item.getStr_valor());
                    else if ("D".equals(licencia_item.getTipo()))
                        text.append(Converter.formatDate(licencia_item.getDate_valor(), "dd/MM/yyyy"));
                    text.append(sep).append(licencia_item.getImp_tipo()).append(sep).append(Converter.formatFloat(licencia_item.getImporte(), 2)).append(lineSep);
                }
            }
        }
        return text.toString();
    }

    @JSON(serialize = false)
    public String getHtmlLicencia() {
        return getLicencia(" - ", "<br>");
    }

    @JSON(serialize = false)
    public String getTextLicencia() {
        return getLicencia(" - ", "\n");
    }

    @JSON(serialize = false)
    public String getPlainLicencia() {
        return getLicencia(":", ";");
    }

    /**
     * Actualiza la información de la licencia
     *
     * @param licencia Informacion de licencia
     */
    public void updateLicencia(Licencia licencia) {
        this.id_distribuidor = licencia.getIdDistribuidor();
        this.id_aplicacion = licencia.getIdAplicacion();
        this.id_cliente = licencia.getIdCliente();
        this.fecha_inicio = Converter.getDate(licencia.getFechaInicio(), "dd/MM/yyyy");
        this.fecha_final = Converter.getDate(licencia.getFechaFinal(), "dd/MM/yyyy");
        this.fecha_actualizacion = Converter.getDate(licencia.getFechaActualizacion(), "dd/MM/yyyy");
        this.trial_tiempo = licencia.getTrialTiempo();
        this.trial_terminado = licencia.isTrialTerminado();
        this.activa = licencia.isActiva();
        this.hash_code = licencia.getHashCode();
        this.hash_modulo = licencia.getHashModulo();
        this.hash_exponente = licencia.getHashExponente();
        this.hash_frase = licencia.getHashFrase();
        this.dias_invalida = licencia.getDiasInvalida();
        this.param_1 = licencia.getParam1();
        this.param_2 = licencia.getParam2();
        this.param_3 = licencia.getParam3();
        this.param_4 = licencia.getParam4();
        this.param_5 = licencia.getParam5();
        // Valores
        for (int i = 0; i < licencia.getItems().size(); i++) {
            LicenciaITEMS licenciaITEMS = licencia.getItems().get(i);
            APPEX_LICENCIA_ITEMS item = getLicencia_items(licenciaITEMS.getIdItem());
            if (item == null) {
                item = new APPEX_LICENCIA_ITEMS();
                item.setId_licencia(licencia.getIdLicencia());
                item.setId_item(licenciaITEMS.getIdItem());
                items.add(item);
            }
            item.updateLicencia(licenciaITEMS);
        }
    }

    public APPEX_LICENCIA_ITEMS getLicencia_items(String id_item) {
        if (items == null)
            items = new ArrayList<APPEX_LICENCIA_ITEMS>();
        for (APPEX_LICENCIA_ITEMS licencia_items : items) {
            if (licencia_items.getId_item().equals(id_item))
                return licencia_items;
        }
        return null;
    }

    /**
     * Obtiene el valor de un item de la licencia
     *
     * @param id_item Id. del item
     * @return Valor
     */
    public long getIntValue(String id_item) {
        APPEX_LICENCIA_ITEMS item = getLicencia_items(id_item);
        return item != null && item.getActivo() && "I".equals(item.getTipo()) ? item.getInt_valor() : null;
    }

    public String getStrValue(String id_item) {
        APPEX_LICENCIA_ITEMS item = getLicencia_items(id_item);
        return item != null && item.getActivo() && "S".equals(item.getTipo()) ? item.getStr_valor() : null;
    }

    public Date getDateValue(String id_item) {
        APPEX_LICENCIA_ITEMS item = getLicencia_items(id_item);
        return item != null && item.getActivo() && "D".equals(item.getTipo()) ? item.getDate_valor() : null;
    }

    public boolean isDefined(String id_item) {
        APPEX_LICENCIA_ITEMS item = getLicencia_items(id_item);
        return item != null && item.getActivo();
    }

    /**
     * Verifica si un item de la licencia es valido
     *
     * @param id_item Id. del item
     * @return Si es valido
     */
    public boolean isValid(String id_item) {
        APPEX_LICENCIA_ITEMS item = getLicencia_items(id_item);
        if (item != null && item.getActivo()) {
            if ("I".equals(item.getTipo()))
                return item.getInt_actual() == null || item.getInt_valor() == null ? true : item.getInt_actual() < item.getInt_valor();
            if ("D".equals(item.getTipo()))
                return item.getDate_actual() == null || item.getDate_valor() == null ? true : item.getDate_actual().before(item.getDate_valor());
            if ("S".equals(item.getTipo()))
                return item.getStr_valor() != null;
            if ("U".equals(item.getTipo()))
                return true;
        }
        return false;
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

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Long getTrial_tiempo() {
        return trial_tiempo;
    }

    public void setTrial_tiempo(Long trial_tiempo) {
        this.trial_tiempo = trial_tiempo;
    }

    public Long getTrial_remain() {
        if (!trial_terminado) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha_inicio);
            cal.add(Calendar.DAY_OF_YEAR, trial_tiempo.intValue());
            Date today = new Date();
            return ((cal.getTime().getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
        }
        return 0L;
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

    public List<APPEX_LICENCIA_ITEMS> getItems() {
        return items;
    }

    public void setItems(List<APPEX_LICENCIA_ITEMS> items) {
        this.items = items;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "id_licencia"
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
                "id_licencia", "id_distribuidor", "id_cliente", "id_aplicacion", "fecha_inicio", "fecha_final", "fecha_actualizacion", "trial_tiempo", "trial_terminado", "activa",
                "hash_code", "hash_exponente", "hash_modulo", "hash_frase", "dias_invalida", "param_1", "param_2", "param_3", "param_4", "param_5"
        };
    }

}

