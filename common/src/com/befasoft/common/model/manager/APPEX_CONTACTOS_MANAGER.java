package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_CONTACTOS;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
public class APPEX_CONTACTOS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_CONTACTOS";

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
    public static APPEX_CONTACTOS findFirstByFilter(Map filters) {
        return  (APPEX_CONTACTOS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_contacto"
     */
    public static APPEX_CONTACTOS findByKey(Long id_contacto ) {
        Map filters = new HashMap();
        filters.put("id_contacto", id_contacto);

        return findFirstByFilter(filters);
    }

    public static APPEX_CONTACTOS findByKey(Map keyValues) {
        EntityBean bean = new APPEX_CONTACTOS();
        return (APPEX_CONTACTOS) findByKey(bean, keyValues);
    }

}
