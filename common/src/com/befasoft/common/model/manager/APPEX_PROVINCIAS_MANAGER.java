package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_PROVINCIAS;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
public class APPEX_PROVINCIAS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_PROVINCIAS";

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

    public static int findCountByFilter(Map<String, String> filter) {
        return findCountByFilter(HibernateUtil.currentSession(), BEAN_NAME, filter);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static APPEX_PROVINCIAS findFirstByFilter(Map filters) {
        return  (APPEX_PROVINCIAS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_provincia", "id_pais"
     */
    public static APPEX_PROVINCIAS findByKey(Long id_provincia ,Long id_pais) {
        Map filters = new HashMap();
        filters.put("id_provincia", id_provincia);
        filters.put("id_pais", id_pais);

        return findFirstByFilter(filters);
    }

    public static APPEX_PROVINCIAS findByKey(Map keyValues) {
        EntityBean bean = new APPEX_PROVINCIAS();
        return (APPEX_PROVINCIAS) findByKey(bean, keyValues);
    }

    public static Long getNextIdProvince(long idCountry){
        String queryString =
                "SELECT * FROM ( " +
                        "SELECT ID_PROVINCIA + 1 as ID_PROVINCIA FROM APPEX_PROVINCIAS WHERE ID_PAIS = " + idCountry + " order by ID_PROVINCIA desc LIMIT 0,1" +
                        ") results";
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(queryString).setCacheable(false);
        BigInteger result = (BigInteger) query.uniqueResult();
        return result != null ? result.longValue() : 1L;
    }

}
