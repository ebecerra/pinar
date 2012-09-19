package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_PARAMETROS_VALORES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: javier
 * Date: 15-mar-2011
 * Time: 16:29:57
 * To change this template use File | Settings | File Templates.
 */
public class APPBS_PARAMETROS_VALORES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_PARAMETROS_VALORES";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List findByFilter(Map filters, int start, int limit){
         return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static APPBS_PARAMETROS_VALORES findFirstByFilter(Map filters){
        return  (APPBS_PARAMETROS_VALORES)findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_PARAMETROS_VALORES findByKey(String id_aplicacion, String id_parametro, Integer id_valor){

        Map filters = new HashMap();
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_parametro", id_parametro);
        filters.put("id_valor", id_valor);

        return findFirstByFilter(filters);
    }

    public static APPBS_PARAMETROS_VALORES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_PARAMETROS_VALORES();
        return (APPBS_PARAMETROS_VALORES) findByKey(bean, keyValues);
    }

}