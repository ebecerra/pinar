package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Devtools.
 * Date: 31/10/2011
 */
public class APPBS_USUARIOS_LOG_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_USUARIOS_LOG";

    public static final long ID_TIPO_ERROR              = 1;

    public static final long ID_TIPO_ERR_UPDATE         = 10;
    public static final long ID_TIPO_ERR_DELETE         = 11;
    public static final long ID_TIPO_ERR_LIST           = 12;

    public static final long ID_TIPO_UPDATE             = 100;
    public static final long ID_TIPO_DELETE             = 101;
    public static final long ID_TIPO_LIST               = 102;

    public static final long ID_TIPO_EMAIL_TEMPL        = 250;
    public static final long ID_TIPO_EMAIL_OK           = 251;
    public static final long ID_TIPO_EMAIL_ERR          = 252;

    public static final long ID_TIPO_USER_LOGIN         = 300;
    public static final long ID_TIPO_USER_LOGOUT        = 301;

    public static final long ID_TIPO_DIRECCIONES_LOAD   = 500;
    public static final long ID_TIPO_DIRECCIONES_SAVE   = 501;

    public static final long ID_TIPO_CUENTABAN_LOAD     = 510;
    public static final long ID_TIPO_CUENTABAN_SAVE     = 511;

    public static final long ID_TIPO_IVA_LIST           = 520;
    public static final long ID_TIPO_IVA_SAVE           = 521;

    public static final long ID_TIPO_CONTACTOS_LOAD     = 530;
    public static final long ID_TIPO_CONTACTOS_SAVE     = 531;
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
    public static APPBS_USUARIOS_LOG findFirstByFilter(Map filters) {
        return  (APPBS_USUARIOS_LOG) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "fecha", "id_aplicacion", "id_usuario", "id_tipo"
     */
    public static APPBS_USUARIOS_LOG findByKey(Date fecha ,String id_aplicacion,Long id_usuario,Long id_tipo) {
        Map filters = new HashMap();
        filters.put("fecha", fecha);
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_usuario", id_usuario);
        filters.put("id_tipo", id_tipo);

        return findFirstByFilter(filters);
    }

    public static APPBS_USUARIOS_LOG findByKey(Map keyValues) {
        EntityBean bean = new APPBS_USUARIOS_LOG();
        return (APPBS_USUARIOS_LOG) findByKey(bean, keyValues);
    }

    /**
     * Escribe al log del usuario
     * @param id_usuario Id. del usuario
     * @param id_tipo Id. del tipo (Lookup de la tabla APPBS_USUARIOS_LOG_LEGEND
     * @param nivel Nivel de log
     * @param sesion_id Id. de la sesion del usuario
     * @param text1 Texto 1
     * @param text2 Texto 2
     * @param text3 Texto 3
     * @param text4 Texto 4
     * @param text5 Texto 5
     */
    public static void writeLog(Long id_usuario, Long id_tipo, Long nivel, String sesion_id, String text1, String text2, String text3, String text4, String text5) {
        APPBS_USUARIOS_LOG log = new APPBS_USUARIOS_LOG();
        log.setId_usuario(id_usuario);
        log.setId_aplicacion(Constants.APP_NAME);
        log.setId_tipo(id_tipo);
        log.setFecha(new Timestamp(System.currentTimeMillis()));
        log.setNivel(nivel);
        log.setSesion_id(sesion_id);
        log.setTexto1(text1);
        log.setTexto2(text2);
        log.setTexto3(text3);
        log.setTexto4(text4);
        log.setTexto5(text5);
        save(log);
    }

    public static void writeLog(Long id_usuario, Long id_tipo, Long nivel, String sesion_id, String text1, String text2, String text3, String text4) {
        writeLog(id_usuario, id_tipo, nivel, sesion_id, text1, text2, text3, text4, null);
    }
    public static void writeLog(Long id_usuario, Long id_tipo, Long nivel, String sesion_id, String text1, String text2, String text3) {
        writeLog(id_usuario, id_tipo, nivel, sesion_id, text1, text2, text3, null, null);
    }
    public static void writeLog(Long id_usuario, Long id_tipo, Long nivel, String sesion_id, String text1, String text2) {
        writeLog(id_usuario, id_tipo, nivel, sesion_id, text1, text2, null, null, null);
    }
    public static void writeLog(Long id_usuario, Long id_tipo, Long nivel, String sesion_id, String text1) {
        writeLog(id_usuario, id_tipo, nivel, sesion_id, text1, null, null, null, null);
    }
    public static void writeLog(Long id_usuario, Long id_tipo, Long nivel, String sesion_id) {
        writeLog(id_usuario, id_tipo, nivel, sesion_id, null, null, null, null, null);
    }
    public static void writeLog(Long id_usuario, String sesion_id, APPBS_USUARIOS_LOG log) {
        log.setId_usuario(id_usuario);
        log.setId_aplicacion(Constants.APP_NAME);
        log.setFecha(new Timestamp(System.currentTimeMillis()));
        log.setSesion_id(sesion_id);
        save(log);
    }

    /**
     * Obtiene la lista del Log
     * @param id_usuario Id. del usuario
     * @param id_tipo Id. del tipo (APPBS_USUARIOS_LOG_LEGEND)
     * @param nivel Nivel del log
     * @param fec_from Fecha de inicio
     * @param fec_to Fecha final
     * @param start Elemento en el que se comienza
     * @param limit Cantidad de elementos
     * @return Lista de Log
     */
    public static List<APPBS_USUARIOS_LOG> listLog(Long id_usuario, Long id_tipo, Long nivel, Date fec_from, Date fec_to, int start, int limit, String sort, String dir) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(getListQuery(id_usuario, id_tipo, nivel, fec_from, fec_to, false, sort, dir)).setCacheable(false);
        if (start != -1 && limit != -1) {
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }
        List qRes = query.list();
        List<APPBS_USUARIOS_LOG> result = new ArrayList<APPBS_USUARIOS_LOG>();
        for (int i = 0; i < qRes.size(); i++) {
            Object[] item = (Object[]) qRes.get(i);
            APPBS_USUARIOS_LOG uLog = new APPBS_USUARIOS_LOG();
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
                uLog.setId_usuario(((BigDecimal) item[0]).longValue());
                uLog.setNivel(((BigDecimal) item[2]).longValue());
                uLog.setId_tipo(((BigDecimal) item[3]).longValue());
            } else {
                uLog.setId_usuario(((Integer) item[0]).longValue());
                uLog.setNivel(((Integer) item[2]).longValue());
                uLog.setId_tipo(((Integer) item[3]).longValue());
            }
            uLog.setFecha((Timestamp) item[1]);
            uLog.setUsuario((String) item[4]);
            uLog.setDescripcion((String) item[5]);
            uLog.setTexto1((String) item[6]);
            uLog.setTexto2((String) item[7]);
            uLog.setTexto3((String) item[8]);
            uLog.setTexto4((String) item[9]);
            uLog.setTexto5((String) item[10]);
            uLog.setSesion_id((String) item[11]);
            result.add(uLog);
        }
        return result;
    }

    public static int listLogCount(Long id_usuario, Long id_tipo, Long nivel, Date fec_from, Date fec_to) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(getListQuery(id_usuario, id_tipo, nivel, fec_from, fec_to, true, null, null)).setCacheable(false);
        Object result = query.uniqueResult();
        if (result instanceof BigDecimal)
            return ((BigDecimal) result).intValue();
        else
            return ((BigInteger) result).intValue();
    }

    private static String getListQuery(Long id_usuario, Long id_tipo, Long nivel, Date fec_from, Date fec_to, boolean count, String sort, String dir) {
        StringBuilder sql = new StringBuilder();
        if (count)
            sql.append("SELECT COUNT(*) ");
        else {
            sql.append("SELECT L.ID_USUARIO, L.FECHA, L.NIVEL, L.ID_TIPO, U.NOMBRE, ");
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
                sql.append("REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LL.TEXTO, '$1', L.TEXTO1), '$2', L.TEXTO2), '$3', L.TEXTO3), '$4', L.TEXTO4), '$5', L.TEXTO5) AS DESCRIPCION, ");
            } else {
                sql.append("REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LL.TEXTO, '$1', IFNULL(L.TEXTO1, '')), ");
                sql.append("'$2', IFNULL(L.TEXTO2, '')), '$3', IFNULL(L.TEXTO3, '')), '$4', IFNULL(L.TEXTO4, '')), '$5', IFNULL(L.TEXTO5, '')) AS DESCRIPCION, ");
            }
            sql.append("L.TEXTO1, L.TEXTO2, L.TEXTO3, L.TEXTO4, L.TEXTO5, L.SESION_ID ");
        }
        sql.append("FROM APPBS_USUARIOS_LOG L JOIN APPBS_USUARIOS_LOG_LEGEND LL ON LL.ID_TIPO = L.ID_TIPO ");
        sql.append("JOIN APPBS_USUARIOS U ON L.ID_USUARIO = U.ID_USUARIO ");
        sql.append("WHERE L.ID_APLICACION = '").append(Constants.APP_NAME).append("'");
        if (!Converter.isEmpty(id_usuario))
            sql.append(" AND L.ID_USUARIO = ").append(id_usuario);
        if (!Converter.isEmpty(id_tipo))
            sql.append(" AND L.ID_TIPO = ").append(id_tipo);
        if (!Converter.isEmpty(nivel))
            sql.append(" AND L.NIVEL = ").append(nivel);
        if (!Converter.isEmpty(fec_from))
            sql.append(" AND L.FECHA >= ").append(Converter.getSQLDate(fec_from, true));
        if (!Converter.isEmpty(fec_to))
            sql.append(" AND L.FECHA <= ").append(Converter.getSQLDate(fec_to, true));
        if (!count)
            sql.append(" ORDER BY ").append(sort).append(" ").append(dir);
        return sql.toString();
    }
}
