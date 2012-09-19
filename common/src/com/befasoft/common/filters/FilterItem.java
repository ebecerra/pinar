package com.befasoft.common.filters;

import java.util.Date;

/**
 * Date: 21-may-2005
 * Time: 18:59:15
 *
 * Almacena una condicion de filtrado
 */
public class FilterItem extends FilterInfo {

    /**
     * Crea un item con la condicion (AND, OR, ...)
     * @param cond Condicion
     */
    public FilterItem(int cond) {
        this.empty = false;
        field = "";
        this.cond = cond;

    }

    /**
     * Crea un item con una cadena
     * @param field Nombre del campo
     * @param cond Condicion
     * @param value Valor
     */
    public FilterItem(String field, int cond, Object value) {
        fillItem(field, cond, value);
    }

    /**
     * Crea un item con una cadena
     * @param field Nombre del campo
     * @param cond Condicion
     * @param value Valor
     * @param notNull Solo hace la validacion si el valor no es nulo o vacio
     */
    public FilterItem(String field, int cond, String value, boolean notNull) {
        if (notNull) {
            empty = value == null || value.trim().equals("");
            if (empty)
                return;
        }
        fillItem(field, cond, value);
    }

    private void fillItem(String field, int cond, Object value) {
        this.empty = false;
        this.field = field;
        this.keymap = getFieldkey();
        this.cond = cond;
        if (cond == FLT_IN || cond == FLT_NOTIN)
            this.value = value;
        else
            this.value = value;
    }

    /**
     * Crea un item con un entero
     * @param field Nombre del campo
     * @param cond Condicion
     * @param value Valor
     * @param notNull Solo hace la validacion si el valor no es nulo o vacio
     */
    public FilterItem(String field, int cond, int value, boolean notNull) {
        if (notNull) {
            empty = value == 0;
            if (empty)
                return;
        }
        fillItem(field, cond, value);
    }

    public FilterItem(String field, int cond, long value, boolean notNull) {
        if (notNull) {
            empty = value == 0;
            if (empty)
                return;
        }
        fillItem(field, cond, value);
    }

    /**
     * Crea un item con una numero real
     * @param field Nombre del campo
     * @param cond Condicion
     * @param value Valor
     * @param notNull Solo hace la validacion si el valor no es nulo o vacio
     */
    public FilterItem(String field, int cond, double value, boolean notNull) {
        if (notNull) {
            empty = value == 0;
            if (empty)
                return;
        }
        fillItem(field, cond, value);
    }

    /**
     * Crea un item con una fecha
     * @param field Nombre del campo
     * @param cond Condicion
     * @param value Valor
     * @param notNull Solo hace la validacion si el valor no es nulo o vacio
     */
    public FilterItem(String field, int cond, Date value, boolean notNull) {
        if (notNull) {
            empty = value == null;
            if (empty)
                return;
        }
        fillItem(field, cond, value);
    }

    public String getSQL(int index) {
        if ("".equals(field))
            switch (cond) {
                case FLT_AND: return " and ";
                case FLT_OR: return " or ";
                case FLT_OPEN: return "(";
                case FLT_CLOSE: return ")";
            default:
                return " ";
            }
        else
            if (cond == FLT_NULL || cond == FLT_NOTNULL)
                return field + getCondExpr();
            else
                return field + getCondExpr() + ":" + keymap + "_" + index;
    }

    /**
     * Indica si el filtro tiene parametros
     * @return Verdadero si se tiene que utilizar el campo value
     */
    public boolean isParameters() {
        return !"".equals(field) && cond != FLT_NULL && cond != FLT_NOTNULL;
    }
}
