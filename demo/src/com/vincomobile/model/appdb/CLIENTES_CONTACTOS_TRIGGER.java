package com.vincomobile.model.appdb;

import com.befasoft.common.business.ActionTrigger;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.vincomobile.model.manager.CLIENTES_CONTACTOS_MANAGER;
import com.vincomobile.model.manager.CLIENTES_DIRECCIONES_MANAGER;
import org.hibernate.Session;

import java.util.List;

public class CLIENTES_CONTACTOS_TRIGGER implements ActionTrigger {

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
        APPBS_CAMPOS id_contacto = null;
        APPBS_CAMPOS ref_contacto_web = null;
        APPBS_CAMPOS ref_dir_web = null;
        APPBS_CAMPOS ref_cli_web = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("ID_CLIENTE".equals(campo.getNombre()))
                id_cliente = campo;
            if ("ID_DIRECCION".equals(campo.getNombre())) {
                id_direccion = campo;
                if (id_direccion != null && id_direccion.getValue() == null)
                    id_direccion.setValue(0L);
            }
            if ("ID_CONTACTO".equals(campo.getNombre()))
                id_contacto = campo;
            if ("REF_DIR_WEB".equals(campo.getNombre()))
                ref_dir_web = campo;
            if ("REF_CLI_WEB".equals(campo.getNombre()))
                ref_cli_web = campo;
            if ("REF_CONTACTO_WEB".equals(campo.getNombre()))
                ref_contacto_web = campo;
        }
        if (id_cliente != null && id_direccion != null && id_contacto != null && ref_contacto_web != null && ref_dir_web != null && ref_cli_web != null) {
            CLIENTES cliente = CLIENTES_DIRECCIONES_MANAGER.getMobileCliente(session, fields);
            if (cliente != null) {
                id_cliente.setValue(cliente.getId_cliente());
                ref_cli_web.setValue(cliente.getRef_web());
            }
            CLIENTES_DIRECCIONES direccion = CLIENTES_DIRECCIONES_MANAGER.getMobileDireccion(session, fields);
            if (direccion != null) {
                id_direccion.setValue(direccion.getId_direccion());
                ref_dir_web.setValue(direccion.getRef_dir_web());
            }
            Long id = CLIENTES_CONTACTOS_MANAGER.getNextId_contacto();
            id_contacto.setValue(id);
            ref_contacto_web.setValue(id);
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
