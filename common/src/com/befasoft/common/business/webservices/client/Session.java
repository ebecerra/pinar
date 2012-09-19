
package com.befasoft.common.business.webservices.client;

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
@WebService(name = "Session", targetNamespace = "http://server.webservices.business.common.befasoft.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Session {


    /**
     * 
     * @param useDB
     * @param clientKey
     * @return
     *     returns java.lang.String
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createServerKey", targetNamespace = "http://server.webservices.business.common.befasoft.com/", className = "com.befasoft.common.business.webservices.client.CreateServerKey")
    @ResponseWrapper(localName = "createServerKeyResponse", targetNamespace = "http://server.webservices.business.common.befasoft.com/", className = "com.befasoft.common.business.webservices.client.CreateServerKeyResponse")
    public String createServerKey(
        @WebParam(name = "clientKey", targetNamespace = "")
        String clientKey,
        @WebParam(name = "useDB", targetNamespace = "")
        Boolean useDB)
        throws Exception_Exception
    ;

    /**
     * 
     * @param sessionID
     * @param commitTrans
     */
    @WebMethod
    @RequestWrapper(localName = "finishSession", targetNamespace = "http://server.webservices.business.common.befasoft.com/", className = "com.befasoft.common.business.webservices.client.FinishSession")
    @ResponseWrapper(localName = "finishSessionResponse", targetNamespace = "http://server.webservices.business.common.befasoft.com/", className = "com.befasoft.common.business.webservices.client.FinishSessionResponse")
    public void finishSession(
        @WebParam(name = "sessionID", targetNamespace = "")
        String sessionID,
        @WebParam(name = "commitTrans", targetNamespace = "")
        Boolean commitTrans);

    /**
     * 
     * @param sessionID
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "isValidSession", targetNamespace = "http://server.webservices.business.common.befasoft.com/", className = "com.befasoft.common.business.webservices.client.IsValidSession")
    @ResponseWrapper(localName = "isValidSessionResponse", targetNamespace = "http://server.webservices.business.common.befasoft.com/", className = "com.befasoft.common.business.webservices.client.IsValidSessionResponse")
    public boolean isValidSession(
        @WebParam(name = "sessionID", targetNamespace = "")
        String sessionID);

}
