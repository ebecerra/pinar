package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_HIST_CLAVES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 05/05/2011
 */
public class APPBS_USUARIOS_HIST_CLAVES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_USUARIOS_HIST_CLAVES";

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

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static APPBS_USUARIOS_HIST_CLAVES findFirstByFilter(Map filters) {
        return  (APPBS_USUARIOS_HIST_CLAVES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "clave", "id_usuario"
     */


    public static APPBS_USUARIOS_HIST_CLAVES findByKey(String clave ,Long id_usuario) {
        Map filters = new HashMap();
        filters.put("clave", clave);
        filters.put("id_usuario", id_usuario);

        return findFirstByFilter(filters);
    }

    public static APPBS_USUARIOS_HIST_CLAVES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_USUARIOS_HIST_CLAVES();
        return (APPBS_USUARIOS_HIST_CLAVES) findByKey(bean, keyValues);
    }

}
