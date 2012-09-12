package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.vincomobile.model.appdb.ARTICULOS_DESCRIP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 06/04/2011
 */
public class ARTICULOS_DESCRIP_MANAGER extends BeanManager {

    private static String BEAN_NAME ="ARTICULOS_DESCRIP";

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
    public static ARTICULOS_DESCRIP findFirstByFilter(Map filters) {
        return  (ARTICULOS_DESCRIP) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     */
    public static ARTICULOS_DESCRIP findByKey(Long item_id, String id_idioma) {
        Map filters = new HashMap();
        filters.put("item_id", item_id);
        filters.put("id_idioma", id_idioma);

        return findFirstByFilter(filters);
    }

    public static ARTICULOS_DESCRIP findByKey(Map keyValues) {
        EntityBean bean = new ARTICULOS_DESCRIP();
        return (ARTICULOS_DESCRIP) findByKey(bean, keyValues);
    }

}
