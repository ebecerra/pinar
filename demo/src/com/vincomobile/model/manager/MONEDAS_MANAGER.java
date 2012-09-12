package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.vincomobile.model.appdb.MONEDAS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 25/07/2011
 */
public class MONEDAS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="MONEDAS";

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
    public static MONEDAS findFirstByFilter(Map filters) {
        return  (MONEDAS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_moneda"
     */


    public static MONEDAS findByKey(String id_moneda ) {
        Map filters = new HashMap();
        filters.put("id_moneda", id_moneda);

        return findFirstByFilter(filters);
    }

    public static MONEDAS findByKey(Map keyValues) {
        EntityBean bean = new MONEDAS();
        return (MONEDAS) findByKey(bean, keyValues);
    }

}
