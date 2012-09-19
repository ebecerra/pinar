package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_DIRECCIONES;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
public class APPEX_DIRECCIONES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_DIRECCIONES";

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
    public static APPEX_DIRECCIONES findFirstByFilter(Map filters) {
        return  (APPEX_DIRECCIONES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_direccion"
     */
    public static APPEX_DIRECCIONES findByKey(Long id_direccion ) {
        Map filters = new HashMap();
        filters.put("id_direccion", id_direccion);

        return findFirstByFilter(filters);
    }

    public static APPEX_DIRECCIONES findByKey(Map keyValues) {
        EntityBean bean = new APPEX_DIRECCIONES();
        return (APPEX_DIRECCIONES) findByKey(bean, keyValues);
    }

}
