package com.befasoft.common.business.webservices;

import com.befasoft.common.business.webservices.client.Session;
import com.befasoft.common.business.webservices.client.SessionAPIService;
import com.befasoft.common.tools.AlgorithmSHA1;

import javax.xml.namespace.QName;
import java.net.URL;

public class SessionWS {

    /**
     * Crea una sesion en el servidor
     *
     * @param server URL del servidor
     * @param dbUse Indica si hay acceso a datos en servidor
     * @return Id. de la sesion
     * @throws Exception Error
     */
    public static String createSession(String server, boolean dbUse) throws Exception {
        Session sessionAPI = new SessionAPIService(new URL(server + "sessionAPI?wsdl"), new QName("http://server.webservices.business.common.befasoft.com/", "SessionAPIService")).getSessionAPIPort();
        String clientKey = AlgorithmSHA1.generateKey();
        String serverKey = sessionAPI.createServerKey(clientKey, dbUse);
        return AlgorithmSHA1.createSessionID(clientKey, serverKey);
    }

    /**
     * Termina una sesion en el servidor
     *
     * @param server URL del servidor
     * @param sessionID Id. de la sesion
     * @param commitTrans Indica si se hace commit o rollback de las transacciones de DB
     * @throws Exception Error
     */
    public static void finishSession(String server, String sessionID, boolean commitTrans) throws Exception {
        Session sessionAPI = new SessionAPIService(new URL(server + "sessionAPI?wsdl"), new QName("http://server.webservices.business.common.befasoft.com/", "SessionAPIService")).getSessionAPIPort();
        sessionAPI.finishSession(sessionID, commitTrans);
    }

    /**
     * Verifica si una sesion es valida
     *
     * @param server URL del servidor
     * @param sessionID Id. de la sesion
     * @return Si el Id. de sesion es valido
     * @throws Exception Error
     */
    public static boolean isValidSession(String server, String sessionID) throws Exception {
        Session sessionAPI = new SessionAPIService(new URL(server + "sessionAPI?wsdl"), new QName("http://server.webservices.business.common.befasoft.com/", "SessionAPIService")).getSessionAPIPort();
        return sessionAPI.isValidSession(sessionID);
    }


}
