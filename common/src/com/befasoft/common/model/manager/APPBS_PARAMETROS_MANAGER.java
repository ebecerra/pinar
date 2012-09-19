package com.befasoft.common.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_PARAMETROS;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APPBS_PARAMETROS_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPBS_PARAMETROS";

    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    public static List findByFilter(Map filters, int start, int limit) {
        return findByFilter(BEAN_NAME, filters, start, limit, null, null);
    }

    public static List findByFilter(Map filters) {
        return findByFilter(BEAN_NAME, filters, -1, -1, null, null);
    }

    public static APPBS_PARAMETROS findFirstByFilter(Map filters){
        return  (APPBS_PARAMETROS)findFirstByFilter(BEAN_NAME, filters);
    }

    public static APPBS_PARAMETROS findByKey(String id_aplicacion, String id_parametro){

        Map filters = new HashMap();
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_parametro", id_parametro);

        return findFirstByFilter(filters);
    }
 
    public static APPBS_PARAMETROS findByKey( Map keyValues) {
        EntityBean bean = new APPBS_PARAMETROS();
        return  (APPBS_PARAMETROS)findByKey(bean, keyValues);
    }

    public static String findStringValue(String id_aplicacion, String id_parametro) {
        return findByKey(id_aplicacion, id_parametro).getValor();
    }

    public static Long findLongValue(String id_aplicacion, String id_parametro) {
        return Converter.getLong(findByKey(id_aplicacion, id_parametro).getValor());
    }

    public static Integer findIntegerValue(String id_aplicacion, String id_parametro) {
        return Converter.getInt(findByKey(id_aplicacion, id_parametro).getValor());
    }

    /**
     * Carga un entero de la tabla de parametros
     * @param id_parametro Nombre del parametro
     * @param def Valor por defecto
     * @return Entero con el valor del parametro
     */
    public static int getIntParameter(String id_parametro, int def)  {
        APPBS_PARAMETROS param = findByKey(Constants.APP_NAME, id_parametro);
        if (param == null)
            return def;
        else
            return Converter.getInt(param.getValor(), def);
    }

    public static int getIntParameter(String id_parametro) {
        return getIntParameter(id_parametro, 0);
    }

    /**
     * Carga un entero de la tabla de parametros
     * @param id_parametro Nombre del parametro
     * @param def Valor por defecto
     * @return Entero con el valor del parametro
     */
    public static BigInteger getBigIntegerParameter(String id_parametro, BigInteger def)  {
        APPBS_PARAMETROS param = findByKey(Constants.APP_NAME, id_parametro);
        if (param == null)
            return def;
        else {
            try {
                return new BigInteger(param.getValor());
            } catch (Exception ex) {
                return def;
            }
        }
    }

    public static BigInteger getBigIntegerParameter(String id_parametro) {
        return getBigIntegerParameter(id_parametro, new BigInteger("0"));
    }

    /**
     * Carga un long de la tabla de parametros
     * @param id_parametro Nombre del parametro
     * @param def Valor por defecto
     * @return Entero con el valor del parametro
     */
    public static long getLongParameter(String id_parametro, long def) {
        APPBS_PARAMETROS param = findByKey(Constants.APP_NAME, id_parametro);
        if (param == null)
            return def;
        else
            return Converter.getLong(param.getValor(), def);
    }

    public static long getLongParameter(String id_parametro) {
        return getLongParameter(id_parametro, 0);
    }

    /**
     * Carga un double de la tabla de parametros
     * @param id_parametro Nombre del parametro
     * @param def Valor por defecto
     * @return Numero con el valor del parametro
     */
    public static double getFloatParameter(String id_parametro, double def) {
        APPBS_PARAMETROS param = findByKey(Constants.APP_NAME, id_parametro);
        if (param == null)
            return def;
        else
            return Converter.getFloat(param.getValor(), def);
    }

    public static double getFloatParameter(String id_parametro) {
        return getFloatParameter(id_parametro, 0);
    }

    /**
     * Carga una cadena de la tabla de parametros
     * @param id_parametro Nombre del parametro
     * @param def Valor por defecto
     * @return Valor del parametro
     */
    public static String getStrParameter(String id_parametro, String def) {
        APPBS_PARAMETROS param = findByKey(Constants.APP_NAME, id_parametro);
        if (param == null)
            return def;
        else
            return param.getValor();
    }

    public static String getStrParameter(String id_parametro) {
        return getStrParameter(id_parametro, "");
    }

    /**
     * Carga una fecha de la tabla de parametros
     * @param id_parametro Nombre del parametro
     * @return Valor del parametro
     */
    public static Date getDateParameter(String id_parametro) {
        return getDateParameter(id_parametro, "dd/MM/yyyy");
    }

    public static Date getDateParameter(String id_parametro, String format) {
        APPBS_PARAMETROS param = findByKey(Constants.APP_NAME, id_parametro);
        if (param == null)
            return null;
        else
            return Converter.getDate(param.getValor(), format);
    }

    /**
     * Carga un boolean de la tabla de parametros
     * @param id_parametro Nombre del parametro
     * @param def Valor por defecto
     * @return Valor del parametro
     */
    public static boolean getBooleanParameter(String id_parametro, boolean def) {
        APPBS_PARAMETROS param = findByKey(Constants.APP_NAME, id_parametro);
        if (param == null)
            return def;
        else {
            String value = param.getValor().toLowerCase();
            return Converter.getBoolean(value);
        }
    }

    public static boolean getBooleanParameter(String id_parametro) {
        return getBooleanParameter(id_parametro, false);    
    }

    public static void saveParameter(String id_parametro, String nombre, String valor, String tipo) {
        APPBS_PARAMETROS param = new APPBS_PARAMETROS();
        param.setId_aplicacion(Constants.APP_NAME);
        param.setId_parametro(id_parametro);
        param.setNombre(nombre);
        param.setValor(valor);
        param.setTipo(tipo);
        param.setCheckrange("N");
        param.setEdit("S");
        param.setVisible("S");
        save(param);
    }
}