package com.befasoft.common.model.manager;

import com.befasoft.common.filters.FilterAdvanced;
import com.befasoft.common.filters.FilterInfo;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.*;

public class BeanManager {

    protected static Log log = LogFactory.getLog(BeanManager.class);

    /**
     * Guarda el objeto en la session
     *
     * @param session Session del Hibernate
     * @param obj Objecto a guardar
     * @return Verdadero si se guardo correctamente
     */
    public static boolean save(Session session, Object obj) {
        try {
            if (session != null)
                session.save(obj);
            else
                return save(obj);
            return true;
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Guarda el objeto en Session y hace un commit contra Database para refrejar los cambios
     *
     * @param obj Objecto a guardar
     * @return Indica si la operacion termino correctamente
     */
    public static boolean save(Object obj) {

        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        try {
            if (save(session, obj)) {
                tx.commit();
                return true;
            } else {
                tx.rollback();
                return false;
            }
        }
        catch (Throwable e) {
            log.error(e);
            e.printStackTrace();
            // Si ocurrio un error se hace rollback a la transaccion.
            try {
                tx.rollback();
            }
            catch (Throwable e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Borra el objeto en la session
     *
     * @param session Session del Hibernate
     * @param obj Objecto a borrar
     */
    public static void delete(Session session, Object obj) {
        session.delete(obj);
    }


    /**
     * Borra el objeto en Session y hace un commit contra Database para refrejar los cambios
     *
     * @param obj Objecto a borrar
     * @return Indica si la operacion termino correctamente
     */
    public static boolean delete(Object obj) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        try {
            delete(session, obj);
            tx.commit();
            return true;
        }
        catch (Throwable e) {
            log.error(e);
            e.printStackTrace();
            // Si ocurrio un error se hace rollback a la transaccion.
            try {
                tx.rollback();
            }
            catch (Throwable e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }


    /**
     * Lista todos los elementos del tipo indicado
     *
     * @param beanName Nombre del bean a listar
     * @return Lista de elementos
     */
    public static List listAll(String beanName) {
        return listAll(beanName, null, null);
    }

    /**
     * Lista todos los elementos del tipo indicado
     * @param beanName Nombre del bean a listar
     * @param sort Campo por el que se ordena
     * @param dir Direccion del ordenacion
     * @return Lista de elementos
     */
    public static List listAll(String beanName, String sort, String dir) {
        Session session = HibernateUtil.currentSession();
        return listAll(session, beanName, -1, -1, sort, dir);
    }


    /**
     * Lista todos los elementos del tipo indicado
     *
     * @param session Session del Hibernate
     * @param beanName Nombre del bean a listar
     * @param start Elemento inicial
     * @param limit Cantidad de elementos
     * @param sort Campo por el que se ordena
     * @param dir Direccion del ordenacion
     * @return Lista de elementos
     */
    public static List listAll(Session session, String beanName, int start, int limit, String sort, String dir) {

        String queryStr = "from " + beanName;
        // Si se pone el orden se añade order by
        if (sort != null && dir != null) {
            queryStr = queryStr + " order by "+sort+" "+dir;
        }

        Query query = session.createQuery(queryStr);

        if (start < 0) {
            if (limit <= 0) {
                // Nada que hacer
            }
            else {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }
        }
        else {
            query.setFirstResult(start);
            if (limit <= 0) {
                // Nada que hacer
            }
            else {
                query.setMaxResults(limit);
            }
        }

        return query.list();
    }

    protected static Object getFirts(List results) {
        return (results == null || results.size() == 0 ? null : results.get(0));
    }

    /**
     * Obtiene la condicion de filtrado
     * @param filters Filtros pasados
     * @param newFilters Nuevo map con el filtro generado
     * @return Condicion de filtados (WHERE)
     */
    protected static String buildWhere(Map filters, Map newFilters) {
        StringBuilder query = new StringBuilder();
        Iterator itKeys = filters.entrySet().iterator();

        while (itKeys.hasNext()) {
            Map.Entry pairs = (Map.Entry) itKeys.next();
            if (pairs.getValue() instanceof FilterAdvanced) {
                FilterAdvanced fltAd = (FilterAdvanced) pairs.getValue();
                if (!fltAd.isEmpty()) {
                    List<FilterInfo> items = fltAd.getItems();
                    for (int i = 0; i < items.size(); i++) {
                        FilterInfo item = items.get(i);
                        if (!item.isEmpty()) {
                            query.append(item.getSQL(i));
                            if (item.isParameters())
                                newFilters.put(item.getKeymap()+"_"+i, item.getValue());
                        }
                    }
                    if (!"".equals(fltAd.getAddCond())) {
                        if (!query.toString().endsWith(" and ") && !"".equals(query.toString()))
                            query.append(" and ");
                        query.append(fltAd.getAddCond());
                    }
                    if (itKeys.hasNext())
                        query.append(" and ");
                }
            } else {
                if (pairs.getValue() == null || !(pairs.getValue() instanceof String && pairs.getValue().toString().indexOf('%') >= 0)) {
                    query.append(pairs.getKey()).append(" = :").append(((String)pairs.getKey()).replace(".", "_"));
                }
                else {
                    query.append(pairs.getKey()).append(" like :").append(((String)pairs.getKey()).replace(".", "_"));
                }
                if (itKeys.hasNext()) {
                    query.append(" and ");
                }
                newFilters.put(((String)pairs.getKey()).replace(".", "_"), pairs.getValue());
            }
        }
        return query.toString();
    }

    /**
     * Verifica si un filtro es vacio
     * @param filters Filtro
     * @return Verdadero si es vacio
     */
    private static boolean isFilterEmpty(Map filters) {
        if (filters == null || filters.size() == 0)
            return true;
        boolean result = true;
        Collection values = filters.values();
        for (Object next : values) {
            if (next instanceof FilterAdvanced) {
                result = ((FilterAdvanced) next).isEmpty();
                if (!result)
                    break;
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Obtiene la lista de elementos que cumplan unas condiciones
     * @param session Session del Hibernate
     * @param beanName Nombre del bean a listar
     * @param filters Map con los filtros a aplicar
     * @param start Elemento inicial
     * @param limit Cantidad de elementos
     * @param sort Campo por el que se ordena
     * @param dir Direccion del ordenacion
     * @return Lista de elementos
     */
    public static List findByFilter(Session session, String beanName, Map filters, int start, int limit, String sort, String dir) {
        if (isFilterEmpty(filters)) {
            return listAll(session, beanName, start, limit, sort, dir);
        }

        Map newFilter = new HashMap();
        String queryStr = "from " + beanName + " where " + buildWhere(filters, newFilter);

        // Si se pone el orden se añade order by
        if (sort != null && dir != null) {
            queryStr = queryStr + " order by "+sort+" "+dir;
        }

        Query query = session.createQuery(queryStr).setProperties(newFilter).setCacheable(false);
        if (start < 0) {
            if (limit > 0) {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }
        }
        else {
            query.setFirstResult(start);
            if (limit > 0) {
                query.setMaxResults(limit);
            }
        }

        return query.list();
    }

    public static List findByFilter(String beanName, Map filters, int start, int limit, String sort, String dir) {
        Session session = HibernateUtil.currentSession();
        return findByFilter(session, beanName, filters, start, limit, sort, dir);
    }

    public static List findByFilter(String beanName, Map filters, String sort, String dir) {
        Session session = HibernateUtil.currentSession();
        return findByFilter(session, beanName, filters, -1, -1, sort, dir);
    }

    public static List findByFilter(String beanName, Map filters, int start, int limit) {
        Session session = HibernateUtil.currentSession();
        return findByFilter(session, beanName, filters, start, limit, null, null);
    }

    public static List findByFilter(Session session, String beanName, Map filters) {
        return findByFilter(session, beanName, filters, -1, -1, null, null);
    }

    public static List findByFilter(String beanName, Map filters) {
        Session session = HibernateUtil.currentSession();
        return findByFilter(session, beanName, filters, -1, -1, null, null);
    }

    /**
     * Obtiene la cantidad total de elementos que se obtienen con un filtro
     * @param session Session del Hibernate
     * @param beanName Nombre del bean a listar
     * @param filters Map con los filtros a aplicar
     * @return Cantidad de elementos
     */
    public static int findCountByFilter(Session session, String beanName, Map filters) {
        if (isFilterEmpty(filters)) {
            return count(session, beanName);
        }

        Map newFilter = new HashMap();
        String queryStr = "select count(*) from " + beanName + " where " + buildWhere(filters, newFilter);

        Query query = session.createQuery(queryStr).setProperties(newFilter);

        Object result = query.uniqueResult();

        return ((Number) result).intValue();
    }

    public static int findCountByFilter(String beanName, Map filters) {
        Session session = HibernateUtil.currentSession();
        return findCountByFilter(session, beanName, filters);
    }

    private static int count(Session session, String beanName) {
        Query query = session.createQuery("select count(*) from " + beanName);
        return ((Number)query.uniqueResult()).intValue();
    }

    /**
     * Obtiene el primer item que cumpla la condicion
     * @param beanName Nombre del bean a listar
     * @param filters Filtros a utilizar
     * @return Objecto que cumple la condicion
     */
    public static Object findFirstByFilter(String beanName, Map filters) {
        List results = findByFilter(beanName, filters, -1, -1, null, null);
        return BeanManager.getFirts(results);
    }

    public static Object findFirstByFilter(String beanName, Map filters, String sort, String dir) {
        List results = findByFilter(beanName, filters, -1, -1, sort, dir);
        return BeanManager.getFirts(results);
    }

    public static Object findFirstByFilter(Session session, String beanName, Map filters) {
        List results = findByFilter(session, beanName, filters, -1, -1, null, null);
        return BeanManager.getFirts(results);
    }

    /**
     * Filtra por los campos llave
     * @param bean Nombre del bean a listar
     * @param keyValues Valores de la llave
     * @return Objecto que cumple la condicion
     */
    public static Object findByKey(EntityBean bean, Map keyValues) {
        return findByKey(HibernateUtil.currentSession(), bean, keyValues);
    }

    /**
     * Filtra por los campos llave
     * @param session Sesion del Hibernate
     * @param bean Nombre del bean a listar
     * @param keyValues Valores de la llave
     * @return Objecto que cumple la condicion
     */
    public static Object findByKey(Session session, EntityBean bean, Map keyValues) {
        Map filters = new HashMap();
        String[] keyname = bean.getKeyFields();
        for (String s : keyname) {
            filters.put(s, keyValues.get(s));
        }
        return findFirstByFilter(session, bean.getClass().getName(), filters);
    }

    /**
     * Obtiene el valor de una cadena de la DB
     *
     * @param sql Query que obtiene el dato
     * @return Cadena
     */
    public static String getQueryStrValue(String sql) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        return (String) query.uniqueResult();
    }

    /**
     * Obtiene el valor de una fecha de la DB
     *
     * @param sql Query que obtiene el dato
     * @return Fecha
     */
    public static Date getQueryDateValue(String sql) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        return (Date) query.uniqueResult();
    }

    /**
     * Obtiene el valor de una entero de la DB
     *
     * @param sql Query que obtiene el dato
     * @return Entero
     */
    public static Long getQueryLongValue(String sql) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        BigInteger result = (BigInteger) query.uniqueResult();
        return result == null ? 0 : result.longValue();
    }

}
