package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.vincomobile.model.appdb.PROVINCIAS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 15/04/2011
 */
public class PROVINCIAS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="PROVINCIAS";

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

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static PROVINCIAS findFirstByFilter(Map filters) {
        return  (PROVINCIAS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_provincia", "id_pais"
     */


    public static PROVINCIAS findByKey(String id_provincia ,String id_pais) {
        Map filters = new HashMap();
        filters.put("id_provincia", id_provincia);
        filters.put("id_pais", id_pais);

        return findFirstByFilter(filters);
    }

    public static PROVINCIAS findByKey(Map keyValues) {
        EntityBean bean = new PROVINCIAS();
        return (PROVINCIAS) findByKey(bean, keyValues);
    }

}
