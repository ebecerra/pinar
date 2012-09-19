package com.befasoft.common.beans;

import java.util.HashMap;

/**
 * Clase para controlar eventos que demoren mucho tiempo y necesiten un feed al usuario (barra de progreso)
 * 
 */
public class ActionState {

    private static final HashMap<String, ActionStateElement> states = new HashMap<String, ActionStateElement>();

    /**
     * Obtiene un elemento
     * @param key Llave para buscar
     * @return Elemento
     */
    public ActionStateElement getStates(String key) {
        synchronized (states) {
            return states.get(key);
        }
    }

    /**
     * Borra un elemento
     * @param key Llave del elemento
     */
    public void deleteState(String key) {
        synchronized (states){
            states.remove(key);
        }
    }

    /**
     * Adiciona un elemento a la lista
     * @param element Elemento
     */
    public void addState(ActionStateElement element){
        synchronized (states) {
            states.put(element.getElementId(), element);
        }
    }

    /*
     * Metodos Get/Set
     */

    public Long getTotal(String key) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            return item != null ? item.getTotal() : 0;
        }
    }

    public void setTotal(String key, Long total) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            if (item != null)
                item.setTotal(total);
        }
    }

    public Long getCurrent(String key) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            return item != null ? item.getCurrent() : 0;
        }
    }
    
    public void setCurrent(String key, Long current) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            if (item != null)
                item.setCurrent(current);
        }
    }

    public void setNext(String key) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            if (item != null)
                item.setCurrent(item.getCurrent() + 1);
        }
    }

    public boolean getCancel(String key) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            return item != null ? item.isCancel() : true;
        }
    }

    public void setCancel(String key, boolean cancel) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            if (item != null)
                item.setCancel(cancel);
        }
    }

    public int getStatus(String key) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            return item != null ? item.getStatus() : ActionStateElement.STATE_FINISH_ERRR;
        }
    }

    public void setStatus(String key, int status) {
        synchronized (states){
            ActionStateElement item = states.get(key);
            if (item != null)
                item.setStatus(status);
        }
    }
}
