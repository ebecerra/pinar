package com.befasoft.common.model.manager;

import com.befasoft.common.beans.SINCRO_TABLE;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.model.appbs.APPBS_FOREINGKEYS;
import com.befasoft.common.model.appbs.APPBS_FOREINGKEYS_VALUES;
import com.befasoft.common.model.appbs.APPBS_TABLAS;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.*;

/**
 * Created by Devtools.
 * Date: 28/12/2011
 */
public class APPBS_FOREINGKEYS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_FOREINGKEYS";

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
    public static APPBS_FOREINGKEYS findFirstByFilter(Map filters) {
        return  (APPBS_FOREINGKEYS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_key"
     */
    public static APPBS_FOREINGKEYS findByKey(Long id_key ) {
        Map filters = new HashMap();
        filters.put("id_key", id_key);

        return findFirstByFilter(filters);
    }

    public static APPBS_FOREINGKEYS findByKey(Map keyValues) {
        EntityBean bean = new APPBS_FOREINGKEYS();
        return (APPBS_FOREINGKEYS) findByKey(bean, keyValues);
    }

    /**
     * Obtiene los datos dado un FK (obtiene todos los datos que dependan recursivamente utilizando las FK)
     *
     * @param result Lista de actualizacion
     * @param id_tabla Id. de la tabla
     * @param filters Filtros para obtener las filas
     */
    public static void getFKData(List<SINCRO_TABLE> result, Long id_tabla, HashMap filters) {
        if (id_tabla != 0) {
            APPBS_TABLAS table = APPBS_TABLAS_MANAGER.findByKey(id_tabla);
            APPBS_UPDATES_MANAGER.initFields(table);
            SINCRO_TABLE sincro = getSincroInfo(result, table);
            // Obtiene las filas de la tabla que cumplen con el criterio de filtrado
            String sql = getTableSelect(table, filters);
            Session session = HibernateUtil.currentSession();
            SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setProperties(filters).setCacheable(false);
            List qRes = query.list();
            for (Object qRe : qRes) {
                // Genera los INSERTS
                StringBuilder insert = new StringBuilder();
                StringBuilder insertExtra = new StringBuilder();
                insert.append("INSERT INTO ").append(table.getNombre()).append(" (").append(table.getAll_names()).append(") VALUES (");
                Object[] item = (Object[]) qRe;
                int indx = 0, indxExtra = 0;
                for (int j = 0; j < table.getCampos().size(); j++) {
                    APPBS_CAMPOS campo = table.getCampos().get(j);
                    if (campo.getExportable()) {
                        if (campo.getFuente() == 'B') {
                            // Campos reales (DB FIELDS)
                            if (indx != 0)
                                insert.append(", ");
                            campo.setValue(item[indx++]);
                            insert.append(campo.getSQLExportValue());
                        } else {
                            // Campos extras
                            if (indxExtra != 0)
                                insertExtra.append(", ");
                            indxExtra++;
                            insertExtra.append(campo.getSQLValue());
                        }
                    }
                }
                if (indxExtra > 0) {
                    if (indx > 0)
                        insert.append(", ");
                    insert.append(insertExtra);
                }
                insert.append(")");
                sincro.add(insert.toString());
                // Obtiene los valores relacionados por la FK
                HashMap fkFlt = new HashMap();
                fkFlt.put("id_reftabla", id_tabla);
                List<APPBS_FOREINGKEYS_VALUES> keys = APPBS_FOREINGKEYS_VALUES_MANAGER.findByFilter(fkFlt);
                if (!Converter.isEmpty(keys)) {
                    fkFlt.clear();
                    Long fk_table = 0L;
                    for (APPBS_FOREINGKEYS_VALUES key_value : keys) {
                        fk_table = key_value.getId_tabla();
                        if (key_value.getFk().getExportable()) {
                            APPBS_CAMPOS campo = table.getCampo(key_value.getCampo().getNombre());
                            if (campo != null)
                                fkFlt.put(key_value.getCampo().getNombre(), campo.getOrg_value());
                        }
                    }
                    getFKData(result, fk_table, fkFlt);
                }
            }
        }
    }

    private static SINCRO_TABLE getSincroInfo(List<SINCRO_TABLE> result, APPBS_TABLAS table) {
        for (SINCRO_TABLE sincro : result) {
            if (sincro.getTable().equals(table.getNombre()))
                return sincro;
        }
        SINCRO_TABLE sincro = new SINCRO_TABLE(table.getNombre(), table.getVersion());
        result.add(sincro);
        return sincro;
    }

    /**
     * Obtiene la select para obtener una fila de una table filtrada por el filtro pasado
     * @param table Informacion de la tabla
     * @param filters Filtro a aplicar
     * @return Select
     */
    private static String getTableSelect(APPBS_TABLAS table, HashMap filters) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(table.getCampos_names()).append(" FROM ").append(table.getNombre()).append(" WHERE ");
        Iterator iterator = filters.keySet().iterator();
        int i = 0;
        while(iterator. hasNext()) {
            if (i != 0)
                sql.append(" AND ");
            String key = (String) iterator.next();
            sql.append(key).append(" = :").append(key);
            i++;
        }
        return sql.toString();
    }
}
