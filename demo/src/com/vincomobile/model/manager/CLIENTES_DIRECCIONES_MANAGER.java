package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.CLIENTES;
import com.vincomobile.model.appdb.CLIENTES_DIRECCIONES;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 14/04/2011
 */
public class CLIENTES_DIRECCIONES_MANAGER extends BeanManager {

    public static String BEAN_NAME = "CLIENTES_DIRECCIONES";

    /**
     * Lista todos los elementos
     */
    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    /**
     * Lista los elementos que cumplan con el criterio de filtrado
     */
    public static List findByFilter(Map filters) {
        return findByFilter(BEAN_NAME, filters);
    }

    public static int findCountByFilter(Map<String, String> filter) {
        return findCountByFilter(HibernateUtil.currentSession(), BEAN_NAME, filter);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static CLIENTES_DIRECCIONES findFirstByFilter(Map filters) {
        return  (CLIENTES_DIRECCIONES) findFirstByFilter(BEAN_NAME, filters);
    }

    public static CLIENTES_DIRECCIONES findFirstByFilter(Session session, Map filters) {
        return  (CLIENTES_DIRECCIONES) findFirstByFilter(session, BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_direccion", "id_cliente"
     */


    public static CLIENTES_DIRECCIONES findByKey(Long id_direccion ,Long id_cliente) {
        Map filters = new HashMap();
        filters.put("id_direccion", id_direccion);
        filters.put("id_cliente", id_cliente);

        return findFirstByFilter(filters);
    }

    public static CLIENTES_DIRECCIONES findByRef_dir_web(String ref_dir_web) {
        Map filters = new HashMap();
        filters.put("ref_dir_web", ref_dir_web);
        return (CLIENTES_DIRECCIONES) findFirstByFilter(BEAN_NAME, filters, "ID_DIRECCION", "ASC");
    }

    public static CLIENTES_DIRECCIONES findByKey(Map keyValues) {
        EntityBean bean = new CLIENTES_DIRECCIONES();
        return (CLIENTES_DIRECCIONES) findByKey(bean, keyValues);
    }

    /**
     * Obtiene el proximo numero de direccion de cliente
     * @return Numero de direccion
     */
    public static Long getNextId_direccion() {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT CLIENTES_DIRECCIONES_WEB_SEQ.nextval FROM DUAL").setCacheable(false);
        return ((BigDecimal) query.uniqueResult()).longValue();
    }

    public static Long getMobileId_direccion(Session session, Long mobile_id_cliente, Long mobile_id_direccion, Long mobile_id) {
        SQLQuery query = (SQLQuery) session.createSQLQuery(
                "SELECT ID_DIRECCION FROM CLIENTES_DIRECCIONES WHERE MOBILE_ID_CLIENTE = :mobile_id_cliente AND MOBILE_ID_DIRECCION = :mobile_id_direccion AND MOBILE_ID = :mobile_id")
                .setParameter("mobile_id_cliente", mobile_id_cliente)
                .setParameter("mobile_id_direccion", mobile_id_direccion)
                .setParameter("mobile_id", mobile_id)
                .setCacheable(false);
        BigDecimal result = (BigDecimal) query.uniqueResult();
        return result == null ? 0L : result.longValue();
    }

    public static Long getMobileId_direccion(Session session, List<APPBS_CAMPOS> fields) {
        APPBS_CAMPOS mobile_id_cliente = null;
        APPBS_CAMPOS mobile_id_direccion = null;
        APPBS_CAMPOS mobile_id = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("MOBILE_ID_CLIENTE".equals(campo.getNombre()))
                mobile_id_cliente = campo;
            if ("MOBILE_ID_DIRECCION".equals(campo.getNombre()))
                mobile_id_direccion = campo;
            if ("MOBILE_ID".equals(campo.getNombre()))
                mobile_id = campo;
        }
        if (mobile_id_cliente != null && mobile_id != null && mobile_id_direccion != null)
            return getMobileId_direccion(session, Converter.getLong(mobile_id_cliente.getValue()), Converter.getLong(mobile_id_direccion.getValue()), Converter.getLong(mobile_id.getValue()));
        else
            return 0L;
    }

    public static CLIENTES getMobileCliente(Session session, List<APPBS_CAMPOS> fields) {
        APPBS_CAMPOS mobile_id_cliente = null;
        APPBS_CAMPOS mobile_id = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("MOBILE_ID_CLIENTE".equals(campo.getNombre()))
                mobile_id_cliente = campo;
            if ("MOBILE_ID".equals(campo.getNombre()))
                mobile_id = campo;
        }
        if (mobile_id_cliente != null && mobile_id != null) {
            Map filters = new HashMap();
            filters.put("mobile_id_cliente", Converter.getLong(mobile_id_cliente.getValue()));
            filters.put("mobile_id", Converter.getLong(mobile_id.getValue()));
            return CLIENTES_MANAGER.findFirstByFilter(session, filters);
        } else
            return null;
    }

    public static CLIENTES_DIRECCIONES getMobileDireccion(Session session, List<APPBS_CAMPOS> fields) {
        APPBS_CAMPOS mobile_id_cliente = null;
        APPBS_CAMPOS mobile_id_direccion = null;
        APPBS_CAMPOS mobile_id = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("MOBILE_ID_CLIENTE".equals(campo.getNombre()))
                mobile_id_cliente = campo;
            if ("MOBILE_ID_DIRECCION".equals(campo.getNombre()))
                mobile_id_direccion = campo;
            if ("MOBILE_ID".equals(campo.getNombre()))
                mobile_id = campo;
        }
        if (mobile_id_cliente != null && mobile_id != null && mobile_id_direccion != null) {
            Map filters = new HashMap();
            filters.put("mobile_id_cliente", Converter.getLong(mobile_id_cliente.getValue()));
            filters.put("mobile_id_direccion", Converter.getLong(mobile_id_direccion.getValue()));
            filters.put("mobile_id", Converter.getLong(mobile_id.getValue()));
            return findFirstByFilter(session, filters);
        } else
            return null;
    }

    public static Long getNextMobileId_direccion(Long mobile_id, Long id_vendedor) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(
                "SELECT MAX(MOBILE_ID_DIRECCION) FROM CLIENTES_DIRECCIONES " +
                "WHERE MOBILE_ID = :mobile_id AND ID_VENDEDOR = :id_vendedor")
                .setParameter("mobile_id", mobile_id)
                .setParameter("id_vendedor", id_vendedor)
                .setCacheable(false);
        BigDecimal result = (BigDecimal) query.uniqueResult();
        return result == null || result.longValue() < com.vincomobile.Constants.FIRST_MOBILE_ID_DIRECCION ? com.vincomobile.Constants.FIRST_MOBILE_ID_DIRECCION : result.longValue() + 1;
    }
}
