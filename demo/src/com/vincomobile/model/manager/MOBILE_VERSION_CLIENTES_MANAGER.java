package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.MOBILE_VERSION_CLIENTES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 24/04/2012
 */
public class MOBILE_VERSION_CLIENTES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="MOBILE_VERSION_CLIENTES";

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
    public static MOBILE_VERSION_CLIENTES findFirstByFilter(Map filters) {
        return  (MOBILE_VERSION_CLIENTES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "usuario", "mobile_id", "version"
     */
    public static MOBILE_VERSION_CLIENTES findByKey(String usuario, Long mobile_id, Long version) {
        Map filters = new HashMap();
        filters.put("usuario", usuario);
        filters.put("mobile_id", mobile_id);
        filters.put("version", version);

        return findFirstByFilter(filters);
    }

    public static MOBILE_VERSION_CLIENTES findByKey(Map keyValues) {
        EntityBean bean = new MOBILE_VERSION_CLIENTES();
        return (MOBILE_VERSION_CLIENTES) findByKey(bean, keyValues);
    }

}
