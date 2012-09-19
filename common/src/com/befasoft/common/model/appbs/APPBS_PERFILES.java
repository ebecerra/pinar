package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APPBS_PERFILES")
@Proxy
public class APPBS_PERFILES extends EntityBean {

    @Id
    @Column(name = "ID_PERFIL")
    private Long id_perfil;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TIPO")
    private String tipo;


    //@OneToMany(mappedBy = "id_perfil")
    transient private List<APPBS_MENU_PERFILES> menus = new ArrayList<APPBS_MENU_PERFILES>();

    public List<APPBS_MENU_PERFILES> getMenus() {
        return menus;
    }

    public void setMenus(List<APPBS_MENU_PERFILES> menus) {
        this.menus = menus;
    }

    public Long getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "APPBS_PERFILES{" +
                "id_perfil=" + id_perfil +
                ", nombre='" + nombre + '\'' +
                ", menus=" + menus +
                '}';
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
                "id_perfil"
        };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista con todos los campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[] {
                "tipo", "nombre", "id_perfil"
        };
    }
}
