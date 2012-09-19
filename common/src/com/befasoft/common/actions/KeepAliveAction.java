package com.befasoft.common.actions;

import com.opensymphony.xwork2.Action;

public class KeepAliveAction extends BaseManagerAction {

    /**
     * Retorna si es necesario que el usuario este autentificado para realizar esta accion.
     *
     * @return <code>true</code>  si es necesario estar autentificado
     */
    @Override
    public boolean needAuthetificated() {
        return false;
    }

    /**
     * Retorna la clase asociada al bean..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return null;
    }

    /**
     * Retorna la clase asociada al manager del bean..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return null;
    }

    /*
     * Metodos Set/Get
     */
}
