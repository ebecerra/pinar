package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_LOG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/04/2011
 */
public class APPBS_LOG_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_LOG";

    /**
     * Lista todos los elementos
     */
    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    /**
     * Lista los elementos que cumplan con el criterio de filtrado
     */
    public static List findByFilter(Map filters, int start, int limit) {
         return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static int findCountByFilter(Map filters){
        return findCountByFilter(BEAN_NAME, filters);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static APPBS_LOG findFirstByFilter(Map filters) {
        return  (APPBS_LOG) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     */
    public static APPBS_LOG findByKey() {
        Map filters = new HashMap();
        return findFirstByFilter(filters);
    }

    public static APPBS_LOG findByKey(Map keyValues) {
        EntityBean bean = new APPBS_LOG();
        return (APPBS_LOG) findByKey(bean, keyValues);
    }

}
