package com.befasoft.common.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 21-may-2005
 * Time: 19:02:10
 *
 * Almacena un listado de condiciones de filtrado
 */
public class FilterAdvanced {
    List<FilterInfo> items;
    String addCond, extraCond;
    boolean isAnd;

    public FilterAdvanced() {
        items = new ArrayList<FilterInfo>();
        addCond = "";
        extraCond = "";
    }

    /**
     * Adiciona un campo al filtro
     * @param item Informacion del campo
     */
    public void addItem(FilterInfo item) {
        addItem(item, false);
    }

    /**
     * Adiciona un campo al filtro (adicionando una condicion AND si existen mas campos)
     * @param item Informacion del campo
     * @param addAnd Indica si se adiciona el AND o no
     */
    public void addItem(FilterInfo item, boolean addAnd) {
        if (!item.isEmpty()) {
            if (!isEmpty() && addAnd)
                items.add(new FilterItem(FilterInfo.FLT_AND));
            items.add(item);
        }
    }

    /**
     * Adiciona una condicion al filtro
     * @param addCond Condicion
     */
    public void additionalCondition(String addCond) {
        additionalCondition(addCond, true);
    }

    public void additionalCondition(String addCond, boolean isAnd) {
        this.isAnd = isAnd;
        if ((null != this.addCond) && (this.addCond.length() > 0)) {
            this.addCond = this.addCond + " AND " + addCond;
        } else {
            this.addCond = addCond;
        }
    }

    public void setAdditionalCondition(String addCond) {
        this.isAnd = true;
        this.addCond = addCond;
    }

    public void setExtraCond(String extraCond) {
        this.extraCond = extraCond;
    }

    /**
     * Limpia el filtro
     */
    public void clear() {
        items.clear();
        addCond = "";
        extraCond = "";
    }

    /**
     * Verifica si el filtro esta vacio
     * @return Verdadero si el filtro tiene alguna condicion
     */
    public boolean isEmpty() {
        return items.size() == 0 && "".equals(addCond) && "".equals(extraCond);
    }

    /**
     * Verifica si un campo esta en el filtro
     * @param fName Nombre del campo
     * @return Verdadero si el campo esta en el filtro
     */
    public boolean existField(String fName) {
        for (FilterInfo filterItem : items) {
            if (filterItem.getField().equals(fName))
                return true;
        }
        return false;
    }

    public String getAddCond() {
        return this.addCond;
    }

    public boolean getIsAnd() {
        return this.isAnd;
    }

    public List<FilterInfo> getItems() {
        return items;
    }

    public void setItems(List<FilterInfo> items) {
        this.items = items;
    }
}
