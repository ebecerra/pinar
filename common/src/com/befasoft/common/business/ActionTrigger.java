package com.befasoft.common.business;

import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import org.hibernate.Session;

import java.util.List;

public interface ActionTrigger {

    /**
     * Triggers que se llaman en una operacion de UPDATE
     * @param session Session Hibernate
     * @param fields Campos (value = nuevo valor, old_value = valores almacenados en DB)
     * @param nombre Nombre de la tabla
     * @return Indica si se realiza la action
     */
    public boolean beforeUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre);
    public void afterUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre);
    /**
     * Triggers que se llaman en una operacion de INSERT
     * @param session Session Hibernate
     * @param fields Campos (value = nuevo valor, old_value = null)
     * @param nombre Nombre de la tabla
     * @return Indica si se realiza la action
     */
    public boolean beforeInsert(Session session, List<APPBS_CAMPOS> fields, String nombre);
    public void afterInsert(Session session, List<APPBS_CAMPOS> fields, String nombre);
    /**
     * Triggers que se llaman en una operacion de DELETE
     * @param session Session Hibernate
     * @param fields Campos (value = nuevo valor, old_value = null)
     * @param nombre Nombre de la tabla
     * @return Indica si se realiza la action
     */
    public boolean beforeDelete(Session session, List<APPBS_CAMPOS> fields, String nombre);
    public void afterDelete(Session session, List<APPBS_CAMPOS> fields, String nombre);

}
