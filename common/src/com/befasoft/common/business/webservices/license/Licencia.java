
package com.befasoft.common.business.webservices.license;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for licencia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="licencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_licencia" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="id_aplicacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id_distribuidor" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="id_cliente" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="fecha_inicio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fecha_final" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fecha_actualizacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trial_tiempo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="trial_terminado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="activa" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hash_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hash_modulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hash_exponente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hash_frase" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dias_invalida" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="param_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="param_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="param_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="param_4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="param_5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="items" type="{http://webservices.license.befasoft.com/}licenciaITEMS" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "licencia", namespace = "", propOrder = {
    "idLicencia",
    "idAplicacion",
    "idDistribuidor",
    "idCliente",
    "fechaInicio",
    "fechaFinal",
    "fechaActualizacion",
    "trialTiempo",
    "trialTerminado",
    "activa",
    "hashCode",
    "hashModulo",
    "hashExponente",
    "hashFrase",
    "diasInvalida",
    "param1",
    "param2",
    "param3",
    "param4",
    "param5",
    "items"
})
public class Licencia {

    @XmlElement(name = "id_licencia")
    protected long idLicencia;
    @XmlElement(name = "id_aplicacion", required = true)
    protected String idAplicacion;
    @XmlElement(name = "id_distribuidor")
    protected long idDistribuidor;
    @XmlElement(name = "id_cliente")
    protected long idCliente;
    @XmlElement(name = "fecha_inicio", required = true)
    protected String fechaInicio;
    @XmlElement(name = "fecha_final", required = true)
    protected String fechaFinal;
    @XmlElement(name = "fecha_actualizacion")
    protected String fechaActualizacion;
    @XmlElement(name = "trial_tiempo")
    protected long trialTiempo;
    @XmlElement(name = "trial_terminado")
    protected boolean trialTerminado;
    protected boolean activa;
    @XmlElement(name = "hash_code", required = true)
    protected String hashCode;
    @XmlElement(name = "hash_modulo", required = true)
    protected String hashModulo;
    @XmlElement(name = "hash_exponente", required = true)
    protected String hashExponente;
    @XmlElement(name = "hash_frase", required = true)
    protected String hashFrase;
    @XmlElement(name = "dias_invalida")
    protected long diasInvalida;
    @XmlElement(name = "param_1")
    protected String param1;
    @XmlElement(name = "param_2")
    protected String param2;
    @XmlElement(name = "param_3")
    protected String param3;
    @XmlElement(name = "param_4")
    protected String param4;
    @XmlElement(name = "param_5")
    protected String param5;
    @XmlElement(required = true)
    protected List<LicenciaITEMS> items;

    /**
     * Gets the value of the idLicencia property.
     * 
     */
    public long getIdLicencia() {
        return idLicencia;
    }

    /**
     * Sets the value of the idLicencia property.
     * 
     */
    public void setIdLicencia(long value) {
        this.idLicencia = value;
    }

    /**
     * Gets the value of the idAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAplicacion() {
        return idAplicacion;
    }

    /**
     * Sets the value of the idAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAplicacion(String value) {
        this.idAplicacion = value;
    }

    /**
     * Gets the value of the idDistribuidor property.
     * 
     */
    public long getIdDistribuidor() {
        return idDistribuidor;
    }

    /**
     * Sets the value of the idDistribuidor property.
     * 
     */
    public void setIdDistribuidor(long value) {
        this.idDistribuidor = value;
    }

    /**
     * Gets the value of the idCliente property.
     * 
     */
    public long getIdCliente() {
        return idCliente;
    }

    /**
     * Sets the value of the idCliente property.
     * 
     */
    public void setIdCliente(long value) {
        this.idCliente = value;
    }

    /**
     * Gets the value of the fechaInicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the value of the fechaInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaInicio(String value) {
        this.fechaInicio = value;
    }

    /**
     * Gets the value of the fechaFinal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Sets the value of the fechaFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFinal(String value) {
        this.fechaFinal = value;
    }

    /**
     * Gets the value of the fechaActualizacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Sets the value of the fechaActualizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaActualizacion(String value) {
        this.fechaActualizacion = value;
    }

    /**
     * Gets the value of the trialTiempo property.
     * 
     */
    public long getTrialTiempo() {
        return trialTiempo;
    }

    /**
     * Sets the value of the trialTiempo property.
     * 
     */
    public void setTrialTiempo(long value) {
        this.trialTiempo = value;
    }

    /**
     * Gets the value of the trialTerminado property.
     * 
     */
    public boolean isTrialTerminado() {
        return trialTerminado;
    }

    /**
     * Sets the value of the trialTerminado property.
     * 
     */
    public void setTrialTerminado(boolean value) {
        this.trialTerminado = value;
    }

    /**
     * Gets the value of the activa property.
     * 
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * Sets the value of the activa property.
     * 
     */
    public void setActiva(boolean value) {
        this.activa = value;
    }

    /**
     * Gets the value of the hashCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashCode() {
        return hashCode;
    }

    /**
     * Sets the value of the hashCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashCode(String value) {
        this.hashCode = value;
    }

    /**
     * Gets the value of the hashModulo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashModulo() {
        return hashModulo;
    }

    /**
     * Sets the value of the hashModulo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashModulo(String value) {
        this.hashModulo = value;
    }

    /**
     * Gets the value of the hashExponente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashExponente() {
        return hashExponente;
    }

    /**
     * Sets the value of the hashExponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashExponente(String value) {
        this.hashExponente = value;
    }

    /**
     * Gets the value of the hashFrase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashFrase() {
        return hashFrase;
    }

    /**
     * Sets the value of the hashFrase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashFrase(String value) {
        this.hashFrase = value;
    }

    /**
     * Gets the value of the diasInvalida property.
     * 
     */
    public long getDiasInvalida() {
        return diasInvalida;
    }

    /**
     * Sets the value of the diasInvalida property.
     * 
     */
    public void setDiasInvalida(long value) {
        this.diasInvalida = value;
    }

    /**
     * Gets the value of the param1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParam1() {
        return param1;
    }

    /**
     * Sets the value of the param1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParam1(String value) {
        this.param1 = value;
    }

    /**
     * Gets the value of the param2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParam2() {
        return param2;
    }

    /**
     * Sets the value of the param2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParam2(String value) {
        this.param2 = value;
    }

    /**
     * Gets the value of the param3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParam3() {
        return param3;
    }

    /**
     * Sets the value of the param3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParam3(String value) {
        this.param3 = value;
    }

    /**
     * Gets the value of the param4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParam4() {
        return param4;
    }

    /**
     * Sets the value of the param4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParam4(String value) {
        this.param4 = value;
    }

    /**
     * Gets the value of the param5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParam5() {
        return param5;
    }

    /**
     * Sets the value of the param5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParam5(String value) {
        this.param5 = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LicenciaITEMS }
     * 
     * 
     */
    public List<LicenciaITEMS> getItems() {
        if (items == null) {
            items = new ArrayList<LicenciaITEMS>();
        }
        return this.items;
    }

}
