package com.befasoft.common.business.webservices.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionInfo {

    public String sessionID;
    public long lastAction;
    public Session db_session;
    public Transaction tx;

    public SessionInfo(String sessionID, long lastAction) {
        this.sessionID = sessionID;
        this.lastAction = lastAction;
    }

}
