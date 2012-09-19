package com.befasoft.common.business.webservices.server;

import com.befasoft.common.tools.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Hashtable;

public final class UtilsWS {

    private static Log log = LogFactory.getLog(UtilsWS.class);

    // Contenedor donde se almacena la informacion de todas las sesiones
    private static final Hashtable sessionList = new Hashtable();

    /**
     * Agrega una nueva sesion identificada por IDSession
     * @param sessionID ID de sesion
     * @return True si ha creado la sesion correctamente, False en otro caso
     * @throws Exception Error
     */
    public static boolean addSessionID(String sessionID) throws Exception {
        log.debug("UtilsWS.addSessionID("+sessionID+")");

        boolean sesionOK = false;
        // Comprobar que la aplicacion cliente que consulta el WebService tiene una sesion asociada
        SessionInfo session = (SessionInfo) sessionList.get(sessionID);
        if (session == null) {
            // Crea la sesion
            session = new SessionInfo(sessionID, Calendar.getInstance().getTimeInMillis());
            sessionList.put(sessionID, session);
            sesionOK = true;
            log.debug("Se ha iniciado una nueva sesión.");
            log.debug("Número de sesiones activas: " + String.valueOf(getSessionListSize()));
        }
        return sesionOK;
    }

    /**
     * Elimina la sesion identificada por IDSession
     * @param sessionID ID de sesion
     * @param commitTrans Define el modo de terminar la conexion asociada a la sesion,
     *        True hace commit, False rollback
     */
    public static void removeSessionID(String sessionID, boolean commitTrans) {
        // Obtener el hash que almacena los datos de la sesion identificada por este IDSession
        SessionInfo session = (SessionInfo) sessionList.get(sessionID);
        if (session != null) {
            if (session.db_session != null) {
                try {
                    if (commitTrans) {
                        session.tx.commit();
                        log.debug("Modificaciones guardadas.");
                    } else {
                        log.debug("Cancelando las modificaciones...");
                        session.tx.rollback();
                    }
                } catch (Exception e) {
                    log.error("UtilsWS.removeSessionID -> Error en el COMMIT o ROLLBACK en la DB: " + e.getMessage());
                } finally {
                    try {
                        session.db_session.close();
                    } catch (Exception sqlE) {
                        log.error("UtilsWS.removeSessionID -> Error cerrando la conexion de la DB: " + sqlE.getMessage());
                    }
                }
            }
            // Eliminar la sesion de la lista de sesiones
            sessionList.remove(sessionID);
        }
        log.debug("Se ha finalizado una sesión.");
        log.debug("Número de sesiones activas: " + String.valueOf(getSessionListSize()));
    }

    /**
     * Verifica si existe o no la sesion identificada por IDSession.
     * @param sessionID ID de sesion
     * @return True si es una sesion activa, False en otro caso
     */
    public static boolean isValidSessionID(String sessionID) {
        // Actualizar la ultima fecha de accion realizada
        SessionInfo session = (SessionInfo) sessionList.get(sessionID);
        if (session != null) {
            session.lastAction = Calendar.getInstance().getTimeInMillis();
            return true;
        } else
            return false;
    }

    /**
     * Retorna la cantidad de sesiones activas en el momento actual
     * @return Cantidad de sesiones activas en el momento actual
     */
    public static int getSessionListSize() {
        return sessionList.size();
    }

    /**
     * Obtine la informacion de una sesion por el ID
     *
     * @param sessionID ID de Sesion
     * @return Informacion de la sesion
     */
    public static SessionInfo getSession(String sessionID) {
        return (SessionInfo) sessionList.get(sessionID);
    }

    /**
     * Ejecuta las acciones necesarias para eliminar las sesiones que han expirado
     * cada vez que es invocado.
     */
    public static void sessionControl() {
        try {
            Object[] listH = sessionList.values().toArray();
            long tms = Calendar.getInstance().getTimeInMillis();
            long maxIT = Constants.SESSION_MAX_IDLE_TIME_VALUE;
            // Recorrer el Array de sesiones
            for (Object aListH : listH) {
                SessionInfo session = (SessionInfo) aListH;
                // Comprobar que a esta sesion le corresponde destruirse, comprobando que su fecha y hora
                // de ultima accion es mayor o igual que el maximo tiempo de inactividad permitido
                if (maxIT < (tms - session.lastAction)) {
                    log.debug("Sesión que ha revasado el tiempo m�ximo de inactividad: " + session.sessionID);
                    log.debug("Cerrando sesión " + session.sessionID + " ...");
                    removeSessionID(session.sessionID, false);
                }
            }
        } catch (Exception e) {
            log.error("UtilsWS.sessionControl() -> ERROR: " + e.getMessage());
        }
    }

}
