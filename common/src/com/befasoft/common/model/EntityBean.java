package com.befasoft.common.model;

import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.Transient;

/**
 * Clase comun de todas las entidades que tienen como primary key un long autogenerado
 *
 * @author <a href="mailto:tomas.couso@vincomobile.com">Tomas Couso Alvarez</a>
 */
public abstract class EntityBean implements iBaseBean {

    @Transient
    protected boolean save = false;

    /**
     * Retorna la clave primaria de esta instancia. En caso que la clave sea convinada retorna el tuplo de la
     * clave primaria.
     *
     * @return Identificador que es llave primaria de la entidad
     */
    @JSON(serialize = false)
    public Object getPrimaryKey() {
        return null;
    }

    /**
     * Dice si la instancia es nueva o es recuperada de BD
     *
     * @return <code>true</code> si no ha sido creada la instanacia en BD y por tanto no tiene clave primaria asignada
     */
    @JSON(serialize = false)
    public boolean isNewEntity() {
        return getPrimaryKey() == null;
    }

    /**
     * Metodo que nos indica si e bean tiene toda la informacion seteada para ser operativo
     *
     * @return Indica si el bean es valido para su uso
     */
    @JSON(serialize = false)
    public boolean isIsValid() {
        return true;
    }

    /**
     * Nombre de los campos que se van a exponer en la serializacion de JSON
     *
     * @return Lista de campos
     */
    @JSON(serialize = false)
    public String[] getJSONFields() {
        return new String[]{
                "id"
        };
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
                "id"
        };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista de campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[]{
                "id"
        };
    }

    /**
     * Nombre de los campos que se van a utilizar en una exportación
     *
     * @return Lista de campos
     */
    @JSON(serialize = false)
    public String[] getExportFields() {
        return null;
    }

    /**
     * Columnas con formulas para la exportacion a Excel
     *
     * @return Lista de formulas
     */
    @JSON(serialize = false)
    public String[] getFormulas() {
        return null;
    }

    /**
     * Columnas con formulas para la exportacion a Excel
     *
     * @return Lista de captions para las formulas
     */
    @JSON(serialize = false)
    public String[] getFormulaCaptions() {
        return null;
    }

    /**
     * Ancho de las columnas para la exportacion a Excel
     *
     * @return Lista de captions para las formulas
     */
    @JSON(serialize = false)
    public int[] getColWidth() {
        return null;
    }

    @JSON(serialize = false)
    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
}