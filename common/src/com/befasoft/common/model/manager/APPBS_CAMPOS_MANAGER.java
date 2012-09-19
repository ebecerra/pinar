package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APPBS_CAMPOS_MANAGER extends BeanManager{
    private static String BEAN_NAME ="APPBS_CAMPOS";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List findByFilter(Map filters, int start, int limit){
        return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static APPBS_CAMPOS findFirstByFilter(Map filters){
        return  (APPBS_CAMPOS)findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_CAMPOS findByKey(Integer id_campo, Integer id_tabla){

        Map filters = new HashMap();
        filters.put("id_campo", id_campo);
        filters.put("id_tabla", id_tabla);

        return findFirstByFilter(filters);
    }

    public static APPBS_CAMPOS findByKey(Map keyValues) {
        EntityBean bean = new APPBS_CAMPOS();
        return (APPBS_CAMPOS) findByKey(bean, keyValues);
    }

    /**
     * Obtiene una sentencia INSERT
     * @param fields Campos
     * @param tablename Nombre de la tabla
     * @return INSERT
     */
    public static String getInsertSt(List<APPBS_CAMPOS> fields, String tablename) {
        StringBuilder ins_fields = new StringBuilder();
        StringBuilder ins_values = new StringBuilder();

        for (int i = 0; i < fields.size(); i++) {
            APPBS_CAMPOS field = fields.get(i);
            ins_fields.append(i == 0 ? "" : ", ").append(field.getNombre());
            ins_values.append(i == 0 ? "" : ", ").append(field.getSQLValue());
        }
        return  "INSERT INTO "+tablename+" ("+ins_fields.toString()+") VALUES ("+ins_values.toString()+")";
    }

    /**
     * Obtiene la sentencia UPDATE
     * @param fields Campos
     * @param tablename Nombre de la tabla
     * @return UPDATE
     */
    public static String getUpdateSt(List<APPBS_CAMPOS> fields, String tablename) {
        StringBuilder upd_keys = new StringBuilder();
        StringBuilder upd_values = new StringBuilder();

        int pk = 0, normal = 0;
        for (APPBS_CAMPOS field : fields) {
            if (field.isPk()) {
                upd_keys.append(pk == 0 ? "" : " AND ").append(field.getNombre()).append(" = ").append(field.getSQLValue());
                pk++;
            } else {
                upd_values.append(normal == 0 ? "" : ", ").append(field.getNombre()).append(" = ").append(field.getSQLValue());
                normal++;
            }
        }
        if (upd_keys.length() > 0 && upd_values.length() > 0)
            return  "UPDATE "+tablename+" SET "+upd_values.toString()+" WHERE "+upd_keys.toString();
        else
            return null;
    }

    /**
     * Obtiene la sentencia SELECT para obtener un registro
     * @param fields Campos
     * @param tablename Nombre de la tabla
     * @return SELECT
     */
    public static String getFindSt(List<APPBS_CAMPOS> fields, String tablename) {
        StringBuilder select_keys = new StringBuilder();
        StringBuilder select_fields = new StringBuilder();

        int pk = 0, normal = 0;
        for (APPBS_CAMPOS field : fields) {
            if (field.getFuente() == 'B' ) {
                select_fields.append(normal == 0 ? "" : ", ").append(field.getNombre());
                normal++;
            }
            if (field.isPk()) {
                select_keys.append(pk == 0 ? "" : " AND ").append(field.getNombre()).append(" = ").append(field.getSQLValue());
                pk++;
            }
        }
        if (select_fields.length() > 0 && select_keys.length() > 0)
            return  "SELECT "+select_fields+" FROM "+tablename+" WHERE "+select_keys.toString();
        else
            return null;
    }
}