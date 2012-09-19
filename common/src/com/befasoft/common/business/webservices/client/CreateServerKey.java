
package com.befasoft.common.business.webservices.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createServerKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createServerKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useDB" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createServerKey", propOrder = {
    "clientKey",
    "useDB"
})
public class CreateServerKey {

    protected String clientKey;
    protected Boolean useDB;

    /**
     * Gets the value of the clientKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientKey() {
        return clientKey;
    }

    /**
     * Sets the value of the clientKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientKey(String value) {
        this.clientKey = value;
    }

    /**
     * Gets the value of the useDB property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseDB() {
        return useDB;
    }

    /**
     * Sets the value of the useDB property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseDB(Boolean value) {
        this.useDB = value;
    }

}
