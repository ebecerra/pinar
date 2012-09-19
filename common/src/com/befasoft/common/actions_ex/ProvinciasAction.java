package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.model.appbs.APPEX_PROVINCIAS;
import com.befasoft.common.model.manager.APPEX_PROVINCIAS_MANAGER;
import com.befasoft.common.tools.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/11/2011
 */
public class ProvinciasAction extends BaseManagerAction {

    Long id_pais;
    Long id_provincia;


    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_PROVINCIAS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_PROVINCIAS_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_pais))
            filter.put("id_pais", id_pais);
        if (!Converter.isEmpty(id_provincia))
            filter.put("id_provincia", id_provincia);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

}


