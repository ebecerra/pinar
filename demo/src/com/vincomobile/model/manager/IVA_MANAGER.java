package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.vincomobile.model.appdb.IVA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 22/08/2011
 */
public class IVA_MANAGER extends BeanManager {

    private static String BEAN_NAME ="IVA";

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
    public static IVA findFirstByFilter(Map filters) {
        return  (IVA) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_iva"
     */


    public static IVA findByKey(String id_iva ) {
        Map filters = new HashMap();
        filters.put("id_iva", id_iva);

        return findFirstByFilter(filters);
    }

    public static IVA findByKey(Map keyValues) {
        EntityBean bean = new IVA();
        return (IVA) findByKey(bean, keyValues);
    }

}
