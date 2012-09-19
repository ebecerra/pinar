package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

@Entity
@Table(name = "APPBS_PARAMETROS_VALORES")
public class APPBS_PARAMETROS_VALORES extends EntityBean {

    @Id
    @Column(name="ID_APLICACION")
    private String id_aplicacion;

    @Id
    @Column(name="ID_PARAMETRO")
    private String id_parametro;

    @Id
    @Column(name="ID_VALOR")
    private Long id_valor;

    @Column(name = "NOMBRE")
    private String nombre;

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public String getId_parametro() {
        return id_parametro;
    }

    public void setId_parametro(String id_parametro) {
        this.id_parametro = id_parametro;
    }

    public Long getId_valor() {
        return id_valor;
    }

    public void setId_valor(Long id_valor) {
        this.id_valor = id_valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "APPBS_PARAMETROS_VALORES{" +
                "id_aplicacion='" + id_aplicacion + '\'' +
                ", id_parametro='" + id_parametro + '\'' +
                ", id_valor=" + id_valor +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] { "id_aplicacion", "id_parametro", "id_valor" };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista con todos los campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[] { "id_aplicacion", "id_parametro", "id_valor", "nombre" };
    }
}
