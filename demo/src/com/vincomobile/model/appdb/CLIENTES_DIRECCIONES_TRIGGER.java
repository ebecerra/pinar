package com.vincomobile.model.appdb;

import com.befasoft.common.business.ActionTrigger;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.tools.Converter;
import com.vincomobile.model.manager.CLIENTES_DIRECCIONES_MANAGER;
import org.hibernate.Session;

import java.util.List;

public class CLIENTES_DIRECCIONES_TRIGGER implements ActionTrigger {

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
        APPBS_CAMPOS id_direccion = null;
        APPBS_CAMPOS ref_dir_web = null;
        APPBS_CAMPOS ref_cli_web = null;
        APPBS_CAMPOS principal = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("ID_CLIENTE".equals(campo.getNombre()))
                id_cliente = campo;
            if ("ID_DIRECCION".equals(campo.getNombre()))
                id_direccion = campo;
            if ("REF_DIR_WEB".equals(campo.getNombre()))
                ref_dir_web = campo;
            if ("REF_CLI_WEB".equals(campo.getNombre()))
                ref_cli_web = campo;
            if ("PRINCIPAL".equals(campo.getNombre()))
                principal = campo;
        }
        if (id_cliente != null && id_direccion != null && ref_dir_web != null && ref_cli_web != null && principal != null) {
            principal.setValue(Converter.getBoolean(principal.getValue()) ? "Y" : "N");
            CLIENTES cliente = CLIENTES_DIRECCIONES_MANAGER.getMobileCliente(session, fields);
            if (cliente != null) {
                id_cliente.setValue(cliente.getId_cliente());
                ref_cli_web.setValue(cliente.getRef_web());
            }
            Long id = CLIENTES_DIRECCIONES_MANAGER.getNextId_direccion();
            id_direccion.setValue(id);
            ref_dir_web.setValue(id);
            return true;
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
