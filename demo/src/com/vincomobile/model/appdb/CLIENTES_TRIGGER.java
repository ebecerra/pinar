package com.vincomobile.model.appdb;

import com.befasoft.common.business.ActionTrigger;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.vincomobile.model.manager.CLIENTES_MANAGER;
import org.hibernate.Session;

import java.util.List;

public class CLIENTES_TRIGGER implements ActionTrigger {

    /**
     * Triggers que se llaman en una operacion de UPDATE
     *
     * @param session Session Hibernate
     * @param fields  Campos (value = nuevo valor, old_value = valores almacenados en DB)
     * @param nombre  Nombre de la tabla
     */
    public boolean beforeUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        return true;
    }

    public void afterUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre) {
    }

    /**
     * Triggers que se llaman en una operacion de INSERT
     *
     * @param session Session Hibernate
     * @param fields  Campos (value = nuevo valor, old_value = null)
     * @param nombre  Nombre de la tabla
     */
    public boolean beforeInsert(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        APPBS_CAMPOS id_cliente = null;
        APPBS_CAMPOS ref_web = null;
        APPBS_CAMPOS numero = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("ID_CLIENTE".equals(campo.getNombre()))
                id_cliente = campo;
            if ("REF_WEB".equals(campo.getNombre()))
                ref_web = campo;
            if ("NUMERO".equals(campo.getNombre()))
                numero = campo;
        }
        if (id_cliente != null && ref_web != null && numero != null) {
            numero.setValue(null);
            Long id = CLIENTES_MANAGER.getMobileId_cliente(session, fields);
            if (id == 0) {
                id = CLIENTES_MANAGER.getNextId_cliente();
                id_cliente.setValue(id);
                ref_web.setValue(id);
                return true;
            }
        }
        return false;
    }

    public void afterInsert(Session session, List<APPBS_CAMPOS> fields, String nombre) {
    }

    /**
     * Triggers que se llaman en una operacion de DELETE
     *
     * @param session Session Hibernate
     * @param fields  Campos (value = nuevo valor, old_value = null)
     * @param nombre  Nombre de la tabla
     */
    public boolean beforeDelete(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        return true;
    }

    public void afterDelete(Session session, List<APPBS_CAMPOS> fields, String nombre) {

    }

}
