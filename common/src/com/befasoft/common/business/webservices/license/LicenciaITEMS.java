
package com.befasoft.common.business.webservices.license;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for licenciaITEMS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="licenciaITEMS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="date_actual" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="date_valor" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id_item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_licencia" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="imp_tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="int_actual" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="int_valor" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_actual" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_valor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "licenciaITEMS", propOrder = {
    "activo",
    "dateActual",
    "dateValor",
    "idItem",
    "idLicencia",
    "impTipo",
    "importe",
    "intActual",
    "intValor",
    "nombre",
    "strActual",
    "strValor",
    "tipo"
})
public class LicenciaITEMS {

    protected Boolean activo;
    @XmlElement(name = "date_actual")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateActual;
    @XmlElement(name = "date_valor")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateValor;
    @XmlElement(name = "id_item")
    protected String idItem;
    @XmlElement(name = "id_licencia")
    protected Long idLicencia;
    @XmlElement(name = "imp_tipo")
    protected String impTipo;
    protected Long importe;
    @XmlElement(name = "int_actual")
    protected Long intActual;
    @XmlElement(name = "int_valor")
    protected Long intValor;
    protected String nombre;
    @XmlElement(name = "str_actual")
    protected String strActual;
    @XmlElement(name = "str_valor")
    protected String strValor;
    protected String tipo;

    /**
     * Gets the value of the activo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActivo() {
        return activo;
    }

    /**
     * Sets the value of the activo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActivo(Boolean value) {
        this.activo = value;
    }

    /**
     * Gets the value of the dateActual property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateActual() {
        return dateActual;
    }

    /**
     * Sets the value of the dateActual property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateActual(XMLGregorianCalendar value) {
        this.dateActual = value;
    }

    /**
     * Gets the value of the dateValor property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateValor() {
        return dateValor;
    }

    /**
     * Sets the value of the dateValor property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateValor(XMLGregorianCalendar value) {
        this.dateValor = value;
    }

    /**
     * Gets the value of the idItem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdItem() {
        return idItem;
    }

    /**
     * Sets the value of the idItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdItem(String value) {
        this.idItem = value;
    }

    /**
     * Gets the value of the idLicencia property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdLicencia() {
        return idLicencia;
    }

    /**
     * Sets the value of the idLicencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdLicencia(Long value) {
        this.idLicencia = value;
    }

    /**
     * Gets the value of the impTipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpTipo() {
        return impTipo;
    }

    /**
     * Sets the value of the impTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpTipo(String value) {
        this.impTipo = value;
    }

    /**
     * Gets the value of the importe property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getImporte() {
        return importe;
    }

    /**
     * Sets the value of the importe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setImporte(Long value) {
        this.importe = value;
    }

    /**
     * Gets the value of the intActual property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIntActual() {
        return intActual;
    }

    /**
     * Sets the value of the intActual property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIntActual(Long value) {
        this.intActual = value;
    }

    /**
     * Gets the value of the intValor property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIntValor() {
        return intValor;
    }

    /**
     * Sets the value of the intValor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIntValor(Long value) {
        this.intValor = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the strActual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrActual() {
        return strActual;
    }

    /**
     * Sets the value of the strActual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrActual(String value) {
        this.strActual = value;
    }

    /**
     * Gets the value of the strValor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrValor() {
        return strValor;
    }

    /**
     * Sets the value of the strValor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrValor(String value) {
        this.strValor = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

}
