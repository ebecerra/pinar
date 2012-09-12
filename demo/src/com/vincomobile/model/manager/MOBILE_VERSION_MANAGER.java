package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.MOBILE_VERSION;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 24/04/2012
 */
public class MOBILE_VERSION_MANAGER extends BeanManager {

    private static String BEAN_NAME ="MOBILE_VERSION";

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
    public static MOBILE_VERSION findFirstByFilter(Map filters) {
        return  (MOBILE_VERSION) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "version", "id_idioma"
     */
    public static MOBILE_VERSION findByKey(Long version, Long id_idioma) {
        Map filters = new HashMap();
        filters.put("version", version);
        filters.put("id_idioma", id_idioma);

        return findFirstByFilter(filters);
    }

    public static MOBILE_VERSION findByKey(Map keyValues) {
        EntityBean bean = new MOBILE_VERSION();
        return (MOBILE_VERSION) findByKey(bean, keyValues);
    }

    public static MOBILE_VERSION getLastVersion(Long id_idioma) {
        Map filters = new HashMap();
        filters.put("id_idioma", id_idioma);

        List<MOBILE_VERSION> result = findByFilter(BEAN_NAME, filters, -1, -1, "version", "desc");
        return result.size() > 0 ? result.get(0) : null;
    }
}
