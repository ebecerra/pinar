package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.model.appbs.APPEX_IVA;
import com.befasoft.common.model.manager.APPEX_IVA_MANAGER;

/**
 * Created by Devtools.
 * Date: 04/11/2011
 */
public class IvaAction extends BaseManagerAction {

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_IVA.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_IVA_MANAGER.class;
    }

    /*
     * Metodos Get/Set
     */

}


