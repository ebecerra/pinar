package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.vincomobile.model.appdb.VENDEDORES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 24/08/2011
 */
public class VENDEDORES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="VENDEDORES";

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
    public static VENDEDORES findFirstByFilter(Map filters) {
        return  (VENDEDORES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_vendedor"
     */


    public static VENDEDORES findByKey(Long id_vendedor ) {
        Map filters = new HashMap();
        filters.put("id_vendedor", id_vendedor);

        return findFirstByFilter(filters);
    }

    public static VENDEDORES findByKey(Map keyValues) {
        EntityBean bean = new VENDEDORES();
        return (VENDEDORES) findByKey(bean, keyValues);
    }

}
