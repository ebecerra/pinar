package com.befasoft.common.model.manager;

import com.befasoft.common.beans.SINCRO_ERROR;
import com.befasoft.common.beans.SINCRO_TABLE;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.model.appbs.APPBS_TABLAS;
import com.befasoft.common.model.appbs.APPBS_UPDATES;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.util.*;

/**
 * Created by Devtools.
 * Date: 20/12/2011
 */
public class APPBS_UPDATES_MANAGER extends BeanManager {

    private static String BEAN_NAME = "APPBS_UPDATES";

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
    public static APPBS_UPDATES findFirstByFilter(Map filters) {
        return  (APPBS_UPDATES) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_update"
     */
    public static APPBS_UPDATES findByKey(Long id_update ) {
        Map filters = new HashMap();
        filters.put("id_update", id_update);

        return findFirstByFilter(filters);
    }

    public static APPBS_UPDATES findByKey(Map keyValues) {
        EntityBean bean = new APPBS_UPDATES();
        return (APPBS_UPDATES) findByKey(bean, keyValues);
    }

    /**
     * Inicializa los campos de la DB que sean constantes, parametros o maximos
     * @param table Informacion de la tabla
     */
    public static void initFields(APPBS_TABLAS table)  {
        for (int i = 0; i < table.getCampos().size(); i++) {
            APPBS_CAMPOS campo = table.getCampos().get(i);
            // B - Base de datos, C - Constante, P - Parametro, M - Maximo columna, F - Funcion, S - Select
            switch (campo.getFuente()) {
                case 'C':
                    campo.setValue(campo.getFuente_valor1());
                    break;
                case 'P':
                    campo.setValue(APPBS_PARAMETROS_MANAGER.getStrParameter(campo.getFuente_valor1()));
                    break;
                case 'M':
                    campo.setValue(getFieldMaxColumn(campo.getFuente_valor1(), campo.getFuente_valor2()));
                    break;
            }
        }
    }

    /**
     * Obtiene la informacion de actualización de una tabla completa
     * @param sincro Informacion de sincronizacion (a completar)
     * @param table Informacion de la tabla
     * @param filters Filtro a aplicar
     * @param param_1 Parametros extra 1
     * @param param_2 Parametros extra 2
     * @param param_3 Parametros extra 3
     * @param param_4 Parametros extra 4
     * @param param_5 Parametros extra 5
     */
    public static void getTableUpdateSQL(SINCRO_TABLE sincro, APPBS_TABLAS table, String filters, String param_1, String param_2, String param_3, String param_4, String param_5) {
        sincro.setDeleteSt("DELETE FROM " + table.getNombre() + (Converter.isEmpty(table.getDelete_where()) ? "" : " WHERE " + table.getDelete_where()));
        Session session = HibernateUtil.currentSession();
        String tSql;
        if (!Converter.isEmpty(table.getExtra_where())) {
            filters = table.getExtra_where() + (Converter.isEmpty(filters) ? "" : " AND " + filters);
        }
        if (!Converter.isEmpty(table.getXselect()) || !Converter.isEmpty(table.getXfrom()) || !Converter.isEmpty(table.getXwhere())) {
            // Select utilizando los campos XSELECT, XFROM, XWHERE y XGROUP
            tSql = "SELECT ";
            if (Converter.isEmpty(table.getXselect()))
                tSql += table.getCampos_select();
            else
                tSql += table.getXselect();
            if (Converter.isEmpty(table.getXfrom()))
                tSql += " FROM "+table.getNombre();
            else
                tSql += " FROM "+replaceParameters(table.getXfrom(), param_1, param_2, param_3, param_4, param_5);
            if (!Converter.isEmpty(table.getXwhere())) {
                tSql += " WHERE "+replaceParameters(table.getXwhere(), param_1, param_2, param_3, param_4, param_5);
                if (!Converter.isEmpty(filters))
                    tSql += " AND "+replaceParameters(filters, param_1, param_2, param_3, param_4, param_5);
                if (!Converter.isEmpty(table.getXgroup()))
                    tSql += " GROUP BY "+table.getXgroup();
            } else if (!Converter.isEmpty(filters))
                tSql += " WHERE "+replaceParameters(filters, param_1, param_2, param_3, param_4, param_5);
        } else {
            // Select normal de la tabla
            tSql = "SELECT "+table.getCampos_select()+" FROM "+table.getNombre();
            if (!Converter.isEmpty(filters))
                tSql += " WHERE "+replaceParameters(filters, param_1, param_2, param_3, param_4, param_5);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(table.getNombre()).append(" (").append(table.getAll_names());
        sql.append(") VALUES (").append(table.getAll_names_params()).append(")");
        sincro.setInsertSt(sql.toString());
        SQLQuery query = (SQLQuery) session.createSQLQuery(tSql).setCacheable(false);
        List result = query.list();
        for (Object row : result) {
            sincro.add(getListValue((Object[]) row, table.getCampos(), null, true));
        }
    }

    private static String replaceParameters(String value, String param_1, String param_2, String param_3, String param_4, String param_5) {
        if (value == null)
            return null;
        value = value.replaceAll("@1", param_1).replaceAll("@2", param_2).replaceAll("@3", param_3).replaceAll("@4", param_4).replaceAll("@5", param_5);
        if (value.indexOf("@TODAY") > 0) {
            value = value.replaceAll("@TODAY", Converter.getSQLDate(new Date()));
        }
        if (value.indexOf("@NOW") > 0) {
            value = value.replaceAll("@NOW", Converter.getSQLDateTime(new Date(), false));
        }
        return value;
    }

    /**
     * Genera el SQL de actualizacion de una fila
     *
     * @param table Informacion de la tabla
     * @param update Información de actualizacion
     * @return SQL de actualizacion
     */
    public static String getUpdateSQL(APPBS_TABLAS table, APPBS_UPDATES update) {
        StringBuilder sql = new StringBuilder();
        if ("I".equals(update.getOperacion())) {
            String where = update.fillKeyValues(table.getKeys());
            String values = getRowValues(table, update, where, true);
            if (values != null) {
                sql.append("INSERT INTO ").append(table.getNombre()).append(" (").append(table.getAll_names());
                sql.append(") VALUES (").append(values).append(")");
            }
        } else if ("D".equals(update.getOperacion())) {
            sql.append("DELETE FROM ").append(table.getNombre()).append(" WHERE ").append(update.fillKeyValues(table.getKeys()));
        } else {
            String where = update.fillKeyValues(table.getKeys());
            String values = getRowValues(table, update, where, false);
            if (values != null) {
                sql.append("UPDATE ").append(table.getNombre()).append(" SET ").append(values);
                sql.append(" WHERE ").append(where);
            }
        }
        return sql.toString();
    }

       /**
        * Obtiene los valores para hacer el INSERT/UPDATE
        * @param item Datos de la fila
        * @param campos Informacion de los campos
        * @param update Información de actualizacion
        * @param insert Indica si es un INSERT o UPDATE
        * @return Valores para el INSERT/UPDATE
        */
    private static List getListValue(Object[] item, List<APPBS_CAMPOS> campos, APPBS_UPDATES update, boolean insert) {
        int indx = 0;
        List values = new ArrayList();
        for (APPBS_CAMPOS campo : campos) {
            if (campo.getExportable()) {
                // B - Base de datos, C - Constante, P - Parametro, M - Maximo columna, F - Funcion, S - Select
                switch (campo.getFuente()) {
                    case 'B':
                        campo.setValue(item[indx++]);
                        break;
                    case 'F':
                        if (update != null)
                            campo.setValue(getFieldFunctionValue(campo.getFuente_valor1(), update.getId_update()));
                        break;
                    case 'S':
                        campo.setValue(getFieldSelectValue(campos, campo.getFuente_valor1()));
                        break;
                }
                values.add(campo.getSqliteValue());
            }
        }
        return values;
    }

    /**
     * Obtiene los valores para hacer el INSERT/UPDATE
     * @param item Datos de la fila
     * @param campos Informacion de los campos
     * @param update Información de actualizacion
     * @param insert Indica si es un INSERT o UPDATE
     * @return Valores para el INSERT/UPDATE
     */
    private static String getRowValue(Object[] item, List<APPBS_CAMPOS> campos, APPBS_UPDATES update, boolean insert) {
        int indx = 0;
        StringBuilder sql = new StringBuilder();
        for (APPBS_CAMPOS campo : campos) {
            if (campo.getExportable()) {
                // B - Base de datos, C - Constante, P - Parametro, M - Maximo columna, F - Funcion, S - Select
                switch (campo.getFuente()) {
                    case 'B':
                        campo.setValue(item[indx++]);
                        break;
                    case 'F':
                        if (update != null)
                            campo.setValue(getFieldFunctionValue(campo.getFuente_valor1(), update.getId_update()));
                        break;
                    case 'S':
                        campo.setValue(getFieldSelectValue(campos, campo.getFuente_valor1()));
                        break;
                }
                if (sql.length() > 0)
                    sql.append(", ");
                sql.append(insert ? "" : campo.getNombre() + " = ").append(campo.getSQLExportValue());
            }
        }
        return sql.toString();
    }

    /**
     * Obtiene los valores para hacer el INSERT/UPDATE
     * @param table Informacion de la tabla
     * @param update Información de actualizacion
     * @param where Condicion
     * @param insert Indica si es un INSERT o UPDATE
     * @return Valores para el INSERT/UPDATE
     */
    private static String getRowValues(APPBS_TABLAS table, APPBS_UPDATES update, String where, boolean insert) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT "+table.getCampos_select()+" FROM "+table.getNombre()+" WHERE "+where).setCacheable(false);
        List result = query.list();
        if (result.size() > 0) {
            return getRowValue((Object[]) result.get(0), table.getCampos(), update, insert);
        }
        return null;
    }

    /**
     * Obtiene el máximo de una columna + 1
     * @param table Nombre de la tabla
     * @param column Nombre de la columna
     * @return Maximo + 1
     */
    private static int getFieldMaxColumn(String table, String column) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT MAX("+column+") FROM "+table).setCacheable(false);
        Integer result = (Integer) query.uniqueResult();
        return result == null ? 1 : result + 1;
    }

    /**
     * Obtiene el valor de una SELECT configurada en el campo
     * @param campos Campos de la tabla
     * @param sql Select a ejecutar
     * @return Valor que retorna la Select
     */
    private static String getFieldSelectValue(List<APPBS_CAMPOS> campos, String sql) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        String[] params = query.getNamedParameters();
        for (APPBS_CAMPOS campo : campos) {
            for (String param : params) {
                if (campo.getNombre().equals(param)) {
                    query.setParameter(param, campo.getValue());
                    break;
                }
            }
        }
        return query.uniqueResult().toString();
    }

    /**
     * Llama a una funcion de la DB [ fName(id_update) ]
     * @param fName Nombre de la funcion
     * @param id_update Id. de la fila que se esta procesando
     * @return Valor de la funcion
     */
    private static String getFieldFunctionValue(String fName, Long id_update) {
        return "";
    }

    /**
     * Importa el contenido del BufferedReader en la DB
     * @param session Sesion de DB
     * @param input Buffer
     * @param skipUnknow Ignora las tablas indefinidas
     * @param separator Separador de campos
     * @return Código de error
     * @throws Exception Error en el proceso
     */
    public static SINCRO_ERROR makeUpdate(Session session, BufferedReader input, boolean skipUnknow, String separator) throws Exception {
        SINCRO_ERROR result = new SINCRO_ERROR();
        String line = null;
        APPBS_TABLAS currentTable = null;
        boolean startTable = true, startFields = false;
        List<APPBS_CAMPOS> fieldList = null;
        long time = 0;
        boolean skipMode = false;
        while ((line = input.readLine()) != null) {
            line = line.trim();
            log.debug(line);
            if ("".equals(line)) {
                startTable = true;
            } else {
                if (startTable) {
                    // Inicio de una nueva tabla
                    if (time != 0) {
                        time = System.currentTimeMillis() - time;
                        log.info("\t\tTiempo: "+time/1000+"."+time%1000);
                    }
                    time = System.currentTimeMillis();
                    startTable = false; startFields = true; fieldList = null;
                    currentTable = getTableInfo(result, line);
                    skipMode = currentTable == null;
                    if (skipMode) {
                        if (skipUnknow)
                            log.info("\t\tNo existe definición para la tabla: "+line+", se ignora");
                        else {
                            log.error("\t\tNo existe definición para la tabla: " + line);
                            return result;
                        }
                    }
                } else if (startFields) {
                    startFields = false;
                    if (!skipMode) {
                        // Fila de definicion de campos
                        fieldList = loadFieldList(result, currentTable.getId_tabla(), line, separator);
                        if (fieldList == null) {
                            return result;
                        }
                        result.setRow(0);
                    }
                } else {
                    if (!skipMode) {
                        // Procesa una fila
                        if (!processRow(result, session, currentTable, fieldList, line, separator)) {
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Obtiene la informacion de la tabla
     * @param result Informacion de error
     * @param tablename Nombre de la tabla en origen
     * @return Informacion de la tabla
     */
    public static APPBS_TABLAS getTableInfo(SINCRO_ERROR result, String tablename) {
        log.info("\tTabla: "+tablename);
        Map filters = new HashMap();
        filters.put("nombre", tablename);
        APPBS_TABLAS table = APPBS_TABLAS_MANAGER.findFirstByFilter(filters);
        result.setTable(tablename);
        result.setResult(table == null ? 1 : 0);
        return table;
    }

    /**
     * Obtiene la lista de campos a procesar de la tabla
     * @param result Informacion de error
     * @param id_tabla Id. de la tabla
     * @param line Lista de nombres de campos
     * @param separator Separador de campos
     * @return Lista con la información de los campos
     */
    public static List<APPBS_CAMPOS> loadFieldList(SINCRO_ERROR result, Long id_tabla, String line, String separator) {
        log.info("\t\tCampos: "+line);
        Map filters = new HashMap();
        filters.put("id_tabla", id_tabla);
        StringTokenizer st = new StringTokenizer(line, separator);
        List<APPBS_CAMPOS> fields = new ArrayList<APPBS_CAMPOS>();
        while (st.hasMoreTokens()) {
            String name = st.nextToken();
            if (!"".equals(name)) {
                filters.put("nombre", name);
                APPBS_CAMPOS field = APPBS_CAMPOS_MANAGER.findFirstByFilter(filters);
                if (field != null) {
                    fields.add(field);
                } else {
                    log.error("No existe definición para el campo: "+name);
                    result.setResult(2);
                    result.setField(name);
                    return null;
                }
            }
        }
        return fields;
    }

    /**
     * Procesa una fila
     * @param result Informacion de error
     * @param session Sesion de DB
     * @param currentTable Informacion de la tabla que se esta procesando
     * @param fields Lista de campos
     * @param line Valores de la fila
     * @param separator Separador de campos
     * @return Verdadero si se proceso correctamente
     */
    private static boolean processRow(SINCRO_ERROR result, Session session, APPBS_TABLAS currentTable, List<APPBS_CAMPOS> fields, String line, String separator) {
        log.debug("\t\t"+line);
        result.incrementRow();
        result.setIndex(0);
        for (APPBS_CAMPOS f : fields) {
            if (f.getId_campo() != 0)
                f.setValue(getToken(result, line, separator));
        }
        int sqlResult = 0;
        // Triggers
        if (currentTable.hasTriggers()) {
            String select = APPBS_CAMPOS_MANAGER.getFindSt(fields, currentTable.getNombre());
            if (select != null) {
                SQLQuery query = (SQLQuery) session.createSQLQuery(select).setCacheable(false);
                List qResult = query.list();
                if (qResult.size() > 0) {
                    Object[] item = (Object[]) qResult.get(0);
                    int indx = 0;
                    for (APPBS_CAMPOS campo : fields) {
                        if (campo.getFuente() == 'B') {
                            campo.setOld_value(item[indx++]);
                        }
                    }
                    // Update
                    if (currentTable.beforeUpdate(session, fields, currentTable.getNombre())) {
                        String sql = APPBS_CAMPOS_MANAGER.getUpdateSt(fields, currentTable.getNombre());
                        sqlResult = sql == null ? 0 : HibernateUtil.executeInsert2(session, sql);
                        if (sqlResult != 0)
                            currentTable.afterUpdate(session, fields, currentTable.getNombre());
                        else
                            log.error("\t\tError al actualizar");
                    } else
                        sqlResult = 1;
                } else {
                    // Insert
                    if (currentTable.beforeInsert(session, fields, currentTable.getNombre())) {
                        String sql = APPBS_CAMPOS_MANAGER.getInsertSt(fields, currentTable.getNombre());
                        log.debug("\t\t"+sql);
                        sqlResult = HibernateUtil.executeInsert2(session, sql);
                        if (sqlResult != 0)
                            currentTable.afterInsert(session, fields, currentTable.getNombre());
                        else
                            log.error("\t\tError al insertar");
                    } else
                        sqlResult = 1;
                }
                if (sqlResult == 0) {
                    result.setResult(3);
                    result.setSql_error(HibernateUtil.getLast_error());
                }
            }
        } else {
            // Actualizacion sin triggers
            String sql = APPBS_CAMPOS_MANAGER.getUpdateSt(fields, currentTable.getNombre());
            sqlResult = sql == null ? 0 : HibernateUtil.executeInsert2(session, sql);
            if (sqlResult == 0) {
                sql = APPBS_CAMPOS_MANAGER.getInsertSt(fields, currentTable.getNombre());
                sqlResult = HibernateUtil.executeInsert2(session, sql);
            }
            if (sqlResult == 0) {
                result.setResult(3);
                result.setSql_error(HibernateUtil.getLast_error());
            }
        }
        return sqlResult == 1;
    }

    public static String getToken(SINCRO_ERROR result, String value, String separator) {
        String token = null;
        int pos = value.indexOf(separator, result.getIndex());
        if (pos > 0 && result.getIndex() < pos) {
            token = value.substring(result.getIndex(), pos);
            result.setIndex(pos + 1);
        } else {
            if (pos == -1 && result.getIndex() < value.length()) {
                token = value.substring(result.getIndex());
                result.setIndex(value.length());
            } else
                result.incrementIndex();
        }
        return token;
    }

}
