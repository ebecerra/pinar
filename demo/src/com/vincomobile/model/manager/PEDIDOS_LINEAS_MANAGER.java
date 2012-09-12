package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.PEDIDOS_LINEAS;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 11/04/2011
 */
public class PEDIDOS_LINEAS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="PEDIDOS_LINEAS";

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

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static PEDIDOS_LINEAS findFirstByFilter(Map filters) {
        return  (PEDIDOS_LINEAS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     */
    public static PEDIDOS_LINEAS findByKey(Long linea_num, Long pedido_num) {
        Map filters = new HashMap();
        filters.put("linea_num", linea_num);
        filters.put("pedido_num", pedido_num);

        return findFirstByFilter(filters);
    }

    public static PEDIDOS_LINEAS findByKey(Session session, Long linea_num, Long pedido_num) {
        Map filters = new HashMap();
        filters.put("linea_num", linea_num);
        filters.put("pedido_num", pedido_num);

        return (PEDIDOS_LINEAS) findFirstByFilter(session, BEAN_NAME, filters);
    }

    public static PEDIDOS_LINEAS findByKey(Map keyValues) {
        EntityBean bean = new PEDIDOS_LINEAS();
        return (PEDIDOS_LINEAS) findByKey(bean, keyValues);
    }

    public static BigDecimal getNextLineaNum() {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT PEDIDOS_LINEAS_SEQ.nextval FROM DUAL").setCacheable(false);
        return (BigDecimal) query.uniqueResult();
    }
}
