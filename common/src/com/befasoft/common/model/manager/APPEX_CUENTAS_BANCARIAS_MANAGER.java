package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_CUENTAS_BANCARIAS;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
public class APPEX_CUENTAS_BANCARIAS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_CUENTAS_BANCARIAS";

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
    public static APPEX_CUENTAS_BANCARIAS findFirstByFilter(Map filters) {
        return  (APPEX_CUENTAS_BANCARIAS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_cuenta"
     */
    public static APPEX_CUENTAS_BANCARIAS findByKey(Long id_cuenta ) {
        Map filters = new HashMap();
        filters.put("id_cuenta", id_cuenta);

        return findFirstByFilter(filters);
    }

    public static APPEX_CUENTAS_BANCARIAS findByKey(Map keyValues) {
        EntityBean bean = new APPEX_CUENTAS_BANCARIAS();
        return (APPEX_CUENTAS_BANCARIAS) findByKey(bean, keyValues);
    }

}
