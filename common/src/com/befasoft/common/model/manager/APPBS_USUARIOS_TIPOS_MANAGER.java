package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_TIPOS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: javier
 * Date: 15-mar-2011
 * Time: 18:46:22
 * To change this template use File | Settings | File Templates.
 */
public class APPBS_USUARIOS_TIPOS_MANAGER extends BeanManager{
        private static String BEAN_NAME ="APPBS_USUARIOS_TIPOS";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List findByFilter(Map filters, int start, int limit){
         return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static APPBS_USUARIOS_TIPOS findFirstByFilter(Map filters){
        return  (APPBS_USUARIOS_TIPOS)findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_USUARIOS_TIPOS findByKey(Integer id_tipo){

        Map filters = new HashMap();
        filters.put("id_tipo", id_tipo);

        return findFirstByFilter(filters);
    }

    public static APPBS_USUARIOS_TIPOS findByKey(Map keyValues) {
        EntityBean bean = new APPBS_USUARIOS_TIPOS();
        return (APPBS_USUARIOS_TIPOS) findByKey(bean, keyValues);
    }

}