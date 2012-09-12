package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.vincomobile.model.appdb.ARTICULOS_FOTOS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 06/04/2011
 */
public class ARTICULOS_FOTOS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="ARTICULOS_FOTOS";

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

    public static List findByItem_id(Long item_id) {
        Map filters = new HashMap();
        filters.put("item_id", item_id);
        return findByFilter(BEAN_NAME, filters, -1, -1, "orden", "asc");
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static ARTICULOS_FOTOS findFirstByFilter(Map filters) {
        return  (ARTICULOS_FOTOS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     */
    public static ARTICULOS_FOTOS findByKey(Long item_id, String nombre) {
        Map filters = new HashMap();
        filters.put("item_id", item_id);
        filters.put("nombre", nombre);

        return findFirstByFilter(filters);
    }

    public static ARTICULOS_FOTOS findByKey(Map keyValues) {
        EntityBean bean = new ARTICULOS_FOTOS();
        return (ARTICULOS_FOTOS) findByKey(bean, keyValues);
    }

}
