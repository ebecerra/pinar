package com.vincomobile.model.manager;

import com.befasoft.common.actions.BodyResult;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.ARTICULOS;
import com.vincomobile.model.appdb.ARTICULOS_DESCRIP;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 06/04/2011
 */
public class ARTICULOS_MANAGER extends BeanManager {

    public static String BEAN_NAME = "ARTICULOS";

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

    public static List findByFilter(Map filters, int start, int limit) {
        return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static ARTICULOS findFirstByFilter(Map filters) {
        return  (ARTICULOS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     */
    public static ARTICULOS findByKey(Long item_id) {
        Map filters = new HashMap();
        filters.put("item_id", item_id);

        return findFirstByFilter(filters);
    }

    public static ARTICULOS findByKey(Map keyValues) {
        EntityBean bean = new ARTICULOS();
        return (ARTICULOS) findByKey(bean, keyValues);
    }

    /**
     * Lista los articulos asociados
     * @param item_id Id. del articulo
     * @return  Lista de articulos asociados
     */
    public static List<ARTICULOS> findArticulosAsociados(Long item_id) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select item_id ");
        queryString.append("from ARTICULOS where item_id in (");
        queryString.append(" select item_id_1 from ARTICULOS_ASOCIADOS where item_id_2 = :item_id");
        queryString.append(" union");
        queryString.append(" select item_id_2 from ARTICULOS_ASOCIADOS where item_id_1 = :item_id)");
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(queryString.toString()).setParameter("item_id", item_id).setCacheable(false);
        return query.setResultTransformer(Transformers.aliasToBean(ARTICULOS.class)).list();
    }

    /**
     * Obtiene la descripcion agrupada de un producto
     * @param item_id Id. del articulo
     * @param id_idioma Id. del idioma
     * @return Descripcion  agrupada
     */
    public static String getDescAgrupada(Long item_id, String id_idioma) {
        StringBuilder select = new StringBuilder();
        select.append("SELECT CONCAT(CONCAT(CONCAT(TP.NOMBRE, ' ('), M.NOMBRE), ')') AS DESCRIPCION ");
        select.append("FROM ARTICULOS A");
        select.append(" JOIN MATERIALES M ON A.ID_MATERIAL = M.ID_MATERIAL AND M.ID_IDIOMA = '").append(id_idioma).append("'");
        select.append(" JOIN TIPO_PRODUCTO TP ON A.ID_TIPO = TP.ID_TIPO AND TP.ID_IDIOMA = '").append(id_idioma).append("' ");
        select.append("WHERE A.ITEM_ID = ").append(item_id);
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(select.toString()).setCacheable(false);
        return (String) query.uniqueResult();
    }

    /**
     * Busca los articulos
     * @param bodyResult Bean donde se almacena el resultado
     * @param start Primer articulo
     * @param limit Cantidad de articulos
     * @param id_idioma Id. del idioma
     * @param id_familia Id. de la familia
     * @param id_subfamilia Id. de la subfamilia
     * @param producto Nombre o referencia del producto
     * @param verde Disponibilidad
     * @param amarillo Disponibilidad
     * @param naranja Disponibilidad
     * @param rojo Disponibilidad
     * @param novedad Novedad
     * @param promocion Promocion
     * @param id_moneda Id. de la moneda
     */
    public static void findArticulos(BodyResult bodyResult, int start, int limit, String id_idioma, String id_familia, String id_subfamilia, String producto, Boolean verde, Boolean amarillo, Boolean naranja, Boolean rojo, Boolean novedad, Boolean promocion, String id_moneda) {
        StringBuilder select = new StringBuilder();
        select.append("SELECT A.*, ");
        if (Converter.isEmpty(producto))
            select.append("AA.AGRUPADOS, ");
        else
            select.append("1 AS AGRUPADOS, ");
        select.append("AD.DESCRIPCION AS FULL_DESCRIPCION, CONCAT(TP.NOMBRE, DECODE(M.ID_MATERIAL, 'NA', '', ' ('||M.NOMBRE||')')) AS DESCRIPCION,");
        select.append(" C1.NOMBRE AS COLOR_1, C2.NOMBRE AS COLOR_2, C3.NOMBRE AS COLOR_3 ");
        StringBuilder from = new StringBuilder();
        from.append("FROM ARTICULOS A JOIN ARTICULOS_DESCRIP AD ON A.ITEM_ID = AD.ITEM_ID AND AD.ID_IDIOMA = '").append(id_idioma).append("' AND A.VISIBLE IN ('AMBAS', 'PRIVADA')");
        from.append(" JOIN MATERIALES M ON A.ID_MATERIAL = M.ID_MATERIAL AND M.ID_IDIOMA = '").append(id_idioma).append("'");
        from.append(" JOIN TIPO_PRODUCTO TP ON A.ID_TIPO = TP.ID_TIPO AND TP.ID_IDIOMA = '").append(id_idioma).append("'");
        if (Converter.isEmpty(producto))
            from.append(" JOIN (SELECT ID_AGRUPACION, MAX(ITEM_ID) AS ITEM_ID, COUNT(ITEM_ID) AS AGRUPADOS FROM ARTICULOS GROUP BY ID_AGRUPACION) AA ON A.ITEM_ID = AA.ITEM_ID");
        from.append(" LEFT JOIN COLORES C1 ON A.ID_COLOR_1 = C1.ID_COLOR AND C1.ID_IDIOMA = '").append(id_idioma).append("'");
        from.append(" LEFT JOIN COLORES C2 ON A.ID_COLOR_2 = C2.ID_COLOR AND C2.ID_IDIOMA = '").append(id_idioma).append("'");
        from.append(" LEFT JOIN COLORES C3 ON A.ID_COLOR_3 = C3.ID_COLOR AND C3.ID_IDIOMA = '").append(id_idioma).append("' ");
        if (promocion && Converter.isEmpty(producto)) {
            from.append(" JOIN PROMOCIONES_LINEAS PL ON PL.ITEM_ID = A.ITEM_ID ");
            from.append(" JOIN PROMOCIONES P ON PL.ID_PROMOCION = P.ID_PROMOCION AND P.WEB = 'Y' ");
        }
        StringBuilder where = new StringBuilder();
        if (Converter.isEmpty(producto)) {
            if (id_familia == null) {
                if (novedad)
                    where.append(" AND A.NOVEDAD <> 'N'");
                if (promocion)
                    where.append(" AND P.ID_MONEDA = '").append(id_moneda).append("'");
            } else {
                where.append(" AND A.ID_FAMILIA_PRIV = '").append(id_familia).append("'");
                if (id_subfamilia != null)
                    where.append(" AND A.ID_SUBFAMILIA_PRIV = '").append(id_subfamilia).append("'");
            }
            List<String> fecha_disp = new ArrayList<String>();
            if (verde)
                fecha_disp.add("VERDE");
            if (amarillo)
                fecha_disp.add("AMARILLO");
            if (naranja)
                fecha_disp.add("NARANJA");
            if (rojo)
                fecha_disp.add("ROJO");
            if (fecha_disp.size() > 0 && fecha_disp.size() < 4) {
                String in = "";
                for (int i = 0; i < fecha_disp.size(); i++) {
                    if (i != 0)
                        in += ", ";
                    in += "'"+fecha_disp.get(i)+"'";
                }
                where.append(" AND FECHA_DISP IN (").append(in).append(") ");
            }
        } else {
            producto = producto.toUpperCase();
            where.append(" AND UPPER(AD.DESCRIPCION) LIKE '%").append(producto).append("%'");
            where.append(" OR UPPER(A.REFERENCIA) LIKE '%").append(producto).append("%' ");
        }
        where.append(" ORDER BY TP.NOMBRE, M.NOMBRE, C1.NOMBRE, C2.NOMBRE, C3.NOMBRE, A.REFERENCIA");
        String where2 = where.toString();
        where2 = "WHERE "+where2.substring(5);
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(select.toString()+from.toString()+where2).setCacheable(false);
        if (limit > 0) {
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }
        bodyResult.setElements(query.setResultTransformer(Transformers.aliasToBean(ARTICULOS.class)).list());
        query = (SQLQuery) session.createSQLQuery("SELECT COUNT(*) "+from.toString()+where2).setCacheable(false);
        bodyResult.setTotalCount(((BigDecimal) query.uniqueResult()).intValue());
    }

    /**
     * Obtiene la lista de articulos que pertenecen a una agrupacion
     * @param id_idioma Id. del idioma
     * @param id_agrupacion Id. de la agrupacion
     * @return Lista de articulos
     */
    public static List<ARTICULOS> findArticulosAgrupados(String id_idioma, String id_agrupacion) {
        StringBuffer select = new StringBuffer();
        select.append("SELECT A.*, AD.DESCRIPCION AS FULL_DESCRIPCION ");
        select.append("FROM ARTICULOS A JOIN ARTICULOS_DESCRIP AD ON A.ITEM_ID = AD.ITEM_ID AND AD.ID_IDIOMA = '").append(id_idioma).append("' ");
        select.append("WHERE A.ID_AGRUPACION = '").append(id_agrupacion).append("'");
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(select.toString()).setCacheable(false);
        return query.setResultTransformer(Transformers.aliasToBean(ARTICULOS.class)).list();
    }

    /**
     * Retorna la cantidad de productos agrupados
     * @param id_agrupacion Id. de la agrupacion
     * @return Cantidad de productos
     */
    public static Long findArticulos(String id_agrupacion) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT COUNT(*) FROM ARTICULOS WHERE ID_AGRUPACION = :id_agrupacion").setParameter("id_agrupacion", id_agrupacion).setCacheable(false);
        return ((BigDecimal) query.uniqueResult()).longValue();        
    }

    /**
     * Carga la información extra de un articulo: Descripcion y fotos
     * @param articulo Articulo
     * @param id_idioma Id. del idioma
     */
    public static void loadExtraInfo(ARTICULOS articulo, String id_idioma) {
        if (articulo != null) {
            // Descripcion y fotos
            ARTICULOS_DESCRIP art_desc = ARTICULOS_DESCRIP_MANAGER.findByKey(articulo.getItem_id(), id_idioma);
            if (art_desc != null) {
                articulo.setFull_descripcion(art_desc.getDescripcion());
                articulo.setDescripcion(art_desc.getDescripcion());
                articulo.setFotos(ARTICULOS_FOTOS_MANAGER.findByItem_id(articulo.getItem_id()));
            }
        }
    }

    public static void main(String [] args) {
//         List l = CLIENTES_MANAGER.getClientesByVendedor(100012540L);
         StringBuffer queryString = new StringBuffer();
         queryString.append("SELECT A.*, AD.DESCRIPCION AS FULL_DESCRIPCION, CONCAT(CONCAT(CONCAT(TP.NOMBRE, ' ('), M.NOMBRE), ')') AS DESCRIPCION, C1.NOMBRE AS COLOR_1, C2.NOMBRE AS COLOR_2, C3.NOMBRE AS COLOR_3 ");
         queryString.append("FROM ARTICULOS A JOIN ARTICULOS_DESCRIP AD ON A.ITEM_ID = AD.ITEM_ID AND AD.ID_IDIOMA = 'ES' ");
         queryString.append("JOIN MATERIALES M ON A.ID_MATERIAL = M.ID_MATERIAL AND M.ID_IDIOMA = 'ES' ");
         queryString.append("JOIN TIPO_PRODUCTO TP ON A.ID_TIPO = TP.ID_TIPO AND TP.ID_IDIOMA = 'ES' ");
         queryString.append("LEFT JOIN COLORES C1 ON A.ID_COLOR_1 = C1.ID_COLOR AND C1.ID_IDIOMA = 'ES' ");
         queryString.append("LEFT JOIN COLORES C2 ON A.ID_COLOR_2 = C2.ID_COLOR AND C2.ID_IDIOMA = 'ES' ");
         queryString.append("LEFT JOIN COLORES C3 ON A.ID_COLOR_3 = C3.ID_COLOR AND C3.ID_IDIOMA = 'ES' ");
         queryString.append("WHERE A.ID_FAMILIA_PRIV = '004' AND A.ID_SUBFAMILIA_PRIV = '109' ");
         queryString.append("AND UPPER(AD.DESCRIPCION) LIKE '%CROMO%' ");
         Session session = HibernateUtil.currentSession();
         SQLQuery query = (SQLQuery) session.createSQLQuery(queryString.toString()).setCacheable(false);
         List<ARTICULOS> arts = query.setResultTransformer(Transformers.aliasToBean(ARTICULOS.class)).list();
         System.out.println(arts.size());
         for (int i = 0; i < arts.size(); i++) {
             System.out.println(arts.get(i));
         }
    }
}
