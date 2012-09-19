package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.model.appbs.APPEX_CLIENTES;
import com.befasoft.common.model.manager.APPEX_CLIENTES_MANAGER;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
public class ClientesAction extends BaseManagerAction {

    Long id_cliente;


    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_CLIENTES.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_CLIENTES_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_cliente))
            filter.put("id_cliente", id_cliente);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }
}


