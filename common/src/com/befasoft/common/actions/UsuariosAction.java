package com.befasoft.common.actions;

import com.befasoft.common.beans.LOGIN_INFO;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_PERFILES;
import com.befasoft.common.model.manager.APPBS_APLICACIONES_MANAGER;
import com.befasoft.common.model.manager.APPBS_USUARIOS_MANAGER;
import com.befasoft.common.model.manager.APPBS_USUARIOS_PERFILES_MANAGER;
import com.befasoft.common.servlet.SessionManager;
import com.befasoft.common.tools.AlgorithmSHA1;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Action;
import org.svenson.JSONParser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Devtools.
 * Date: 06/05/2011
 */
public class UsuariosAction extends BaseExcelAction {

    protected String login, nombre, email, id_aplicacion, param_1, param_2, param_3, param_4, sessionId;
    protected LOGIN_INFO login_info;
    protected Long id_usuario, id_perfil, int_param_1, int_param_2, int_param_3, int_param_4;
    protected Integer tipo;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception Error
     */
    @Override
    public String execute() throws Exception {
        if (super.execute() == null)
            return null;

        if ("app_avail".equals(action)) {
            bodyResult.elements = APPBS_APLICACIONES_MANAGER.findAvailableApp(id_usuario);
        } else if ("app_select".equals(action)) {
            bodyResult.elements = APPBS_APLICACIONES_MANAGER.findSelectedApp(id_usuario);
        } else if ("add_app_per".equals(action)) {
            bodyResult.success = APPBS_USUARIOS_PERFILES_MANAGER.addUserPerfil(id_aplicacion, id_usuario, id_perfil);
        } else if ("del_app_per".equals(action)) {
            bodyResult.success = APPBS_USUARIOS_PERFILES_MANAGER.deleteUserPerfil(id_aplicacion, id_usuario, id_perfil);
        } else if ("getuser".equals(action)) {
            bodyResult.success = true;
            bodyResult.put("user", user);
        } else if ("getuserlist".equals(action)) {
            setReturn(SessionManager.listUserSessions());
        } else if ("removeuser".equals(action)) {
            SessionManager.removeSession(sessionId);
        }

        return "mobile".equals(op) ? "mobile" : Action.SUCCESS;
    }

    /**
     * Genera el Excel
     * @throws Exception Error
     * @return Verdadero si se genero correctamente el documento
     */
    protected boolean writeExcel() throws Exception {
        file = "usuarios.xls";
        List usuarios = APPBS_USUARIOS_MANAGER.findByFilter(getFilter(), sort, dir);
        exportItems(getText("appbs_usuarios.caption"), "appbs_usuarios.", usuarios);
        return true;
    }

    /**
     * Ejecuta guardar una entidad.
     *
     * @return Action result
     * @throws Exception Error
     */
    @Override
    protected String executeUpdate() throws Exception {
        List<HashMap> dataList = JSONParser.defaultJSONParser().parse(List.class, dataToSave);
        id_perfil = (Long) dataList.get(0).get("id_perfil");
        String clave = (String) dataList.get(0).get("contrasenya");
        dataList.get(0).remove("contrasenya");

        if (Constants.PASSWORD_CHECK != 0) {
            if (!"*".equals(clave))
                dataList.get(0).put("clave", AlgorithmSHA1.calcSHA1(clave));
            else {
                APPBS_USUARIOS usuario = APPBS_USUARIOS_MANAGER.findByKey(Converter.getLong((String) dataList.get(0).get("id_usuario")));
                if (usuario != null)
                    dataList.get(0).put("clave", usuario.getClavereal());
            }
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        dataToSave = gson.toJson(dataList);

        return super.executeUpdate();
    }

    /**
     * Se llama cuando el registro ha sido modificado
     *
     * @param bean Bean modificado
     * @throws Exception Error
     */
    @Override
    protected void wasModified(EntityBean bean) throws Exception {
        if (!Converter.isEmpty(id_perfil)) {
            Map filter = new HashMap();
            filter.put("id_aplicacion", Constants.APP_NAME);
            filter.put("id_usuario", ((APPBS_USUARIOS) bean).getId_usuario());
            List<APPBS_USUARIOS_PERFILES> perfiles = APPBS_USUARIOS_PERFILES_MANAGER.findByFilter(filter, -1, -1);
            boolean insert = true;
            for (APPBS_USUARIOS_PERFILES perfil : perfiles) {
                if (perfil.getId_perfil().longValue() == id_perfil)
                    insert = false;
                else
                    APPBS_USUARIOS_PERFILES_MANAGER.delete(perfil);
            }
            if (insert) {
                APPBS_USUARIOS_PERFILES perfil = new APPBS_USUARIOS_PERFILES();
                perfil.setId_aplicacion(Constants.APP_NAME);
                perfil.setId_usuario(((APPBS_USUARIOS) bean).getId_usuario());
                perfil.setId_perfil(id_perfil);
                APPBS_USUARIOS_PERFILES_MANAGER.save(perfil);
            }
        }
    }

    /**
     * Indica si se puede modificar este registro
     *
     * @param values Map con los valores recibidos
     * @param bean Bean que se va a modificar
     * @return <code>true</code> Si se puede modificar este registro
     * @throws Exception Error
     */
    @Override
    protected String canModify(Map values, EntityBean bean) throws Exception {
        APPBS_USUARIOS usuario = (APPBS_USUARIOS) bean;
        if (!usuario.getLogin().equals(values.get("login_old"))) {
            if (APPBS_USUARIOS_MANAGER.countIdUserByLogin(usuario.getLogin()) > 1)
                return getText("appbs_usuarios.err.login.exist");
            if (Constants.USER_CHECK_UNIQUE_EMAIL && !Converter.isEmpty(usuario.getEmail())) {
                if (APPBS_USUARIOS_MANAGER.countIdUserByEmail(usuario.getEmail()) > 1)
                    return getText("appbs_usuarios.err.email.exist");
            }
        }
        return null;
    }

    /**
     * Indica si se puede insertar este registro
     *
     * @param values Map con los valores recibidos
     * @param bean Bean que se va a insertar
     * @return <code>true</code> Si se puede insertar este registro
     * @throws Exception Error
     */
    @Override
    protected String canInsert(Map values, EntityBean bean) throws Exception {
        APPBS_USUARIOS usuario = (APPBS_USUARIOS) bean;
        usuario.setFecha_creacion(new Date());
        Long count = APPBS_USUARIOS_MANAGER.countIdUserByLogin(usuario.getLogin());
        String insert = count > 0 ? getText("appbs_usuarios.err.login.exist") : null;
        if (insert == null && Constants.USER_CHECK_UNIQUE_EMAIL && !Converter.isEmpty(usuario.getEmail())) {
            count = APPBS_USUARIOS_MANAGER.countIdUserByEmail(usuario.getEmail());
            insert = count > 0 ? getText("appbs_usuarios.err.email.exist") : null;
        }
        return insert;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_USUARIOS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_USUARIOS_MANAGER.class;
    }

    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(login))
            filter.put("login", login+"%");
        if (!Converter.isEmpty(nombre))
            filter.put("nombre", nombre+"%");
        if (!Converter.isEmpty(email))
            filter.put("email", email+"%");
        if (!Converter.isEmpty(param_1))
            filter.put("param_1", param_1);
        if (!Converter.isEmpty(param_2))
            filter.put("param_2", param_2);
        if (!Converter.isEmpty(param_3))
            filter.put("param_3", param_3);
        if (!Converter.isEmpty(param_4))
            filter.put("param_4", param_4);
        if (!Converter.isEmpty(int_param_1))
            filter.put("int_param_1", int_param_1);
        if (!Converter.isEmpty(int_param_2))
            filter.put("int_param_2", int_param_2);
        if (!Converter.isEmpty(int_param_3))
            filter.put("int_param_3", int_param_3);
        if (!Converter.isEmpty(int_param_4))
            filter.put("int_param_4", int_param_4);
        if (!Converter.isEmpty(tipo))
            filter.put("tipo", tipo);

        return filter;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LOGIN_INFO getLogin_info() {
        return login_info;
    }

    public void setLogin_info(LOGIN_INFO login_info) {
        this.login_info = login_info;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public Long getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
