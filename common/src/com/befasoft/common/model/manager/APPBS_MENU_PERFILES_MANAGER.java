package com.befasoft.common.model.manager;


import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_MENU_PERFILES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 14/04/2011
 */
public class APPBS_MENU_PERFILES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_MENU_PERFILES";

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
    public static APPBS_MENU_PERFILES findFirstByFilter(Map filters) {
        return  (APPBS_MENU_PERFILES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_perfil"
     */

    public static List findByPerfil(Long id_perfil) {

        Map filters = new HashMap();
        filters.put("id_perfil", id_perfil);

        return findByFilter(filters);

    }


    /**
     * Busca por el campo llave
     */
    public static APPBS_MENU_PERFILES findByKey(Long id_perfil , String id_aplicacion, String id_menu) {

        Map filters = new HashMap();
        filters.put("id_perfil", id_perfil);
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_menu", id_menu);

        return findFirstByFilter(filters);

    }

    public static APPBS_MENU_PERFILES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_MENU_PERFILES();
        return (APPBS_MENU_PERFILES) findByKey(bean, keyValues);
    }

    public static void main(String[] args){
        List list = APPBS_MENU_PERFILES_MANAGER.listAll();
        System.out.println("fin "+ list);
    }

}
