package com.befasoft.common.servlet;

import com.befasoft.common.beans.LOGIN_INFO;
import com.befasoft.common.model.business.SESSION_INFO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

public class SessionManager implements HttpSessionListener {

    private static Log log = LogFactory.getLog(SessionManager.class);
    public static HashMap<String, HttpSession> activeSessions = new HashMap<String, HttpSession>();
    public static int countSession = 0;

    /**
     * Se crea una sesion
     *
     * @param httpSessionEvent Evento de la sesion
     */
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        countSession++;
        String sessionid = httpSessionEvent.getSession().getId();
        log.debug("sessionCreated -> id = " + sessionid + ", total = " + countSession);
        activeSessions.put(sessionid, httpSessionEvent.getSession());
    }

    /**
     * Se elimina una sesion
     *
     * @param httpSessionEvent Evento de la sesion
     */
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        countSession--;
        String sessionID = httpSessionEvent.getSession().getId();
        log.debug("sessionDestroyed -> id = " + sessionID + ", total = " + countSession);
        activeSessions.remove(sessionID);
    }

    /**
     * Verifica si el usuario tiene mas sesiones abiertas
     *
     * @param sessionId Id. de la sesion del usuario que se verifica
     * @param user Usuario que se verifica
     * @param checkMobile Toma en cuenta las sesiones mobiles y web
     * @return Verdadero si tiene mas sesiones
     */
    public static boolean userHasSession(String sessionId, LOGIN_INFO user, boolean checkMobile) {
        log.debug("userHasSession("+user.getLogin()+")");
        Collection values = activeSessions.values();
        for (Object value : values) {
            HttpSession session = (HttpSession) value;
            LOGIN_INFO sUser = (LOGIN_INFO) session.getAttribute("user");
            if (sUser != null && user.getId_usuario() == sUser.getId_usuario() && !sessionId.equals(session.getId())) {
                if (!checkMobile || user.isMobile() == sUser.isMobile())
                    return true;
            }
        }
        return false;
    }

    public static boolean userHasSession(String sessionId, LOGIN_INFO user) {
        return userHasSession(sessionId, user, false);
    }

    /**
     * Elimina la sesiones anteriores del usuario
     *
     * @param user Usuario que se elimina
     * @param checkMobile Toma en cuenta las sesiones mobiles y web
     */
    public static void removePreviusSessions(LOGIN_INFO user, boolean checkMobile) {
        log.debug("removePreviusSessions("+user.getLogin()+")");
        Collection values = activeSessions.values();
        for (Object value : values) {
            HttpSession session = (HttpSession) value;
            LOGIN_INFO sUser = (LOGIN_INFO) session.getAttribute("user");
            if (sUser != null && user.getId_usuario() == sUser.getId_usuario()) {
                if (!checkMobile || user.isMobile() == sUser.isMobile())
                    session.setAttribute("user", null);
            }
        }
    }

    public static void removePreviusSessions(LOGIN_INFO user) {
        removePreviusSessions(user, false);
    }

    /**
     * Elimina el usuario de una session
     *
     * @param sessionId Id. de la sesion
     */
    public static void removeSession(String sessionId) {
        log.debug("removeSession("+sessionId+")");
        HttpSession session = activeSessions.get(sessionId);
        if (session != null) {
            session.setAttribute("user", null);
        }
    }

    /**
     * Lista las sesiones activas
     *
     * @return Lista de las sesiones activas
     */
    public static List<SESSION_INFO> listUserSessions() {
        log.debug("listUserSessions()");
        List<SESSION_INFO> result = new ArrayList<SESSION_INFO>();
        Collection values = activeSessions.values();
        for (Object value : values) {
            HttpSession session = (HttpSession) value;
            LOGIN_INFO sUser = (LOGIN_INFO) session.getAttribute("user");
            if (sUser != null) {
                SESSION_INFO info = new SESSION_INFO(sUser.getId_usuario(), sUser.getLogin(), sUser.getNombre());
                info.setCreation_time(new Date(session.getCreationTime()));
                info.setLast_access(new Date(session.getLastAccessedTime()));
                info.setSessionId(session.getId());
                info.setMobile(sUser.isMobile());
                info.setUser_agent(sUser.getUser_agent());
                result.add(info);
            }
        }
        return result;
    }
    
}
