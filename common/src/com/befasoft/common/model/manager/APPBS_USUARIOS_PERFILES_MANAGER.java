package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_PERFILES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/05/2011
 */
public class APPBS_USUARIOS_PERFILES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_USUARIOS_PERFILES";

    /**
     * Lista todos los elementos
     */
    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    /**
     * Lista los elementos que cumplan con el criterio de filtrado
     */
    public static List findByFilter(Map filters, int start, int limit) {
        return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static APPBS_USUARIOS_PERFILES findFirstByFilter(Map filters) {
        return  (APPBS_USUARIOS_PERFILES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_aplicacion", "id_usuario", "id_perfil"
     */


    public static APPBS_USUARIOS_PERFILES findByKey(String id_aplicacion, Long id_usuario, Long id_perfil) {
        Map filters = new HashMap();
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_usuario", id_usuario);
        filters.put("id_perfil", id_perfil);

        return findFirstByFilter(filters);
    }

    public static APPBS_USUARIOS_PERFILES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_USUARIOS_PERFILES();
        return (APPBS_USUARIOS_PERFILES) findByKey(bean, keyValues);
    }

    public static boolean addUserPerfil(String id_aplicacion, Long id_usuario, Long id_perfil) {
        APPBS_USUARIOS_PERFILES bean = new APPBS_USUARIOS_PERFILES(id_aplicacion, id_usuario, id_perfil);
        save(bean);
        return true;
    }

    public static boolean deleteUserPerfil(String id_aplicacion, Long id_usuario, Long id_perfil) {
        APPBS_USUARIOS_PERFILES bean = new APPBS_USUARIOS_PERFILES(id_aplicacion, id_usuario, id_perfil);
        delete(bean);
        return true;
    }
}
