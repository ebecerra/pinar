package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_IDIOMAS;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 22/09/2011
 */
public class APPEX_IDIOMAS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_IDIOMAS";

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
    public static APPEX_IDIOMAS findFirstByFilter(Map filters) {
        return  (APPEX_IDIOMAS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_idioma"
     */


    public static APPEX_IDIOMAS findByKey(Long id_idioma ) {
        Map filters = new HashMap();
        filters.put("id_idioma", id_idioma);

        return findFirstByFilter(filters);
    }

    public static APPEX_IDIOMAS findByKey(Map keyValues) {
        EntityBean bean = new APPEX_IDIOMAS();
        return (APPEX_IDIOMAS) findByKey(bean, keyValues);
    }

}
