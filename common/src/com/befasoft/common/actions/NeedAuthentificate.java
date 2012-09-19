package com.befasoft.common.actions;

/**
 * Esta interface debe ser implementada por todas las acciones que impliquen que el usuario esta autentificado.
 *
 */
public interface NeedAuthentificate {

    /**
     * Retorna si es necesario que el usuario este autentificado para realizar esta accion.
     *
     * @return  <code>true</code>  si es necesario estar autentificado
     */
    public boolean needAuthetificated();
}
