
package com.befasoft.common.business.webservices.license;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.befasoft.common.business.webservices.license package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetLicenseResponse_QNAME = new QName("http://webservices.license.befasoft.com/", "getLicenseResponse");
    private final static QName _GetLicense_QNAME = new QName("http://webservices.license.befasoft.com/", "getLicense");
    private final static QName _Exception_QNAME = new QName("http://webservices.license.befasoft.com/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.befasoft.common.business.webservices.license
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Licencia }
     * 
     */
    public Licencia createLicencia() {
        return new Licencia();
    }

    /**
     * Create an instance of {@link GetLicense }
     * 
     */
    public GetLicense createGetLicense() {
        return new GetLicense();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link GetLicenseResponse }
     * 
     */
    public GetLicenseResponse createGetLicenseResponse() {
        return new GetLicenseResponse();
    }

    /**
     * Create an instance of {@link LicenciaITEMS }
     * 
     */
    public LicenciaITEMS createLicenciaITEMS() {
        return new LicenciaITEMS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLicenseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.license.befasoft.com/", name = "getLicenseResponse")
    public JAXBElement<GetLicenseResponse> createGetLicenseResponse(GetLicenseResponse value) {
        return new JAXBElement<GetLicenseResponse>(_GetLicenseResponse_QNAME, GetLicenseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLicense }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.license.befasoft.com/", name = "getLicense")
    public JAXBElement<GetLicense> createGetLicense(GetLicense value) {
        return new JAXBElement<GetLicense>(_GetLicense_QNAME, GetLicense.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.license.befasoft.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
