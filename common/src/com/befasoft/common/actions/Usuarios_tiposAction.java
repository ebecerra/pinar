package com.befasoft.common.actions;

import com.befasoft.common.model.appbs.APPBS_USUARIOS_TIPOS;
import com.befasoft.common.model.manager.APPBS_USUARIOS_TIPOS_MANAGER;


/**
 * Created by Devtools.
 * Date: 06/05/2011
 */
public class Usuarios_tiposAction extends BaseManagerAction {

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_USUARIOS_TIPOS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_USUARIOS_TIPOS_MANAGER.class;
    }

   /*
    * Metodos Get/Set
    */

}
