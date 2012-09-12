package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.PEDIDOS_ORIGEN;
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
public class PEDIDOS_ORIGEN_MANAGER extends BeanManager {

    private static String BEAN_NAME ="PEDIDOS_ORIGEN";

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
    public static PEDIDOS_ORIGEN findFirstByFilter(Map filters) {
        return  (PEDIDOS_ORIGEN) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     */
    public static PEDIDOS_ORIGEN findByKey(Long id_origen) {
        Map filters = new HashMap();
        filters.put("id_origen", id_origen);

        return findFirstByFilter(filters);
    }

    public static PEDIDOS_ORIGEN findByKey(Map keyValues) {
        EntityBean bean = new PEDIDOS_ORIGEN();
        return (PEDIDOS_ORIGEN) findByKey(bean, keyValues);
    }

    public static Long getId_origen() {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT ID_ORIGEN FROM PEDIDOS_ORIGEN WHERE NOMBRE = 'WEB KENTTO'").setCacheable(false);
        BigDecimal res = (BigDecimal) query.uniqueResult();
        return res == null ? 0 : res.longValue();
    }
}
