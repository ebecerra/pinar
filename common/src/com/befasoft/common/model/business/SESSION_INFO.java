package com.befasoft.common.model.business;

import com.befasoft.common.tools.Converter;

import java.util.Date;

public class SESSION_INFO {
    
    long user_id;
    String user_login, user_name, sessionId, user_agent;
    Date creation_time;
    Date last_access;
    boolean mobile;

    public SESSION_INFO(Long user_id, String user_login, String user_name) {
        this.user_id = user_id;
        this.user_login = user_login;
        this.user_name = user_name;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public Date getLast_access() {
        return last_access;
    }

    public String getLast_access_str() {
        return last_access != null ? Converter.formatDate(last_access, "dd/MM/yyyy HH:mm:ss") : "";
    }

    public String getCreation_time_str() {
        return creation_time != null ? Converter.formatDate(creation_time, "dd/MM/yyyy HH:mm:ss") : "";
    }

    public void setLast_access(Date last_access) {
        this.last_access = last_access;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }
}
