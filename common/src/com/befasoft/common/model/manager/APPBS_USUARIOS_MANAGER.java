package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.tools.HibernateUtil;
import com.google.gson.Gson;
import com.googlecode.jsonplugin.JSONException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APPBS_USUARIOS_MANAGER extends BeanManager{
    public static String BEAN_NAME ="APPBS_USUARIOS";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List listAll(String sort, String dir) {
        return listAll(BEAN_NAME, sort, dir);
    }

    public static List findByFilter(Map filters, int start, int limit){
        return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static List findByFilter(Map filters, int start, int limit, String sort, String dir){
        return findByFilter(BEAN_NAME, filters, start, limit, sort, dir);
    }

    public static List findByFilter(Map filters, String sort, String dir){
        return findByFilter(BEAN_NAME, filters, -1, -1, sort, dir);
    }

    public static int findCountByFilter(Map filters){
        return findCountByFilter(BEAN_NAME, filters);
    }


    public static APPBS_USUARIOS findFirstByFilter(Map filters){
        return  (APPBS_USUARIOS)findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_USUARIOS findByKey(Long id_usario){

        Map filters = new HashMap();
        filters.put("id_usuario", id_usario);

        return findFirstByFilter(filters);
    }

    public static APPBS_USUARIOS findByLogin(String login){

        Map filters = new HashMap();
        filters.put("login", login);

        return findFirstByFilter(filters);
    }

    public static APPBS_USUARIOS findByKey(Map keyValues) {
        EntityBean bean = new APPBS_USUARIOS();
        return (APPBS_USUARIOS) findByKey(bean, keyValues);
    }

    public static Integer findPerfil(String id_aplicacion, Long id_usuario) {
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("SELECT id_perfil FROM APPBS_USUARIOS_PERFILES WHERE id_aplicacion = :id_aplicacion AND id_usuario = :id_usuario")
                .setString("id_aplicacion", id_aplicacion)
                .setLong("id_usuario", id_usuario).setCacheable(false);
        List<Integer> result = query.list();
        return result != null && result.size() > 0 ? (Integer) result.get(0) : null;
    }

    public static Integer findIdUserByLogin(String login) {
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("SELECT id_usuario FROM APPBS_USUARIOS WHERE login = :login")
                .setString("login", login).setCacheable(false);
        List<Integer> result = query.list();
        return result != null && result.size() > 0 ? (Integer) result.get(0) : null;
    }

    public static Long countIdUserByLogin(String login) {
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("SELECT count(*) FROM APPBS_USUARIOS WHERE login = :login").setString("login", login).setCacheable(false);
        List<Long> result = query.list();
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public static Long countIdUserByEmail(String email) {
        Session session = HibernateUtil.currentSession();
        Query query = session.createQuery("SELECT count(*) FROM APPBS_USUARIOS WHERE email = :email").setString("email", email).setCacheable(false);
        List<Long> result = query.list();
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public static void main(String[] args) throws JSONException {
        List<APPBS_USUARIOS> users = findByFilter(null, 0,20);
        System.out.println("users = " + users);

        Gson gson  = new Gson();
        String result = gson.toJson(users);
        System.out.println("json = " + result);
    }
}
