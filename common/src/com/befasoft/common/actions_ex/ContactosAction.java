package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.model.appbs.APPEX_CONTACTOS;
import com.befasoft.common.model.manager.APPEX_CONTACTOS_MANAGER;
import com.befasoft.common.tools.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 04/11/2011
 */
public class ContactosAction extends BaseManagerAction {

    Long id_contacto;

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_CONTACTOS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_CONTACTOS_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_contacto))
            filter.put("id_contacto", id_contacto);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

}


