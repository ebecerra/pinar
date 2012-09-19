package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.model.appbs.APPEX_DISTRIBUIDORES;
import com.befasoft.common.model.manager.APPEX_DISTRIBUIDORES_MANAGER;
import com.befasoft.common.tools.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 05/06/2012
 */
public class DistribuidoresAction extends BaseManagerAction {

    Long id_distribuidor;


    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_DISTRIBUIDORES.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_DISTRIBUIDORES_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_distribuidor))
            filter.put("id_distribuidor", id_distribuidor);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

}


