package com.befasoft.common.actions;

import com.befasoft.common.beans.ErrorResult;
import com.befasoft.common.business.DBLogger;
import com.befasoft.common.model.EntityBean;

import java.util.*;

public class BodyResult {

    protected List elements = new ArrayList();
    protected List<ErrorResult> errors = null;
    protected int totalCount = 0;
    protected int currentPage = 0;
    protected boolean success = true;
    protected String message = null;
    protected Map<String, Object> properties = new HashMap<String, Object>();

    /**
     * Adiciona un error a la lista de errores
     * @param bean Bean en el que ocurrio el error
     * @param error Información del error
     */
    public void addError(EntityBean bean, String error) {
        if (errors == null)
            errors = new ArrayList<ErrorResult>();
        errors.add(new ErrorResult(bean, error));
        success = false;
        DBLogger.log(DBLogger.LEVEL_ERROR, "BEANMANAGER_ERROR", error);
    }

    /*
     * Metodos Get/Set
     */

    public String getMessage() {
        return message;
    }

    public String getMensaje() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List getElements() {
        return elements;
    }

    public void setElements(List elements) {
        this.elements = elements;
    }

    public int getTotalCount() {
        if (totalCount == 0 && elements.size() > 0) {
            totalCount= elements.size();
        }
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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

    public List<ErrorResult> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResult> errors) {
        this.errors = errors;
    }

}