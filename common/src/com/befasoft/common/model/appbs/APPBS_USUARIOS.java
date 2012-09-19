package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Constants;
import com.googlecode.jsonplugin.annotations.JSON;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "APPBS_USUARIOS")
@Proxy
public class APPBS_USUARIOS extends EntityBean {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_USUARIO")
    private Long id_usuario;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "CLAVE")
    private String clave;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "MOVIL")
    private String movil;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FECHA_CREACION")
    private Date fecha_creacion;

    @Column(name = "FECHA_CONEXION")
    private Date fecha_conexion;

    @Column(name = "LOG_LEVEL")
    private Long log_level;

    @Column(name = "ACTIVO")
    private String activo;

    @Column(name = "COOKIE")
    private String cookie;

    @Column(name = "TIPO")
    private Integer tipo;

    @Column(name = "id_idioma")
    private Long id_idioma;

    @ManyToOne
    @JoinColumn(name="TIPO", insertable = false, updatable = false)
    private APPBS_USUARIOS_TIPOS usuario_tipo;

    @OneToMany(mappedBy = "id_usuario")
    private List<APPBS_USUARIOS_PERFILES> perfiles = new ArrayList<APPBS_USUARIOS_PERFILES>();

    @Column(name = "PARAM_1")
    private String param_1;

    @Column(name = "PARAM_2")
    private String param_2;

    @Column(name = "PARAM_3")
    private String param_3;

    @Column(name = "PARAM_4")
    private String param_4;

    @Column(name = "INT_PARAM_1")
    private Long int_param_1;

    @Column(name = "INT_PARAM_2")
    private Long int_param_2;

    @Column(name = "INT_PARAM_3")
    private Long int_param_3;

    @Column(name = "INT_PARAM_4")
    private Long int_param_4;

    @Transient
    private Long id_perfil;


    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public List<APPBS_USUARIOS_PERFILES> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<APPBS_USUARIOS_PERFILES> perfiles) {
        this.perfiles = perfiles;
    }

    public APPBS_USUARIOS_TIPOS getUsuario_tipo() {
        return usuario_tipo;
    }

    public void setUsuario_tipo(APPBS_USUARIOS_TIPOS usuario_tipo) {
        this.usuario_tipo = usuario_tipo;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return Constants.PASSWORD_CHECK == 0 ? clave : "";
    }

    @JSON(serialize = false)
    public String getClavereal() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_conexion() {
        return fecha_conexion;
    }

    public void setFecha_conexion(Date fecha_conexion) {
        this.fecha_conexion = fecha_conexion;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(Long id_idioma) {
        this.id_idioma = id_idioma;
    }

    public String getParam_1() {
        return param_1;
    }

    public void setParam_1(String param_1) {
        this.param_1 = param_1;
    }

    public String getParam_2() {
        return param_2;
    }

    public void setParam_2(String param_2) {
        this.param_2 = param_2;
    }

    public String getParam_3() {
        return param_3;
    }

    public void setParam_3(String param_3) {
        this.param_3 = param_3;
    }

    public String getParam_4() {
        return param_4;
    }

    public void setParam_4(String param_4) {
        this.param_4 = param_4;
    }

    public Long getInt_param_1() {
        return int_param_1;
    }

    public void setInt_param_1(Long int_param_1) {
        this.int_param_1 = int_param_1;
    }

    public Long getInt_param_2() {
        return int_param_2;
    }

    public void setInt_param_2(Long int_param_2) {
        this.int_param_2 = int_param_2;
    }

    public Long getInt_param_3() {
        return int_param_3;
    }

    public void setInt_param_3(Long int_param_3) {
        this.int_param_3 = int_param_3;
    }

    public Long getInt_param_4() {
        return int_param_4;
    }

    public void setInt_param_4(Long int_param_4) {
        this.int_param_4 = int_param_4;
    }

    public Long getId_perfil() {
        if (id_perfil == null && perfiles != null && perfiles.size() > 0) {
            return perfiles.get(0).getId_perfil();
        }
        return id_perfil;
    }

    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public Long getLog_level() {
        return log_level;
    }

    public void setLog_level(Long log_level) {
        this.log_level = log_level;
    }
    
    public String getTipo_nombre() {
        return usuario_tipo != null ? usuario_tipo.getNombre() : "";
    }

    @Override
    public String toString() {
        return "APPBS_USUARIOS{" +
                "id_usario='" + id_usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", login='" + login + '\'' +
                ", perfiles=" + perfiles  +
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
            "id_usuario"
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
            "clave", "fax", "login", "id_usuario", "fecha_creacion", "fecha_conexion", "activo", "nombre", "email", "tipo", "log_level", "cookie", "id_idioma",
            "telefono", "movil", "param_1",  "param_2", "param_3", "param_4", "int_param_1", "int_param_2", "int_param_3", "int_param_4"
        };
    }
    
    /**
     * Nombre de los campos que se van a utilizar en una exportación
     *
     * @return Lista de campos
     */
    @JSON(serialize = false)
    public String[] getExportFields() {
        return new String[] {
             "id_usuario", "nombre", "email", "telefono", "movil", "fax", "tipo", "login", "activo", "fecha_creacion", "fecha_conexion"
        };
    }
}
