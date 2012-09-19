package com.befasoft.common.business.webservices.server;

import com.befasoft.common.tools.AlgorithmSHA1;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "com.befasoft.common.business.webservices.server.Session")
public final class SessionAPI implements Session {

    private static Log log = LogFactory.getLog(SessionAPI.class);

    /**
     * Genera y retorna una serverKey, un sessionID y crea una nueva sesion asociada a este
     * @param clientKey Cadena a partir de la cual se generaro la Llave del Servidor
     * @param useDB Indica si se accede a DB
     * @return Llave del servidor
     * @throws Exception Error
     */
    @Override
    public String createServerKey(@WebParam(name = "clientKey") String clientKey, @WebParam(name = "useDB") Boolean useDB) throws Exception {

        String serverKey, sessionID;
        int i = 0;

        // Ciclo para intentar <Constants.SESSION_MAX_TRY> veces crear una serverKey y a partir de ella un SessionID y su sesion
        do {
            serverKey = AlgorithmSHA1.generateKey();
            sessionID = AlgorithmSHA1.createSessionID(clientKey, serverKey);
            log.debug("SessionAPI.createServerKey -> clientKey = "+clientKey+", serverKey = " + serverKey);

            // Limpia las sesiones antiguas
            UtilsWS.sessionControl();

            // Comprobar que no existe ninguna sesion con este IDSession
            if (!UtilsWS.isValidSessionID(sessionID)) {
                if (UtilsWS.addSessionID(sessionID)) {
                    SessionInfo session = UtilsWS.getSession(sessionID);

                    if (useDB) {
                        // Agregar la conexion a BBDD creada para esta sesion
                        try {
                            session.db_session = HibernateUtil.currentSession();
                            session.tx = session.db_session.beginTransaction();
                        } catch (Exception e) {
                            session.db_session = null;
                            log.error("SessionAPI.createServerKey -> Error de conexion a la DB: " + e.getMessage());
                        }
                    }
                }
            } else {
                // Si ya existia una sesion con este SessionID no me sirve la serverKey generada, entonces la elimino
                serverKey = null;
            }
            // Aumentar el contador que controla el numero de intentos de crear la nueva sesion
            i++;
        } while ((serverKey == null) && (i < Constants.SESSION_MAX_TRY));

        return serverKey;
    }

    /**
     * Termina la sesion y cierra la conexion a BBDD
     * @param sessionID ID de Sesion
     * @param commitTrans Define el modo de terminar la conexion, True hace commit, False rollback
     */
    @Override
    public void finishSession(@WebParam(name = "sessionID") String sessionID, @WebParam(name = "commitTrans") Boolean commitTrans) {

        // Comprobar que la aplicacion cliente que consulta el WebService tiene una sesion asociada
        if (UtilsWS.isValidSessionID(sessionID)) {
            try {
                // Cerrar la sesion asociada a este sessionID
                UtilsWS.removeSessionID(sessionID, commitTrans);
            } catch (Exception e) {
                log.error("SessionAPI.finishSession -> DB Error: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica si la sesion es validad
     * @param sessionID ID de Sesion
     * @return Verdadero/Falso si la session es valida o no
     */
    @Override
    public boolean isValidSession(@WebParam(name = "sessionID") String sessionID) {
        return UtilsWS.isValidSessionID(sessionID);
    }

}
