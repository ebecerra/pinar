package com.befasoft.common.actions;

import com.befasoft.common.model.appbs.APPBS_LOOKUPS;
import com.befasoft.common.model.manager.APPBS_LOOKUPS_MANAGER;
import com.befasoft.common.tools.Converter;
import com.opensymphony.xwork2.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/10/2011
 */
public class LookupsAction extends BaseManagerAction {

    String id_lookup, activo;

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_LOOKUPS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_LOOKUPS_MANAGER.class;
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
        if (!Converter.isEmpty(activo))
            filter.put("activo", activo);
        return filter;
    }

    public String getValores() throws Exception {
        List result = APPBS_LOOKUPS_MANAGER.findByFilter(getFilter());
        if (result.size() > 0) {
            APPBS_LOOKUPS lookup = (APPBS_LOOKUPS) result.get(0);
            setReturn(lookup.getValores(), lookup.getValores().size(), -1, -1);
        }
        return Action.SUCCESS;
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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}

