package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.model.appbs.APPEX_LICENCIA_ITEMS;
import com.befasoft.common.model.manager.APPEX_LICENCIA_ITEMS_MANAGER;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
public class Licencia_itemsAction extends BaseManagerAction {

    Long id_licencia;
    String id_item;


    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_LICENCIA_ITEMS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_LICENCIA_ITEMS_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_licencia))
            filter.put("id_licencia", id_licencia);
        if (!Converter.isEmpty(id_item))
            filter.put("id_item", id_item);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

    public Long getId_licencia() {
        return id_licencia;
    }

    public void setId_licencia(Long id_licencia) {
        this.id_licencia = id_licencia;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }
}


