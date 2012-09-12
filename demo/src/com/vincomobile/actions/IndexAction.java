package com.vincomobile.actions;

import com.befasoft.common.actions.BaseAction;
import com.befasoft.common.beans.LOGIN_INFO;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.vincomobile.model.business.USER_DEMO;
import org.apache.commons.logging.LogFactory;

import java.util.Locale;

public class IndexAction extends BaseAction {

    // Password recover
    String email, fecha, hash, clave, nombre;
    Long id_usuario;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    public String execute() throws Exception {
        log = LogFactory.getLog(IndexAction.class);
        super.execute();
        Locale loc = new Locale("es");
        ActionContext.getContext().setLocale(loc);
        super.execute();
        if ("reset".equals(action)) {
            show = "reset";
            APPBS_USUARIOS usu = resetPassword(email, hash, fecha);
            if (usu != null) {
                id_usuario = usu.getId_usuario();
                nombre = usu.getNombre();
            }
            return Action.SUCCESS;
        }
        if (user == null) {
            USER_DEMO login_info = (USER_DEMO) checkLoginCookie();
            if (login_info != null) {
                session.put("user", login_info);
            }
        }
        return Action.SUCCESS;

    }

    /**
     * Retorna la información de usario para almacenar en la sesion
     * @param user Informacion del usario (tabla APPBS_USUARIOS)
     * @return Informacion del usuario
     */
    @Override
    public LOGIN_INFO getLoginInfo(APPBS_USUARIOS user) {
        return new USER_DEMO(user);
    }


    /*
     * Metodos Get/Set
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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
}