
package com.befasoft.common.business.webservices.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.befasoft.common.business.webservices.client package. 
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

    private final static QName _FinishSessionResponse_QNAME = new QName("http://server.webservices.business.common.befasoft.com/", "finishSessionResponse");
    private final static QName _CreateServerKey_QNAME = new QName("http://server.webservices.business.common.befasoft.com/", "createServerKey");
    private final static QName _IsValidSession_QNAME = new QName("http://server.webservices.business.common.befasoft.com/", "isValidSession");
    private final static QName _Exception_QNAME = new QName("http://server.webservices.business.common.befasoft.com/", "Exception");
    private final static QName _FinishSession_QNAME = new QName("http://server.webservices.business.common.befasoft.com/", "finishSession");
    private final static QName _IsValidSessionResponse_QNAME = new QName("http://server.webservices.business.common.befasoft.com/", "isValidSessionResponse");
    private final static QName _CreateServerKeyResponse_QNAME = new QName("http://server.webservices.business.common.befasoft.com/", "createServerKeyResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.befasoft.common.business.webservices.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateServerKeyResponse }
     * 
     */
    public CreateServerKeyResponse createCreateServerKeyResponse() {
        return new CreateServerKeyResponse();
    }

    /**
     * Create an instance of {@link FinishSessionResponse }
     * 
     */
    public FinishSessionResponse createFinishSessionResponse() {
        return new FinishSessionResponse();
    }

    /**
     * Create an instance of {@link IsValidSession }
     * 
     */
    public IsValidSession createIsValidSession() {
        return new IsValidSession();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link IsValidSessionResponse }
     * 
     */
    public IsValidSessionResponse createIsValidSessionResponse() {
        return new IsValidSessionResponse();
    }

    /**
     * Create an instance of {@link CreateServerKey }
     * 
     */
    public CreateServerKey createCreateServerKey() {
        return new CreateServerKey();
    }

    /**
     * Create an instance of {@link FinishSession }
     * 
     */
    public FinishSession createFinishSession() {
        return new FinishSession();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FinishSessionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservices.business.common.befasoft.com/", name = "finishSessionResponse")
    public JAXBElement<FinishSessionResponse> createFinishSessionResponse(FinishSessionResponse value) {
        return new JAXBElement<FinishSessionResponse>(_FinishSessionResponse_QNAME, FinishSessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateServerKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservices.business.common.befasoft.com/", name = "createServerKey")
    public JAXBElement<CreateServerKey> createCreateServerKey(CreateServerKey value) {
        return new JAXBElement<CreateServerKey>(_CreateServerKey_QNAME, CreateServerKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsValidSession }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservices.business.common.befasoft.com/", name = "isValidSession")
    public JAXBElement<IsValidSession> createIsValidSession(IsValidSession value) {
        return new JAXBElement<IsValidSession>(_IsValidSession_QNAME, IsValidSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservices.business.common.befasoft.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FinishSession }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservices.business.common.befasoft.com/", name = "finishSession")
    public JAXBElement<FinishSession> createFinishSession(FinishSession value) {
        return new JAXBElement<FinishSession>(_FinishSession_QNAME, FinishSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsValidSessionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservices.business.common.befasoft.com/", name = "isValidSessionResponse")
    public JAXBElement<IsValidSessionResponse> createIsValidSessionResponse(IsValidSessionResponse value) {
        return new JAXBElement<IsValidSessionResponse>(_IsValidSessionResponse_QNAME, IsValidSessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateServerKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservices.business.common.befasoft.com/", name = "createServerKeyResponse")
    public JAXBElement<CreateServerKeyResponse> createCreateServerKeyResponse(CreateServerKeyResponse value) {
        return new JAXBElement<CreateServerKeyResponse>(_CreateServerKeyResponse_QNAME, CreateServerKeyResponse.class, null, value);
    }

}
