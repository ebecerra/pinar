package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_PAISES;
import com.befasoft.common.model.appbs.APPEX_TRADUCCIONES;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
public class APPEX_PAISES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_PAISES";

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
    public static APPEX_PAISES findFirstByFilter(Map filters) {
        return  (APPEX_PAISES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_pais"
     */
    public static APPEX_PAISES findByKey(Long id_pais ) {
        Map filters = new HashMap();
        filters.put("id_pais", id_pais);

        return findFirstByFilter(filters);
    }

    public static APPEX_PAISES findByKey(Map keyValues) {
        EntityBean bean = new APPEX_PAISES();
        return (APPEX_PAISES) findByKey(bean, keyValues);
    }

    /**
     * Lista los paises activos en el idioma seleccionado
     * @param id_idioma Id. del idioma
     * @return Lista de los paises
     */
    public static List<APPEX_PAISES> listActivos(Long id_idioma) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT P.ID_PAIS, P.ISO_CODE_2, P.ISO_CODE_3, P.ACTIVO, ");
        if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
            sql.append(" NVL(T.TEXTO, ' ') AS NOMBRE ");
        } else {
            sql.append(" IF(T.TEXTO IS NULL, ' ', T.TEXTO) AS NOMBRE ");
        }
        sql.append("FROM APPEX_PAISES P LEFT JOIN APPEX_TRADUCCIONES T ON T.ROWKEY = P.ID_PAIS AND T.ID_IDIOMA = ").append(id_idioma).append(" AND T.ID_TABLA = ").append(Constants.TABLEID_APPEX_PAISES);
        sql.append(" WHERE P.ACTIVO = 1");
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql.toString()).setCacheable(false);
        List qRes = query.list();
        List<APPEX_PAISES> result = new ArrayList<APPEX_PAISES>();
        for (int i = 0; i < qRes.size(); i++) {
            Object[] item = (Object[]) qRes.get(i);
            APPEX_PAISES pais = new APPEX_PAISES();
            pais.setIso_code_2((String) item[1]);
            pais.setIso_code_3((String) item[2]);
            pais.setNombre((String) item[4]);
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
                pais.setId_pais((BigDecimal) item[0]);
                pais.setActivo((BigDecimal) item[3]);
            } else {
                pais.setId_pais((Integer) item[0]);
                pais.setActivo((Integer) item[3]);
            }
            result.add(pais);
        }
        return result;
    }
}