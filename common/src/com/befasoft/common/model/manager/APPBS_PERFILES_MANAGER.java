package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_PERFILES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: javier
 * Date: 15-mar-2011
 * Time: 17:50:00
 * To change this template use File | Settings | File Templates.
 */
public class APPBS_PERFILES_MANAGER extends BeanManager{

    private static String BEAN_NAME ="APPBS_PERFILES";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List findByFilter(Map filters, int start, int limit){
         return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static APPBS_PERFILES findFirstByFilter(Map filters){
        return  (APPBS_PERFILES)findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_PERFILES findByKey(Long id_perfil){

        Map filters = new HashMap();
        filters.put("id_perfil", id_perfil);

        return findFirstByFilter(filters);
    }

    public static APPBS_PERFILES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_PERFILES();
        return (APPBS_PERFILES) findByKey(bean, keyValues);
    }
}
