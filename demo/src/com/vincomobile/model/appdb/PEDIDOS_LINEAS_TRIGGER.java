package com.vincomobile.model.appdb;

import com.befasoft.common.business.ActionTrigger;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.tools.Converter;
import com.vincomobile.model.manager.PEDIDOS_LINEAS_MANAGER;
import com.vincomobile.model.manager.PEDIDOS_MANAGER;
import org.hibernate.Session;

import java.util.List;

public class PEDIDOS_LINEAS_TRIGGER implements ActionTrigger {

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
        APPBS_CAMPOS pedido_num = null;
        APPBS_CAMPOS linea_num = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("PEDIDO_NUM".equals(campo.getNombre())) {
                pedido_num = campo;
            }
            if ("LINEA_NUM".equals(campo.getNombre())) {
                linea_num = campo;
            }
        }
        if (pedido_num != null && linea_num != null) {
            Long ped_num = PEDIDOS_MANAGER.getMobilePedidoNum(session, fields);
            if (ped_num > 0) {
                pedido_num.setValue(ped_num);
                // Verifica si la linea ya esta incluida
                return PEDIDOS_LINEAS_MANAGER.findByKey(session, Converter.getLong(linea_num.getValue()), ped_num) == null;
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
