package com.vincomobile.model.appdb;

import com.befasoft.common.business.ActionTrigger;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.tools.Converter;
import com.vincomobile.Constants;
import com.vincomobile.model.manager.PEDIDOS_MANAGER;
import org.hibernate.Session;

import java.util.List;

public class PEDIDOS_TRIGGER implements ActionTrigger {

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
        APPBS_CAMPOS id_cliente = null;
        APPBS_CAMPOS id_dir_fact = null;
        APPBS_CAMPOS id_dir_env = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("PEDIDO_NUM".equals(campo.getNombre()))
                pedido_num = campo;
            if ("ID_CLIENTE".equals(campo.getNombre()))
                id_cliente = campo;
            if ("ID_DIR_FACT".equals(campo.getNombre()))
                id_dir_fact = campo;
            if ("ID_DIR_ENV".equals(campo.getNombre()))
                id_dir_env = campo;
        }
        if (pedido_num != null && id_cliente != null && id_dir_fact != null && id_dir_env != null) {
            Long ped_num = PEDIDOS_MANAGER.getMobilePedidoNum(session, fields);
            if (ped_num == 0)
                pedido_num.setValue(PEDIDOS_MANAGER.getNextPedidoNum());
            else
                return false;
            Long id_dir_f = Converter.getLong(id_dir_fact.getValue());
            if (id_dir_f >= Constants.FIRST_MOBILE_ID_DIRECCION) {
                id_dir_f = PEDIDOS_MANAGER.getMobileId_direccion(session, fields, "ID_DIR_FACT");
                if (id_dir_f > 0)
                    id_dir_fact.setValue(id_dir_f);
            }
            Long id_dir_e = Converter.getLong(id_dir_env.getValue());
            if (id_dir_e >= Constants.FIRST_MOBILE_ID_DIRECCION) {
                id_dir_e = PEDIDOS_MANAGER.getMobileId_direccion(session, fields, "ID_DIR_ENV");
                if (id_dir_e > 0)
                    id_dir_env.setValue(id_dir_e);
            }
            Long mobile_id_cliente = Converter.getLong(id_cliente.getValue());
            if (mobile_id_cliente >= Constants.FIRST_MOBILE_ID_CLIENTE) {
                Long id_cli = PEDIDOS_MANAGER.getMobileId_cliente(session, fields);
                if (id_cli > 0)
                    id_cliente.setValue(id_cli);
            }
            return true;
        }
        return false;
    }

    public void afterInsert(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        Long pedido_num = 0L;
        for (APPBS_CAMPOS campo : fields) {
            if ("PEDIDO_NUM".equals(campo.getNombre())) {
                pedido_num = Converter.getLong(campo.getValue());
                break;
            }
        }

        PEDIDOS pedido = PEDIDOS_MANAGER.findByKey(session, pedido_num);
        if (pedido != null) {
            pedido.setEstado("C");
            PEDIDOS_MANAGER.save(session, pedido);
        }
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
