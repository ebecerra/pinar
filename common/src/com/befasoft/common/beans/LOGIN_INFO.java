package com.befasoft.common.beans;

import com.befasoft.common.model.appbs.APPBS_MENU;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG;

import java.util.Date;
import java.util.List;

public class LOGIN_INFO {
    long id_usuario, id_perfil, log_level, id_idioma;
    int tipo;
    String nombre, email, perfil_tipo, login;
    String param_1, param_2, param_3, param_4;
    long int_param_1, int_param_2, int_param_3, int_param_4;
    Date fecha_conexion;
    List<APPBS_MENU> menu;
    boolean mobile;
    String user_agent;

    public LOGIN_INFO(APPBS_USUARIOS user) {
        this.id_usuario = user.getId_usuario();
        this.tipo = user.getTipo();
        this.log_level = user.getLog_level() == null ? APPBS_USUARIOS_LOG.LEVEL_INFO : user.getLog_level();
        this.nombre = user.getNombre();
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.fecha_conexion = user.getFecha_conexion();
        this.id_idioma = getLongValue(user.getId_idioma());
        this.param_1 = user.getParam_1();
        this.param_2 = user.getParam_2();
        this.param_3 = user.getParam_3();
        this.param_4 = user.getParam_4();
        this.int_param_1 = getLongValue(user.getInt_param_1());
        this.int_param_2 = getLongValue(user.getInt_param_2());
        this.int_param_3 = getLongValue(user.getInt_param_3());
        this.int_param_4 = getLongValue(user.getInt_param_4());
    }

    private long getLongValue(Long val) {
        return val == null ? 0 : val;
    }

    private String getStringValue(String val) {
        return val == null ? "" : val;
    }

    /*
    * Metodos Get/Set
    */
    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public long getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPerfil_tipo() {
        return perfil_tipo;
    }

    public void setPerfil_tipo(String perfil_tipo) {
        this.perfil_tipo = perfil_tipo;
    }

    public long getLog_level() {
        return log_level;
    }

    public void setLog_level(long log_level) {
        this.log_level = log_level;
    }

    public long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(long id_idioma) {
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

    public long getInt_param_1() {
        return int_param_1;
    }

    public void setInt_param_1(long int_param_1) {
        this.int_param_1 = int_param_1;
    }

    public long getInt_param_2() {
        return int_param_2;
    }

    public void setInt_param_2(long int_param_2) {
        this.int_param_2 = int_param_2;
    }

    public long getInt_param_3() {
        return int_param_3;
    }

    public void setInt_param_3(long int_param_3) {
        this.int_param_3 = int_param_3;
    }

    public long getInt_param_4() {
        return int_param_4;
    }

    public void setInt_param_4(long int_param_4) {
        this.int_param_4 = int_param_4;
    }

    public List<APPBS_MENU> getMenu() {
        return menu;
    }

    public void setMenu(List<APPBS_MENU> menu) {
        this.menu = menu;
    }

    public Date getFecha_conexion() {
        return fecha_conexion;
    }

    public void setFecha_conexion(Date fecha_conexion) {
        this.fecha_conexion = fecha_conexion;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }
}
