package com.befasoft.common.model;

/**
 * Created by IntelliJ IDEA.
 * User: javier
 * Date: 15-mar-2011
 * Time: 9:37:08
 * To change this template use File | Settings | File Templates.
 */
import java.io.Serializable;

/**
 * Interfas de la que heredan todas las clases persisntentes. Tiene un metodo para obtener la clave
 * y siempre es comparable
 *
 * @author <a href="mailto:tomas.couso@vincomobile.com">Tomas Couso Alvarez</a>
 */
public interface iBaseBean extends Serializable {

    /**
     * Retorna la clave primaria de esta instancia. En caso que la clave sea convinada retorna el tuplo de la
     * clave primaria.
     *
     * @return  Objeto simple o tupla de objetos que es llave primaria del objeto.
     */
    public Object getPrimaryKey();

    /**
     * Dice si la instancia es nueva o es recuperada de BD
     * @return  Indica si la entidad esta guardada en BD o no.
     */
    public boolean isNewEntity();

    /**
     * Metodo que nos indica si e bean tiene toda la informacion seteada para ser operativo
     * @return  Indica si el bean es valido para su uso
     */
    public boolean isIsValid();
}
