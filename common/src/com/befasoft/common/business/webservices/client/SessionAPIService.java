
package com.befasoft.common.business.webservices.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "SessionAPIService", targetNamespace = "http://server.webservices.business.common.befasoft.com/", wsdlLocation = "http://localhost:8080/sessionAPI?wsdl")
public class SessionAPIService
    extends Service
{

    private final static URL SESSIONAPISERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.befasoft.common.business.webservices.client.SessionAPIService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.befasoft.common.business.webservices.client.SessionAPIService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/sessionAPI?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/sessionAPI?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        SESSIONAPISERVICE_WSDL_LOCATION = url;
    }

    public SessionAPIService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SessionAPIService() {
        super(SESSIONAPISERVICE_WSDL_LOCATION, new QName("http://server.webservices.business.common.befasoft.com/", "SessionAPIService"));
    }

    /**
     * 
     * @return
     *     returns Session
     */
    @WebEndpoint(name = "SessionAPIPort")
    public Session getSessionAPIPort() {
        return super.getPort(new QName("http://server.webservices.business.common.befasoft.com/", "SessionAPIPort"), Session.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Session
     */
    @WebEndpoint(name = "SessionAPIPort")
    public Session getSessionAPIPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://server.webservices.business.common.befasoft.com/", "SessionAPIPort"), Session.class, features);
    }

}