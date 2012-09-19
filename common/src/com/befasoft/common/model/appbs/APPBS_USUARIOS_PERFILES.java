package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "APPBS_USUARIOS_PERFILES")
@Proxy
public class APPBS_USUARIOS_PERFILES extends EntityBean {

    @Id
    @Column(name = "ID_APLICACION")
    private String id_aplicacion;

    @Id
    @Column(name = "ID_USUARIO")
    private Long id_usuario;

    @Id
    @Column(name = "ID_PERFIL")
    private Long id_perfil;

    @ManyToOne
    @JoinColumn(name = "id_perfil", referencedColumnName = "ID_PERFIL", insertable = false, updatable = false)
    private APPBS_PERFILES perfil;


    public APPBS_USUARIOS_PERFILES() {
    }

    public APPBS_USUARIOS_PERFILES(String id_aplicacion, Long id_usuario, Long id_perfil) {
        this.id_aplicacion = id_aplicacion;
        this.id_usuario = id_usuario;
        this.id_perfil = id_perfil;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public APPBS_PERFILES getPerfil() {
        return perfil;
    }

    public void setPerfil(APPBS_PERFILES perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "APPBS_USUARIOS_PERFILES{" +
                "id_aplicacion=" + id_aplicacion  +
                ", perfil=" + id_perfil +
                ", usuario=" + id_usuario  +
                '}';
    }
}
