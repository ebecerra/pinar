package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.HibernateUtil;
import com.befasoft.common.model.appbs.APPEX_LICENCIA_ITEMS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
public class APPEX_LICENCIA_ITEMS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_LICENCIA_ITEMS";

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
    public static APPEX_LICENCIA_ITEMS findFirstByFilter(Map filters) {
        return  (APPEX_LICENCIA_ITEMS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_licencia", "id_item"
     */
    public static APPEX_LICENCIA_ITEMS findByKey(Long id_licencia ,String id_item) {
        Map filters = new HashMap();
        filters.put("id_licencia", id_licencia);
        filters.put("id_item", id_item);

        return findFirstByFilter(filters);
    }

    public static APPEX_LICENCIA_ITEMS findByKey(Map keyValues) {
        EntityBean bean = new APPEX_LICENCIA_ITEMS();
        return (APPEX_LICENCIA_ITEMS) findByKey(bean, keyValues);
    }

}
