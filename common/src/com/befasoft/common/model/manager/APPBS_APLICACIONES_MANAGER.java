package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_APLICACIONES;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;

public class APPBS_APLICACIONES_MANAGER extends BeanManager {

    private static String BEAN_NAME = "APPBS_APLICACIONES";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List findByFilter(Map filters, int start, int limit) {
        return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static APPBS_APLICACIONES findFirstByFilter(Map filters) {
        return (APPBS_APLICACIONES) findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_APLICACIONES findByKey(String id_aplicacion) {

        Map filters = new HashMap();
        filters.put("id_aplicacion", id_aplicacion);

        return findFirstByFilter(filters);
    }

    public static APPBS_APLICACIONES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_APLICACIONES();
        return (APPBS_APLICACIONES) findByKey(bean, keyValues);
    }

    public static List findAvailableApp(Long id_usuario) {
        Session session = HibernateUtil.currentSession();
        Query query = session
                .createQuery("select A from APPBS_APLICACIONES as A where id_aplicacion not in (select id_aplicacion from APPBS_USUARIOS_PERFILES where id_usuario = :id_usuario)")
                .setParameter("id_usuario", id_usuario);
        return query.list();
    }

    public static List findSelectedApp(Long id_usuario) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session
                .createSQLQuery("select * from ("+
                        "select A.id_aplicacion, A.nombre, P.id_perfil, P.nombre as nombre_perfil \n"+
                        "from APPBS_APLICACIONES A, APPBS_USUARIOS_PERFILES UP, APPBS_PERFILES P \n"+
                        "where UP.id_usuario = :id_usuario and A.id_aplicacion = UP.id_aplicacion and P.id_perfil = UP.id_perfil) result")
                .setParameter("id_usuario", id_usuario);
        List values = query.list();
        List result = new ArrayList();
        for (int i = 0; i < values.size(); i++) {
            Object[] obj = (Object[]) values.get(i);
            APPBS_APLICACIONES app = new APPBS_APLICACIONES();
            app.setId_aplicacion((String) obj[0]);
            app.setNombre((String) obj[1]);
            if (obj[2] instanceof BigDecimal)
                app.setID_PERFIL((BigDecimal) obj[2]);
            else
                app.setId_perfil((Integer) obj[2]);
            app.setNombre_perfil((String) obj[3]);
            result.add(app);
        }
        return result;
    }

    public static void main(String[] args) {
    }
}
