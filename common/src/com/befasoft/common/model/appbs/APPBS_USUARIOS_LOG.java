package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Devtools.
 * Date: 31/10/2011
 */
@Entity
@Table(name = "APPBS_USUARIOS_LOG")
public class APPBS_USUARIOS_LOG extends EntityBean {

    public static final long LEVEL_ERROR        = 1;
    public static final long LEVEL_WARNING      = 2;
    public static final long LEVEL_INFO         = 3;
    public static final long LEVEL_COMMON_INFO  = 4;
    public static final long LEVEL_TRACE        = 5;

    @Id
    @Column(name = "ID_TIPO")
    private Long id_tipo;

    @Id
    @Column(name = "ID_APLICACION")
    private String id_aplicacion;

    @Id
    @Column(name = "ID_USUARIO")
    private Long id_usuario;

    @Id
    @Column(name = "FECHA")
    private Timestamp fecha;

  
    @Column(name = "TEXTO4")
    private String texto4;

    @Column(name = "TEXTO2")
    private String texto2;

    @Column(name = "NIVEL")
    private Long nivel;

    @Column(name = "TEXTO1")
    private String texto1;

    @Column(name = "SESION_ID")
    private String sesion_id;

    @Column(name = "TEXTO5")
    private String texto5;

    @Column(name = "TEXTO3")
    private String texto3;

    @Transient
    private String usuario;

    @Transient
    private String descripcion;

    /*
     * Set/Get Methods
     */
    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getTexto1() {
        return texto1;
    }

    public void setTexto1(String texto1) {
        this.texto1 = texto1;
    }

    public String getTexto2() {
        return texto2;
    }

    public void setTexto2(String texto2) {
        this.texto2 = texto2;
    }

    public String getTexto3() {
        return texto3;
    }

    public void setTexto3(String texto3) {
        this.texto3 = texto3;
    }

    public String getTexto4() {
        return texto4;
    }

    public void setTexto4(String texto4) {
        this.texto4 = texto4;
    }

    public String getTexto5() {
        return texto5;
    }

    public void setTexto5(String texto5) {
        this.texto5 = texto5;
    }
    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }
    
    public String getSesion_id() {
        return sesion_id;
    }

    public void setSesion_id(String sesion_id) {
        this.sesion_id = sesion_id;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = new Timestamp(fecha.getTime());
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_tipo", "id_aplicacion", "id_usuario", "fecha"
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
            "texto4", "texto2", "nivel", "id_tipo", "texto1", "id_aplicacion", "sesion_id", "id_usuario", "fecha", "texto5", "texto3"
        };
    }

}

