package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_MENU;
import com.befasoft.common.tools.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

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
public class APPBS_MENU_MANAGER extends BeanManager {

    private static Log log = LogFactory.getLog(APPBS_MENU_MANAGER.class);

    private static String BEAN_NAME ="APPBS_MENU";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List findByFilter(Map filters, int start, int limit){
         return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static APPBS_MENU findFirstByFilter(Map filters){
        return  (APPBS_MENU)findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_MENU findByKey(String id_aplicacion, String id_menu){

        Map filters = new HashMap();
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_menu", id_menu);

        return findFirstByFilter(filters);
    }

    public static List findMenusByAppId(String id_aplicacion, String IdPadre){
        Map filters = new HashMap();
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_padre" , IdPadre);

        return findByFilter(BEAN_NAME, filters, -1, -1, "ORDEN", "ASC");
    }

    public static APPBS_MENU findByKey(Map keyValues) {
        EntityBean bean = new APPBS_MENU();
        return (APPBS_MENU) findByKey(bean, keyValues);
    }

    public static List findSubmenu(String id_aplicacion, String id_padre, long id_perfil) {
        Session session = HibernateUtil.currentSession();
        Query query =  session.createSQLQuery(
                "SELECT {M.*} FROM APPBS_MENU M JOIN APPBS_MENU_PERFILES MP ON M.ID_APLICACION = MP.ID_APLICACION AND M.ID_MENU = MP.ID_MENU\n " +
                "WHERE MP.ID_PERFIL = :id_perfil AND MP.ID_APLICACION = :id_aplicacion AND M.ID_PADRE = :id_padre order by M.ORDEN")
                .addEntity("M", APPBS_MENU.class)
                .setParameter("id_aplicacion", id_aplicacion)
                .setParameter("id_padre", id_padre)
                .setParameter("id_perfil", id_perfil).setCacheable(false);
        List result = query.list();
        log.debug("APPBS_MENU_MANAGER.findSubmenu("+id_aplicacion+","+id_padre+","+id_perfil+") = "+result);
        return result;
    }

}
