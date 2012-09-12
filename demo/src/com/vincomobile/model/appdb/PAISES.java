package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 15/04/2011
 */
@Entity
@Table(name = "PAISES")
public class PAISES extends EntityBean {

    @Id
    @Column(name = "ID_PAIS")
    private String id_pais;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ID_IDIOMA")
    private String id_idioma;


    /*
     * Set/Get Methods
     */
    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getId_idioma() {
		return id_idioma;
	}

	public void setId_idioma(String id_idioma) {
		this.id_idioma = id_idioma;
	}

	/**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "id_pais"
        };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista con todos los campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[]{
            "id_pais", "nombre", "id_idioma"
        };
    }

}

