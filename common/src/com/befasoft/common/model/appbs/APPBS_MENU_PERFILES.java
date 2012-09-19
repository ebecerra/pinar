package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;

import javax.persistence.*;

@Entity
@Table(name = "APPBS_MENU_PERFILES")
public class APPBS_MENU_PERFILES extends EntityBean {

    @Id
    @Column(name = "ID_APLICACION")
    private String id_aplicacion;

    @Id
    @Column(name = "ID_MENU")
    private String id_menu;
    //@Column(name = "ID_MENU", insertable = false, updatable = false)

    @Id
    @Column(name = "ID_PERFIL", insertable = false, updatable = false)
    private long id_perfil;


/*
    @Id
    @ManyToOne
    @JoinColumns({
                         @JoinColumn(name = "ID_MENU", referencedColumnName = "ID_MENU") ,
                         @JoinColumn(name = "ID_APLICACION", referencedColumnName = "ID_APLICACION")
                 })
*/
    transient private APPBS_MENU menu;


//    @Id
//    @ManyToOne
//    @JoinColumns({
//                         @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID_PERFIL")
//                 })
//    transient private APPBS_PERFILES perfil;

    public long getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public APPBS_MENU getMenu() {
        return menu;
    }

    public void setMenu(APPBS_MENU menu) {
        this.menu = menu;
    }

//    public APPBS_PERFILES getPerfil() {
//        return perfil;
//    }
//
//    public void setPerfil(APPBS_PERFILES perfil) {
//        this.perfil = perfil;
//    }

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }


    @Override
    public String toString() {
        return "APPBS_MENU_PERFILES{" +
                "id_aplicacion='" + id_aplicacion + '\'' +
                ", id_menu='" + id_menu + '\'' +
                ", id_perfil='" + id_perfil + '\'' +
                '}';
    }
}
