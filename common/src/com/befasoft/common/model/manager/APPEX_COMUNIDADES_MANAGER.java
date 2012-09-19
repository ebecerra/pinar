package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_COMUNIDADES;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/05/2012
 */
public class APPEX_COMUNIDADES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_COMUNIDADES";

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
    public static APPEX_COMUNIDADES findFirstByFilter(Map filters) {
        return  (APPEX_COMUNIDADES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_comunidad"
     */
    public static APPEX_COMUNIDADES findByKey(Long id_comunidad ) {
        Map filters = new HashMap();
        filters.put("id_comunidad", id_comunidad);

        return findFirstByFilter(filters);
    }

    public static APPEX_COMUNIDADES findByKey(Map keyValues) {
        EntityBean bean = new APPEX_COMUNIDADES();
        return (APPEX_COMUNIDADES) findByKey(bean, keyValues);
    }

}
