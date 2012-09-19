package com.befasoft.common.business;

import com.befasoft.common.beans.UTL_INFOSERIES;
import com.befasoft.common.beans.UTL_INFOSERIESVALUES;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseStatisticsManager {

    /**
     * Obtiene los nombres de las series que se utilizan en el Chart
     * @param sql Select para obtener las series
     * @return Lista de series
     */
    public static List<UTL_INFOSERIES> listSeries(String sql) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        List qRes = query.list();
        List<UTL_INFOSERIES> result = new ArrayList<UTL_INFOSERIES>();
        for (int i = 0; i < qRes.size(); i++) {
            result.add(new UTL_INFOSERIES(i+1, (String) qRes.get(i)));
        }
        return result;
    }

    /**
     * Obtiene los nombres de las series que se utilizan en el Chart (con una select tipo: SELECT ID, NOMBRE)
     * @param sql Select para obtener las series
     * @return Lista de series
     */
    public static List<UTL_INFOSERIES> listSeriesWidthId(String sql) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        List qRes = query.list();
        List<UTL_INFOSERIES> result = new ArrayList<UTL_INFOSERIES>();
        for (int i = 0; i < qRes.size(); i++) {
            Object[] item = (Object[]) qRes.get(i);
            result.add(new UTL_INFOSERIES(i+1, ((Integer) item[0]).longValue(), (String) item[1]));
        }
        return result;
    }

    /**
     * Obtiene los nombres de las series que se utilizan una string como identificador
     * @param sql Select para obtener las series
     * @return Lista de series
     */
    public static List<UTL_INFOSERIES> listSeriesWidthStringId(String sql) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        List qRes = query.list();
        List<UTL_INFOSERIES> result = new ArrayList<UTL_INFOSERIES>();
        for (int i = 0; i < qRes.size(); i++) {
            Object[] item = (Object[]) qRes.get(i);
            result.add(new UTL_INFOSERIES(i+1, ((String) item[1]), (String) item[0]));
        }
        return result;
    }


    /**
     * Adiciona un valor a una serie anual
     * @param series Informacion de las series
     * @param values Valores
     * @param serie Nombre de la serie
     * @param mes Mes
     * @param valor Valor
     */
    protected static void addSerieValue(List<UTL_INFOSERIES> series, List<UTL_INFOSERIESVALUES> values, String serie, Integer mes, Long valor) {
        int indx = 0;
        for (int i = 0; i < series.size(); i++) {
            UTL_INFOSERIES infoseries = series.get(i);
            if (infoseries.getNombre().equals(serie)) {
                indx = i+1;
                break;
            }
        }
        for (UTL_INFOSERIESVALUES sValor : values) {
            if (sValor.getMes() == mes) {
                sValor.setValue(indx, valor);
                break;
            }
        }

    }

    /**
     * Adiciona un valor a una serie de dias
     * @param series Informacion de las series
     * @param values Valores
     * @param serie Nombre de la serie
     * @param fecha Dia
     * @param valor Valor
     */
    protected static void addSerieValueWidthId(List<UTL_INFOSERIES> series, List<UTL_INFOSERIESVALUES> values, String serie, Date fecha, Long valor, Long id) {
        int indx = 0;
        for (int i = 0; i < series.size(); i++) {
            UTL_INFOSERIES infoseries = series.get(i);
            if (infoseries.getNombre().equals(serie)) {
                indx = i+1;
                break;
            }
        }
        boolean updated = false;
        for (UTL_INFOSERIESVALUES sValor : values) {
            if (fecha.compareTo(sValor.getFecha()) == 0) {
                sValor.setValue(indx, valor);
                sValor.setId(indx,id);
                updated = true;
                break;
            }
        }
        if (!updated) {
            UTL_INFOSERIESVALUES sValor = new UTL_INFOSERIESVALUES(fecha, serie);
            sValor.setValue(indx, valor);
            sValor.setId(indx,id);
            values.add(sValor);
        }
    }

     /**
     * Adiciona un valor a una serie de dias con dimension
     * @param series Informacion de las series
     * @param values Valores
     * @param serie Nombre de la serie
     * @param dimension Dia
     * @param valor Valor
     */
    protected static void addSerieValueWidthDimension(List<UTL_INFOSERIES> series, List<UTL_INFOSERIESVALUES> values, String serie, String dimension, Long valor, Long id) {
        int indx = 0;
        for (int i = 0; i < series.size(); i++) {
            UTL_INFOSERIES infoseries = series.get(i);
            if (infoseries.getId_cadena().equals(dimension)) {
                indx = i+1;
                break;
            }
        }
        boolean updated = false;
        for (UTL_INFOSERIESVALUES sValor : values) {
            if (serie.compareTo(sValor.getDimension()) == 0) {
                sValor.setValue(indx, valor);
                sValor.setId(indx,id);
                sValor.setSent(indx,dimension);
                updated = true;
                break;
            }
        }
        if (!updated) {
            UTL_INFOSERIESVALUES sValor = new UTL_INFOSERIESVALUES(serie, dimension);
            sValor.setValue(indx, valor);
            sValor.setId(indx,id);
            sValor.setSent(indx,dimension);
            values.add(sValor);

        }
    }


    /**
     * Adiciona un valor a una serie de dias
     * @param series Informacion de las series
     * @param values Valores
     * @param serie Nombre de la serie
     * @param fecha Dia
     * @param valor Valor
     */
    protected static void addSerieValue(List<UTL_INFOSERIES> series, List<UTL_INFOSERIESVALUES> values, String serie, Date fecha, Long valor) {
        int indx = 0;
        for (int i = 0; i < series.size(); i++) {
            UTL_INFOSERIES infoseries = series.get(i);
            if (infoseries.getNombre().equals(serie)) {
                indx = i+1;
                break;
            }
        }
        boolean updated = false;
        for (UTL_INFOSERIESVALUES sValor : values) {
            if (fecha.compareTo(sValor.getFecha()) == 0) {
                sValor.setValue(indx, valor);
                updated = true;
                break;
            }
        }
        if (!updated) {
            UTL_INFOSERIESVALUES sValor = new UTL_INFOSERIESVALUES(fecha, serie);
            sValor.setValue(indx, valor);
            values.add(sValor);
        }
    }


}
