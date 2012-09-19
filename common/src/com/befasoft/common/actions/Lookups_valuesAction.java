package com.befasoft.common.actions;

import com.befasoft.common.model.appbs.APPBS_LOOKUPS_VALUES;
import com.befasoft.common.model.manager.APPBS_LOOKUPS_VALUES_MANAGER;
import com.befasoft.common.tools.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/10/2011
 */
public class Lookups_valuesAction extends BaseManagerAction {

    String id_lookup;

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_LOOKUPS_VALUES.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_LOOKUPS_VALUES_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_lookup))
            filter.put("id_lookup", id_lookup);
        return filter;
    }

    /*
     * Metodos Get/Set
     */

    public String getId_lookup() {
        return id_lookup;
    }

    public void setId_lookup(String id_lookup) {
        this.id_lookup = id_lookup;
    }
}


