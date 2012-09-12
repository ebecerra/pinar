package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.CLIENTES_CONTACTOS;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/07/2011
 */
public class CLIENTES_CONTACTOS_MANAGER extends BeanManager {

    public static String BEAN_NAME = "CLIENTES_CONTACTOS";

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
    public static CLIENTES_CONTACTOS findFirstByFilter(Map filters) {
        return  (CLIENTES_CONTACTOS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_direccion", "id_cliente", "id_contacto"
     */


    public static CLIENTES_CONTACTOS findByKey(Long id_direccion ,Long id_cliente,Long id_contacto) {
        Map filters = new HashMap();
        filters.put("id_direccion", id_direccion);
        filters.put("id_cliente", id_cliente);
        filters.put("id_contacto", id_contacto);

        return findFirstByFilter(filters);
    }

    public static CLIENTES_CONTACTOS findByKey(Map keyValues) {
        EntityBean bean = new CLIENTES_CONTACTOS();
        return (CLIENTES_CONTACTOS) findByKey(bean, keyValues);
    }

    /**
     * Obtiene el proximo numero de contacto de cliente
     * @return Numero de contacto
     */
    public static Long getNextId_contacto() {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT CLIENTES_CONTACTOS_WEB_SEQ.nextval FROM DUAL").setCacheable(false);
        return ((BigDecimal) query.uniqueResult()).longValue();
    }

    public static Long getNextMobileId_contacto(Long mobile_id, Long id_vendedor) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(
                "SELECT MAX(CC.MOBILE_ID_CONTACTO) FROM CLIENTES C JOIN CLIENTES_DIRECCIONES CD ON C.ID_CLIENTE = CD.ID_CLIENTE " +
                "JOIN CLIENTES_CONTACTOS CC ON C.ID_CLIENTE = CC.ID_CLIENTE "+
                "WHERE C.MOBILE_ID = :mobile_id AND CD.ID_VENDEDOR = :id_vendedor")
                .setParameter("mobile_id", mobile_id)
                .setParameter("id_vendedor", id_vendedor)
                .setCacheable(false);
        BigDecimal result = (BigDecimal) query.uniqueResult();
        return result == null || result.longValue() < com.vincomobile.Constants.FIRST_MOBILE_ID_CONTACTO ? com.vincomobile.Constants.FIRST_MOBILE_ID_CONTACTO : result.longValue() + 1;
    }

}
