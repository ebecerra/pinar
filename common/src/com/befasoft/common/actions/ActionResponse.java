package com.befasoft.common.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionResponse {
    private boolean success = true;
    private Object item;
    private String mensaje;
    protected Map<String, Object> properties = new HashMap<String, Object>();


    public String getMensaje() {
        return mensaje;
    }

    public String getMessage() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void put(String name, Object value) {
        properties.put(name, value);
    }

    public void remove(String name) {
        properties.remove(name);
    }
}
