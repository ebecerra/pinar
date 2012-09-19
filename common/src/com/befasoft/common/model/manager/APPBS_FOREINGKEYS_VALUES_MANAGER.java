package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_FOREINGKEYS_VALUES;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 28/12/2011
 */
public class APPBS_FOREINGKEYS_VALUES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_FOREINGKEYS_VALUES";

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
    public static APPBS_FOREINGKEYS_VALUES findFirstByFilter(Map filters) {
        return  (APPBS_FOREINGKEYS_VALUES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_campo", "id_tabla", "id_key"
     */
    public static APPBS_FOREINGKEYS_VALUES findByKey(Long id_campo ,Long id_tabla,Long id_key) {
        Map filters = new HashMap();
        filters.put("id_campo", id_campo);
        filters.put("id_tabla", id_tabla);
        filters.put("id_key", id_key);

        return findFirstByFilter(filters);
    }

    public static APPBS_FOREINGKEYS_VALUES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_FOREINGKEYS_VALUES();
        return (APPBS_FOREINGKEYS_VALUES) findByKey(bean, keyValues);
    }

}
