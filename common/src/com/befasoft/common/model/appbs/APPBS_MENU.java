package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 18/03/2011
 */
@Entity
@Table(name = "APPBS_MENU")
public class APPBS_MENU extends EntityBean {

    @Id
    @Column(name = "ID_MENU")
    private String id_menu;

    @Id
    @Column(name = "ID_APLICACION")
    private String id_aplicacion;


    @Column(name = "ATRIBUTO9")
    private String atributo9;

    @Column(name = "ATRIBUTO7")
    private String atributo7;

    @Column(name = "ATRIBUTO4")
    private String atributo4;

    @Column(name = "TIPO_ENLACE")
    private String tipo_enlace;

    @Column(name = "ENLACE")
    private String enlace;

    @Column(name = "ATRIBUTO2")
    private String atributo2;

    @Column(name = "ATRIBUTO10")
    private String atributo10;

    @Column(name = "ATRIBUTO1")
    private String atributo1;

    @Column(name = "ORDEN")
    private Long orden;

    @Column(name = "ATRIBUTO5")
    private String atributo5;

    @Column(name = "ID_PADRE")
    private String id_padre;

    @Column(name = "ATRIBUTO8")
    private String atributo8;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ATRIBUTO3")
    private String atributo3;

    @Column(name = "ATRIBUTO6")
    private String atributo6;

//    @OneToMany
//    @JoinColumns({
//                         @JoinColumn(name = "id_aplicacion"),
//                         @JoinColumn(name = "id_padre")
//                 })
//    @OrderColumn(name = "orden")
    transient List<APPBS_MENU> subMenus = new ArrayList<APPBS_MENU>();

    /*
     * Set/Get Methods
     */

    public String getAtributo9() {
        return atributo9;
    }

    public void setAtributo9(String atributo9) {
        this.atributo9 = atributo9;
    }

    public String getAtributo7() {
        return atributo7;
    }

    public void setAtributo7(String atributo7) {
        this.atributo7 = atributo7;
    }

    public String getAtributo4() {
        return atributo4;
    }

    public void setAtributo4(String atributo4) {
        this.atributo4 = atributo4;
    }

    public String getTipo_enlace() {
        return tipo_enlace;
    }

    public void setTipo_enlace(String tipo_enlace) {
        this.tipo_enlace = tipo_enlace;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getAtributo2() {
        return atributo2;
    }

    public void setAtributo2(String atributo2) {
        this.atributo2 = atributo2;
    }

    public String getAtributo10() {
        return atributo10;
    }

    public void setAtributo10(String atributo10) {
        this.atributo10 = atributo10;
    }

    public String getAtributo1() {
        return atributo1;
    }

    public void setAtributo1(String atributo1) {
        this.atributo1 = atributo1;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public String getAtributo5() {
        return atributo5;
    }

    public void setAtributo5(String atributo5) {
        this.atributo5 = atributo5;
    }

    public String getId_padre() {
        return id_padre;
    }

    public void setId_padre(String id_padre) {
        this.id_padre = id_padre;
    }

    public String getAtributo8() {
        return atributo8;
    }

    public void setAtributo8(String atributo8) {
        this.atributo8 = atributo8;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAtributo3() {
        return atributo3;
    }

    public void setAtributo3(String atributo3) {
        this.atributo3 = atributo3;
    }

    public String getAtributo6() {
        return atributo6;
    }

    public void setAtributo6(String atributo6) {
        this.atributo6 = atributo6;
    }

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public List<APPBS_MENU> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<APPBS_MENU> subMenus) {
        this.subMenus = subMenus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("APPBS_MENU");
        sb.append("{id_aplicacion='").append(id_aplicacion).append('\'');
        sb.append(", id_menu='").append(id_menu).append('\'');
        sb.append(", id_padre='").append(id_padre).append('\'');
        sb.append(", subMenus=").append(subMenus);
        sb.append('}');
        return sb.toString();
    }
}




