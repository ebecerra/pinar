package com.vincomobile.actions;

import com.befasoft.common.beans.LOGIN_INFO;
import com.befasoft.common.business.DBLogger;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG;
import com.befasoft.common.model.appbs.APPEX_IDIOMAS;
import com.befasoft.common.model.manager.APPBS_PARAMETROS_MANAGER;
import com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_MANAGER;
import com.befasoft.common.model.manager.APPEX_IDIOMAS_MANAGER;
import com.befasoft.common.tools.Converter;
import com.opensymphony.xwork2.Action;
import com.vincomobile.Constants;
import com.vincomobile.model.appdb.CLIENTES;
import com.vincomobile.model.appdb.MOBILE_VERSION;
import com.vincomobile.model.appdb.MOBILE_VERSION_CLIENTES;
import com.vincomobile.model.appdb.VENDEDORES;
import com.vincomobile.model.business.MOBILE_VERSION_INFO;
import com.vincomobile.model.business.USER_DEMO;
import com.vincomobile.model.manager.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginAction extends DemoAction {

    String usuario, clave, origen;
    String cif, email;
    Long id_cliente, mobile_id, version;
    boolean remember;
    Long id_usuario;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    public String execute() throws Exception {

        super.execute();

        if (Converter.isEmpty(action))
            action = "login";
        if ("login".equals(action)) {
            show = "submit";
            user = validateUser();
            if (user == null) {
                actionResponse.setSuccess(false);
                session.remove("user");
            } else {
                actionResponse.setSuccess(true);
                session.put("user", user);
                writeUserLog(Constants.LOG_LOGIN_OK, APPBS_USUARIOS_LOG.LEVEL_INFO, Long.toString(user.getId_usuario()), user.getNombre(), user.getPerfil_tipo());
            }
            if ("mobile".equals(op)) {
                if (user != null) {
                    if (!"SALES".equals(user.getPerfil_tipo())) {
                        actionResponse.setSuccess(false);
                        session.remove("user");
                    } else {
                        // Informacion de Id. para registros nuevos
                        actionResponse.put("id_vendedor", ((USER_DEMO) user).getId_vendedor());
                        actionResponse.put("pedido_num_next", PEDIDOS_MANAGER.getNextMobilePedidoNum(mobile_id, ((USER_DEMO) user).getId_vendedor()));
                        actionResponse.put("id_cliente_next", CLIENTES_MANAGER.getNextMobileId_cliente(mobile_id, ((USER_DEMO) user).getId_vendedor()));
                        actionResponse.put("id_direccion_next", CLIENTES_DIRECCIONES_MANAGER.getNextMobileId_direccion(mobile_id, ((USER_DEMO) user).getId_vendedor()));
                        actionResponse.put("id_contacto_next", CLIENTES_CONTACTOS_MANAGER.getNextMobileId_contacto(mobile_id, ((USER_DEMO) user).getId_vendedor()));
                        actionResponse.put("db_version", APPBS_PARAMETROS_MANAGER.getLongParameter("APPBS_SYNC_DB_VERSION", 1));
                        // Control de versiones
                        if (!Converter.isEmpty(id_idioma)) {
                            MOBILE_VERSION mVersion = MOBILE_VERSION_MANAGER.getLastVersion(id_idioma);
                            if (mVersion != null) {
                                if (mVersion.getVersion() > version) {
                                    MOBILE_VERSION_INFO vInfo = new MOBILE_VERSION_INFO(mVersion);
                                    MOBILE_VERSION cVersion = MOBILE_VERSION_MANAGER.findByKey(version, id_idioma);
                                    if (cVersion != null)
                                        vInfo.setSincro(cVersion.getSincro());
                                    actionResponse.put("version", vInfo);
                                }
                            }
                            MOBILE_VERSION_CLIENTES version_cliente = MOBILE_VERSION_CLIENTES_MANAGER.findByKey(usuario, mobile_id, version);
                            if (version_cliente == null) {
                                version_cliente = new MOBILE_VERSION_CLIENTES();
                                version_cliente.setUsuario(usuario);
                                version_cliente.setMobile_id(mobile_id);
                                version_cliente.setVersion(version);
                            }
                            version_cliente.setFecha(new Date());
                            version_cliente.setId_idioma(id_idioma);
                            MOBILE_VERSION_CLIENTES_MANAGER.save(version_cliente);
                        }
                    }
                }
                return "mobile";
            }
        } else if ("recover".equals(action)) {
            recoverPassword(com.befasoft.common.tools.Constants.realAppPath + "WEB-INF/templates/passwd_recover.html", email);
        } else if ("reset".equals(action)) {
            changePassword(id_usuario, clave);
        } else if ("logout".equals(action)) {
            removeLoginCookie();
            writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_USER_LOGOUT, APPBS_USUARIOS_LOG.LEVEL_INFO);
            session.remove("user");
            if (!"mobile".equals(op))
                return "index";
        }

        return Action.SUCCESS;
    }

    /**
     * Valida el usuario
     * @return Informacion del usuario
     * @throws Exception Error en la DB
     */
    private LOGIN_INFO validateUser() throws Exception {
        USER_DEMO login_info = (USER_DEMO) loginUser(usuario, clave, remember);
        if (login_info != null) {
            Map flt = new HashMap();
            if ("CLIENTS".equals(login_info.getPerfil_tipo())) {
                // Valida el cliente
                flt.put("id_cliente", login_info.getInt_param_1());
                CLIENTES cliente = CLIENTES_MANAGER.findFirstByFilter(flt);
                if (cliente != null) {
                    if (!CLIENTES_MANAGER.setUserInfo(cliente, login_info)) {
                        login_info = null;
                        actionResponse.setMensaje(getText("login.err.client_prec"));
                    }
                } else {
                    login_info = null;
                    actionResponse.setMensaje(getText("login.err.client_notfound"));
                }
            } else if ("SALES".equals(login_info.getPerfil_tipo())) {
                flt.put("id_vendedor", login_info.getInt_param_1());
                VENDEDORES vendedor = VENDEDORES_MANAGER.findFirstByFilter(flt);
                if (vendedor != null) {
                    login_info.setCliente_select(false);
                    login_info.setId_vendedor(login_info.getInt_param_1());
                } else {
                    login_info = null;
                    actionResponse.setMensaje(getText("login.err.vendedor_notfound"));
                }
            }
            // Carga el ultimo idioma
            if ((login_info != null) && (login_info.getIdioma() != null) && !login_info.getIdioma().equals(id_idioma)) {
                APPEX_IDIOMAS idioma = APPEX_IDIOMAS_MANAGER.findByKey(login_info.getIdioma());
                if (idioma != null)
                    setIdioma(idioma, false);
            }
        } else {
            actionResponse.setMensaje(getText("appbs.login.err.invalid"));
            DBLogger.log(DBLogger.LEVEL_ERROR, "LOGIN_INVALID", "Usuario incorrecto: "+usuario+"/"+clave);
        }
        return login_info;
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

    @Override
    protected Class getBeanClass() {
        return null;
    }

    @Override
    protected Class getManagerClass() {
        return null;
    }

    /*
     * Metodos Get/Set
     */

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Long getMobile_id() {
        return mobile_id;
    }

    public void setMobile_id(Long mobile_id) {
        this.mobile_id = mobile_id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
}
