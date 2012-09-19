
package com.befasoft.common.business.webservices.license;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLicense complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLicense">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_distribuidor" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="id_aplicacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_cliente" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLicense", propOrder = {
    "sessionID",
    "idDistribuidor",
    "idAplicacion",
    "idCliente"
})
public class GetLicense {

    protected String sessionID;
    @XmlElement(name = "id_distribuidor")
    protected Long idDistribuidor;
    @XmlElement(name = "id_aplicacion")
    protected String idAplicacion;
    @XmlElement(name = "id_cliente")
    protected Long idCliente;

    /**
     * Gets the value of the sessionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionID(String value) {
        this.sessionID = value;
    }

    /**
     * Gets the value of the idDistribuidor property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdDistribuidor() {
        return idDistribuidor;
    }

    /**
     * Sets the value of the idDistribuidor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdDistribuidor(Long value) {
        this.idDistribuidor = value;
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
     * Gets the value of the idCliente property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdCliente() {
        return idCliente;
    }

    /**
     * Sets the value of the idCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdCliente(Long value) {
        this.idCliente = value;
    }

}
