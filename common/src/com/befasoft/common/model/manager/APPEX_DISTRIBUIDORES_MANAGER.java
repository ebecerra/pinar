package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_DISTRIBUIDORES;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 05/06/2012
 */
public class APPEX_DISTRIBUIDORES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_DISTRIBUIDORES";

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
    public static APPEX_DISTRIBUIDORES findFirstByFilter(Map filters) {
        return  (APPEX_DISTRIBUIDORES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_distribuidor"
     */
    public static APPEX_DISTRIBUIDORES findByKey(Long id_distribuidor ) {
        Map filters = new HashMap();
        filters.put("id_distribuidor", id_distribuidor);

        return findFirstByFilter(filters);
    }

    public static APPEX_DISTRIBUIDORES findByKey(Map keyValues) {
        EntityBean bean = new APPEX_DISTRIBUIDORES();
        return (APPEX_DISTRIBUIDORES) findByKey(bean, keyValues);
    }

}
