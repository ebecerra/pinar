package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_TABLAS;
import com.befasoft.common.tools.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APPBS_TABLAS_MANAGER extends BeanManager{

    private static String BEAN_NAME ="APPBS_TABLAS";

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

    public static List findByFilter(Map filters, int start, int limit){
        return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static List findByFilter(Map filters, int start, int limit, String order, String dir){
        return findByFilter(BEAN_NAME, filters, start, limit, order, dir);
    }

    public static List findByFilter(Map filters, String order, String dir){
        return findByFilter(BEAN_NAME, filters, -1, -1, order, dir);
    }

    public static int findCountByFilter(Map<String, String> filter) {
        return findCountByFilter(HibernateUtil.currentSession(), BEAN_NAME, filter);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static APPBS_TABLAS findFirstByFilter(Map filters) {
        return  (APPBS_TABLAS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_tabla"
     */
    public static APPBS_TABLAS findByKey(Long id_tabla ) {
        Map filters = new HashMap();
        filters.put("id_tabla", id_tabla);

        return findFirstByFilter(filters);
    }

    public static APPBS_TABLAS findByKey(Map keyValues) {
        EntityBean bean = new APPBS_TABLAS();
        return (APPBS_TABLAS) findByKey(bean, keyValues);
    }

    public static APPBS_TABLAS findByKey(Integer id_tabla){

        Map filters = new HashMap();
        filters.put("id_tabla", id_tabla);

        return findFirstByFilter(filters);
    }

    public static Long getTableId(String nombre) {
        Map filters = new HashMap();
        filters.put("nombre", nombre);
        APPBS_TABLAS result = findFirstByFilter(filters);
        return result != null ? result.getId_tabla() : 0;
    }

}