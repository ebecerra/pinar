
package com.befasoft.common.business.webservices.license;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "License", targetNamespace = "http://webservices.license.befasoft.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface License {


    /**
     * 
     * @param idAplicacion
     * @param sessionID
     * @param idCliente
     * @param idDistribuidor
     * @return
     *     returns java.util.List<com.befasoft.common.business.webservices.license.Licencia>
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLicense", targetNamespace = "http://webservices.license.befasoft.com/", className = "com.befasoft.common.business.webservices.license.GetLicense")
    @ResponseWrapper(localName = "getLicenseResponse", targetNamespace = "http://webservices.license.befasoft.com/", className = "com.befasoft.common.business.webservices.license.GetLicenseResponse")
    public List<Licencia> getLicense(
        @WebParam(name = "sessionID", targetNamespace = "")
        String sessionID,
        @WebParam(name = "id_distribuidor", targetNamespace = "")
        Long idDistribuidor,
        @WebParam(name = "id_aplicacion", targetNamespace = "")
        String idAplicacion,
        @WebParam(name = "id_cliente", targetNamespace = "")
        Long idCliente)
        throws Exception_Exception
    ;

}
