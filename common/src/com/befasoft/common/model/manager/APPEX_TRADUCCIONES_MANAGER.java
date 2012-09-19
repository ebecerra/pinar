package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_TRADUCCIONES;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 22/09/2011
 */
public class APPEX_TRADUCCIONES_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_TRADUCCIONES";

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
    public static APPEX_TRADUCCIONES findFirstByFilter(Map filters) {
        return  (APPEX_TRADUCCIONES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_idioma", "id_tabla", "rowkey"
     */
    public static APPEX_TRADUCCIONES findByKey(Long id_idioma, Long id_tabla, String rowkey) {
        Map filters = new HashMap();
        filters.put("id_idioma", id_idioma);
        filters.put("id_tabla", id_tabla);
        filters.put("rowkey", rowkey);

        return findFirstByFilter(filters);
    }

    public static APPEX_TRADUCCIONES findByKey(Map keyValues) {
        EntityBean bean = new APPEX_TRADUCCIONES();
        return (APPEX_TRADUCCIONES) findByKey(bean, keyValues);
    }

    /**
     * Obtiene la traduccion de un campo
     * @param id_idioma Id. del idioma
     * @param id_tabla Id. de la tabla
     * @param rowkey Llave de campo
     * @return Traduccion
     */
    public static String getTraduccion(Long id_idioma, Long id_tabla, String rowkey) {
        Map filters = new HashMap();
        filters.put("id_idioma", id_idioma);
        filters.put("id_tabla", id_tabla);
        filters.put("rowkey", rowkey);
        APPEX_TRADUCCIONES trad = findFirstByFilter(filters);
        return trad != null ? trad.getTexto() : "";
    }

    /**
     * Obtiene todas las traducciones de un campo en los idiomas activos
     * @param id_tabla Id. de la tabla
     * @param rowkey Llave de campo
     * @return Lista con las traducciones
     */
    public static List<APPEX_TRADUCCIONES> getTraducciones(Long id_tabla, String rowkey) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT I.ID_IDIOMA, I.NOMBRE, ");
        if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
            sql.append(" NVL(T.ID_TABLA, ").append(id_tabla).append(") AS ID_TABLA,");
            sql.append(" NVL(T.ROWKEY, ' ') AS ROWKEY,");
            sql.append(" NVL(T.TEXTO, ' ') AS TEXTO ");
        } else {
            sql.append(" IF(T.ID_TABLA IS NULL, ").append(id_tabla).append(", T.ID_TABLA) AS ID_TABLA,");
            sql.append(" IF(T.ROWKEY IS NULL, ' ', T.ROWKEY) AS ROWKEY,");
            sql.append(" IF(T.TEXTO IS NULL, ' ', T.TEXTO) AS TEXTO ");
        }
        sql.append("FROM APPEX_IDIOMAS I LEFT JOIN ");
        sql.append(" (SELECT ID_IDIOMA, ROWKEY, TEXTO, ID_TABLA FROM APPEX_TRADUCCIONES ");
        sql.append("WHERE ROWKEY = :rowkey AND ID_TABLA = :id_tabla");
        sql.append(" ) T ON T.ID_IDIOMA = I.ID_IDIOMA WHERE I.ACTIVO = 1");
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql.toString())
                .setParameter("id_tabla", id_tabla)
                .setParameter("rowkey", rowkey)
                .setCacheable(false);
        List qRes = query.list();
        List<APPEX_TRADUCCIONES> result = new ArrayList<APPEX_TRADUCCIONES>();
        for (Object qRe : qRes) {
            Object[] item = (Object[]) qRe;
            APPEX_TRADUCCIONES trad = new APPEX_TRADUCCIONES();
            trad.setIdioma((String) item[1]);
            trad.setRowkey((String) item[3]);
            trad.setTexto((String) item[4]);
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
                trad.setID_IDIOMA((BigDecimal) item[0]);
                trad.setID_TABLA((BigDecimal) item[2]);
            } else {
                trad.setID_IDIOMA((Integer) item[0]);
                trad.setID_TABLA((BigInteger) item[2]);
            }
            result.add(trad);
        }
        return result;
    }

    /**
     * Elimina las traducciones de un item
     * @param tabla Nombre de la tabla
     * @param rowkey Llave de campo
     */
    public static void deleteTraducciones(String tabla, String rowkey) {
        Long id_tabla = APPBS_TABLAS_MANAGER.getTableId(tabla);
        if (id_tabla != null)
            HibernateUtil.executeInsert("DELETE FROM APPEX_TRADUCCIONES WHERE ID_TABLA = "+id_tabla+" AND ROWKEY = "+ Converter.getSQLString(rowkey));
    }
}
