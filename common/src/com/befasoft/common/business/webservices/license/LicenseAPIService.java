
package com.befasoft.common.business.webservices.license;

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
@WebServiceClient(name = "LicenseAPIService", targetNamespace = "http://webservices.license.befasoft.com/", wsdlLocation = "http://localhost:8080/licenseAPI?wsdl")
public class LicenseAPIService
    extends Service
{

    private final static URL LICENSEAPISERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.befasoft.common.business.webservices.license.LicenseAPIService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.befasoft.common.business.webservices.license.LicenseAPIService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/licenseAPI?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/licenseAPI?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        LICENSEAPISERVICE_WSDL_LOCATION = url;
    }

    public LicenseAPIService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LicenseAPIService() {
        super(LICENSEAPISERVICE_WSDL_LOCATION, new QName("http://webservices.license.befasoft.com/", "LicenseAPIService"));
    }

    /**
     * 
     * @return
     *     returns License
     */
    @WebEndpoint(name = "LicenseAPIPort")
    public License getLicenseAPIPort() {
        return super.getPort(new QName("http://webservices.license.befasoft.com/", "LicenseAPIPort"), License.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns License
     */
    @WebEndpoint(name = "LicenseAPIPort")
    public License getLicenseAPIPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://webservices.license.befasoft.com/", "LicenseAPIPort"), License.class, features);
    }

}
