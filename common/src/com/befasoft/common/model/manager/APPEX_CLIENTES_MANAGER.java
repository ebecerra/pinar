package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.HibernateUtil;
import com.befasoft.common.model.appbs.APPEX_CLIENTES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
public class APPEX_CLIENTES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_CLIENTES";

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
    public static APPEX_CLIENTES findFirstByFilter(Map filters) {
        return  (APPEX_CLIENTES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_cliente"
     */
    public static APPEX_CLIENTES findByKey(Long id_cliente ) {
        Map filters = new HashMap();
        filters.put("id_cliente", id_cliente);

        return findFirstByFilter(filters);
    }

    public static APPEX_CLIENTES findByKey(Map keyValues) {
        EntityBean bean = new APPEX_CLIENTES();
        return (APPEX_CLIENTES) findByKey(bean, keyValues);
    }

}
