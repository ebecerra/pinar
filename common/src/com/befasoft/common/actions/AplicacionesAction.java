package com.befasoft.common.actions;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.model.appbs.APPBS_APLICACIONES;
import com.befasoft.common.model.manager.APPBS_APLICACIONES_MANAGER;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 31/05/2012
 */
public class AplicacionesAction extends BaseManagerAction {

    String id_aplicacion;


    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_APLICACIONES.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_APLICACIONES_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_aplicacion))
            filter.put("id_aplicacion", id_aplicacion);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }
}



