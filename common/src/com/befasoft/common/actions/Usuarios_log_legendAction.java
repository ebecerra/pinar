package com.befasoft.common.actions;

import com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG_LEGEND;
import com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_LEGEND_MANAGER;
import com.befasoft.common.tools.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 02/11/2011
 */
public class Usuarios_log_legendAction extends BaseManagerAction {

    Long id_tipo;


    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_USUARIOS_LOG_LEGEND.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_USUARIOS_LOG_LEGEND_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_tipo))
            filter.put("id_tipo", id_tipo);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

}


