package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.vincomobile.model.appdb.PAISES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 15/04/2011
 */
public class PAISES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="PAISES";

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
    public static PAISES findFirstByFilter(Map filters) {
        return  (PAISES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_pais"
     */


    public static PAISES findByKey(String id_pais ) {
        Map filters = new HashMap();
        filters.put("id_pais", id_pais);

        return findFirstByFilter(filters);
    }

    public static PAISES findByKey(Map keyValues) {
        EntityBean bean = new PAISES();
        return (PAISES) findByKey(bean, keyValues);
    }

}
