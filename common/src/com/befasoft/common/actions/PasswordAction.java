package com.befasoft.common.actions;

import com.befasoft.common.beans.UTL_PASSWORD;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_HIST_CLAVES;
import com.befasoft.common.model.manager.APPBS_PARAMETROS_MANAGER;
import com.befasoft.common.model.manager.APPBS_USUARIOS_HIST_CLAVES_MANAGER;
import com.befasoft.common.model.manager.APPBS_USUARIOS_MANAGER;
import com.befasoft.common.tools.AlgorithmSHA1;
import com.opensymphony.xwork2.Action;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordAction extends BaseAction {

    String login, pw_old, pw_new;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    public String execute() throws Exception {
        log = LogFactory.getLog(PasswordAction.class);
        super.execute();

        if ("params".equals(action)) {
            loadParams();
        } else if ("submit".equals(action)) {
            changePassword();    
        }

        return Action.SUCCESS;
    }

    /**
     * Carga los parametros de configuracion del nivel de seguridad de las claves
     * @throws Exception Error en la DB
     */
    private void loadParams() throws Exception {
        log.debug("loadParams()");
        UTL_PASSWORD info = new UTL_PASSWORD();
        info.setCheck(APPBS_PARAMETROS_MANAGER.getIntParameter("PASSWORD_CHECK"));
        info.setMax(APPBS_PARAMETROS_MANAGER.getIntParameter("PASSWORD_MAX"));
        info.setMin(APPBS_PARAMETROS_MANAGER.getIntParameter("PASSWORD_MIN"));
        info.setStore(APPBS_PARAMETROS_MANAGER.getIntParameter("PASSWORD_STORE"));
        info.setValid(APPBS_PARAMETROS_MANAGER.getIntParameter("PASSWORD_VALID"));
        info.setUser(APPBS_PARAMETROS_MANAGER.getBooleanParameter("PASSWORD_USER"));
        info.setErr_min(getText("appbs_password.err.min", new String[] {Integer.toString(info.getMin())}));
        info.setErr_max(getText("appbs_password.err.max", new String[] {Integer.toString(info.getMax())}));
        info.setErr_check2(getText("appbs_password.err.check2"));
        info.setErr_check3(getText("appbs_password.err.check3"));
        info.setErr_check4(getText("appbs_password.err.check4"));
        info.setErr_confirm(getText("appbs_password.err.confirm"));
        info.setLogin(user.getLogin());
        actionResponse.setItem(info);
    }

    /**
     * Cambia el password del usuario
     * @throws Exception Error en la DB
     */
    private void changePassword() throws Exception {
        log.debug("changePassword("+user.getId_usuario()+", "+login+")");
        Map flt = new HashMap();
        // Valida el usuario y la clave
        flt.put("id_usuario", user.getId_usuario());
        flt.put("clave", AlgorithmSHA1.calcSHA1(pw_old));
        APPBS_USUARIOS usu = APPBS_USUARIOS_MANAGER.findFirstByFilter(flt);
        if (usu == null) {
            actionResponse.setSuccess(false);
            actionResponse.setMensaje(getText("appbs_password.err.invalid"));
        } else {
            loadParams();
            UTL_PASSWORD info = (UTL_PASSWORD) actionResponse.getItem();
            // Valida el cambio de login
            if (info.isUser() && !login.equals(user.getLogin())) {
                if (APPBS_USUARIOS_MANAGER.findIdUserByLogin(login) != null) {
                    actionResponse.setSuccess(false);
                    actionResponse.setMensaje(getText("appbs_password.err.exist"));
                    return;
                }
                usu.setLogin(login);
//                DBLogger.log(DBLogger.LEVEL_INFO, "MOD_CHANGE_LOGIN", common.tools.Constants.APP_NAME, Integer.toString(user.getCod()), user.getNom());
            }
            // Cambio de la clave
            if (!"".equals(pw_new)) {
                pw_new = AlgorithmSHA1.calcSHA1(pw_new);
                if (info.getStore() > 0) {
                    // Procesa el historico
                    flt.clear();
                    flt.put("id_usuario", user.getId_usuario());
                    List<APPBS_USUARIOS_HIST_CLAVES> vItems = APPBS_USUARIOS_HIST_CLAVES_MANAGER.findByFilter(flt, -1, -1);
                    // Elimina los claves mas antiguas
                    int i = 0, extra = vItems.size() - info.getStore() + 1;
                    while (i < extra) {
                        APPBS_USUARIOS_HIST_CLAVES_MANAGER.delete(vItems.get(0));
                        vItems.remove(0);
                        i++;
                    }
                    // Verifica si se ha usado la clave
                    for (int j = 0; j < vItems.size(); j++) {
                        APPBS_USUARIOS_HIST_CLAVES hist = vItems.get(j);
                        if (hist.getClave().equals(pw_new)) {
                            actionResponse.setSuccess(false);
                            actionResponse.setMensaje(getText("appbs_password.err.used"));
                            return;
                        }
                    }
                    // Inserta en el historico
                    APPBS_USUARIOS_HIST_CLAVES_MANAGER.save(new APPBS_USUARIOS_HIST_CLAVES(user.getId_usuario(), pw_new));
                }
                usu.setClave(pw_new);
                APPBS_USUARIOS_MANAGER.save(usu);
//                DBLogger.log(DBLogger.LEVEL_INFO, "MOD_CHANGE_PASSWD", common.tools.Constants.APP_NAME, Integer.toString(user.getCod()), user.getNom());
            }
        }
    }

    /*
     * Metodos Get/Set
     */

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPw_old() {
        return pw_old;
    }

    public void setPw_old(String pw_old) {
        this.pw_old = pw_old;
    }

    public String getPw_new() {
        return pw_new;
    }

    public void setPw_new(String pw_new) {
        this.pw_new = pw_new;
    }
}