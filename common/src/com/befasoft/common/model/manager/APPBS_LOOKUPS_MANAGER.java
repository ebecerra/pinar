package com.befasoft.common.model.manager;


import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_LOOKUPS;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/10/2011
 */
public class APPBS_LOOKUPS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_LOOKUPS";

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
    public static APPBS_LOOKUPS findFirstByFilter(Map filters) {
        return  (APPBS_LOOKUPS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_lookup"
     */
    public static APPBS_LOOKUPS findByKey(String id_lookup ) {
        Map filters = new HashMap();
        filters.put("id_lookup", id_lookup);

        return findFirstByFilter(filters);
    }

    public static APPBS_LOOKUPS findByKey(Map keyValues) {
        EntityBean bean = new APPBS_LOOKUPS();
        return (APPBS_LOOKUPS) findByKey(bean, keyValues);
    }

}
