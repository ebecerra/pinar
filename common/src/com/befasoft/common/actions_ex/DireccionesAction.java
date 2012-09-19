package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.model.appbs.APPEX_DIRECCIONES;
import com.befasoft.common.model.manager.APPEX_DIRECCIONES_MANAGER;

/**
 * Created by Devtools.
 * Date: 11/04/2011
 */
public class DireccionesAction extends BaseManagerAction {

    /**
     * Retorna la clase asociada al bean..
     *
     * @return
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_DIRECCIONES.class;
    }

    /**
     * Retorna la clase asociada al manager del bean..
     *
     * @return
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_DIRECCIONES_MANAGER.class;
    }
}