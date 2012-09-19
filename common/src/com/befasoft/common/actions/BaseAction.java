package com.befasoft.common.actions;

import com.befasoft.common.beans.ActionState;
import com.befasoft.common.beans.ActionStateElement;
import com.befasoft.common.beans.LOGIN_INFO;
import com.befasoft.common.business.ActionThread;
import com.befasoft.common.business.DBLogger;
import com.befasoft.common.business.UserManager;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG;
import com.befasoft.common.model.appbs.APPBS_USUARIOS_PERFILES;
import com.befasoft.common.model.appbs.APPEX_IDIOMAS;
import com.befasoft.common.model.manager.*;
import com.befasoft.common.servlet.SessionManager;
import com.befasoft.common.tools.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.svenson.JSONParser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected String action, show, op, loginfo;
    protected Session currentSession;
    protected ActionResponse actionResponse = new ActionResponse();
    protected boolean hasError, mobile = false;

    protected Map session;

    protected LOGIN_INFO user;
    protected static Log log = null;

    protected static final ActionState actionState = new ActionState();
    
    /**
     * Inicializa el Logger
     */
    protected void setLogger() {
        log = LogFactory.getLog(this.getClass());
    }

    /**
     * Escribe el el log del usuario
     * @param id_tipo Id. del tipo (Lookup de la tabla APPBS_USUARIOS_LOG_LEGEND
     * @param nivel Nivel de log
     * @param text1 Texto 1
     * @param text2 Texto 2
     * @param text3 Texto 3
     * @param text4 Texto 4
     * @param text5 Texto 5
     */
    public void writeUserLog(Long id_tipo, Long nivel, String text1, String text2, String text3, String text4, String text5) {
        if (user != null && user.getLog_level() >= nivel)
            APPBS_USUARIOS_LOG_MANAGER.writeLog(
                    user.getId_usuario(), id_tipo, nivel, request.getSession().getId(),
                    text1 != null ? text1.length() > 1000 ? text1.substring(0, 1000) : text1 : null,
                    text2 != null ? text2.length() > 1000 ? text2.substring(0, 1000) : text2 : null,
                    text3 != null ? text3.length() > 1000 ? text3.substring(0, 1000) : text3 : null,
                    text4 != null ? text4.length() > 1000 ? text4.substring(0, 1000) : text4 : null,
                    text5 != null ? text5.length() > 1000 ? text5.substring(0, 1000) : text5 : null
            );
    }
    public void writeUserLog(Long id_tipo, Long nivel, String text1, String text2, String text3, String text4) {
        writeUserLog(id_tipo, nivel, text1, text2, text3, text4, null);
    }
    public void writeUserLog(Long id_tipo, Long nivel, String text1, String text2, String text3) {
        writeUserLog(id_tipo, nivel, text1, text2, text3, null, null);
    }
    public void writeUserLog(Long id_tipo, Long nivel, String text1, String text2) {
        writeUserLog(id_tipo, nivel, text1, text2, null, null, null);
    }
    public void writeUserLog(Long id_tipo, Long nivel, String text1) {
        writeUserLog(id_tipo, nivel, text1, null, null, null, null);
    }
    public void writeUserLog(Long id_tipo, Long nivel) {
        writeUserLog(id_tipo, nivel, null, null, null, null, null);
    }

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception Error
     */
    public String execute() throws Exception {
        if (log != null) {
            log.debug("execute: action = " + action + ", op = " + op);
        } else
            setLogger();
        session = ActionContext.getContext().getSession();
        if (session != null)
            user = (LOGIN_INFO) session.get("user");
        currentSession = HibernateUtil.currentSession();

        if ("logUser".equals(action))
            logUser();
        return Action.SUCCESS;
    }

    /**
     * Graba informacion del Log del usuario
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception Error
     */
    public String logUser() throws Exception {
        if (user != null) {
            List<HashMap> dataList = JSONParser.defaultJSONParser().parse(List.class, loginfo);
            for (Map dataRow : dataList) {
                APPBS_USUARIOS_LOG log = new APPBS_USUARIOS_LOG();
                BeanUtils.populate(log, dataRow);
                APPBS_USUARIOS_LOG_MANAGER.writeLog(user.getId_usuario(), request.getSession().getId(), log);
            }
        }
        return Action.SUCCESS;
    }

    /**
     * Obtiene el usuario de la DB
     * @param login Login
     * @param password Clave
     * @return Usuario de la tabla APPBS_USUARIOS
     */
    protected APPBS_USUARIOS getLoginUser(String login, String password) {
        actionResponse.setSuccess(false);
        Map flt = new HashMap();
        flt.put("login", login);
        flt.put("clave", Constants.PASSWORD_CHECK == 0 ? password : AlgorithmSHA1.calcSHA1(password));
        flt.put("activo", "S");
        //log.debug("getLoginUser("+login+", "+flt.get("clave")+")");
        return APPBS_USUARIOS_MANAGER.findFirstByFilter(flt);
    }

    /**
     * Valida si el usario tiene acceso a la aplicacion
     * @param login Login del usuario
     * @param password Clave de acceso
     * @param remember Indica si se recuerda el usuario a través de Cookies
     * @return Informacion del usuario
     * @throws Exception Error en la DB
     */
    protected LOGIN_INFO loginUser(String login, String password, boolean remember) throws Exception {
        LOGIN_INFO login_info = null;
        actionResponse.setSuccess(false);
        if (!Converter.isEmpty(login) && !Converter.isEmpty(password) && !"*".equals(password)) {
            // Valida el usuario
            APPBS_USUARIOS user = getLoginUser(login, password);
            if (user != null) {
                login_info = loginUser(user, remember);
            } else {
                actionResponse.setMensaje(getText("appbs.login.err.invalid"));
                DBLogger.log(DBLogger.LEVEL_ERROR, "LOGIN_INVALID", "Usuario incorrecto: "+login+"/"+password);
            }
        } else
            actionResponse.setMensaje(getText("appbs.login.err.invalid"));
        return login_info;
    }

    protected LOGIN_INFO loginUser(String login, String password) throws Exception {
        return loginUser(login, password, false);
    }

    protected LOGIN_INFO loginUser(APPBS_USUARIOS user, boolean remember) throws Exception {
        log.debug("loginUser("+user.getLogin()+", "+remember+")");
        LOGIN_INFO login_info = null;
        actionResponse.setSuccess(false);
        // Obtiene el perfil
        Map flt = new HashMap();
        flt.put("id_aplicacion", Constants.APP_NAME);
        flt.put("id_usuario", user.getId_usuario());
        APPBS_USUARIOS_PERFILES usu_perfil = APPBS_USUARIOS_PERFILES_MANAGER.findFirstByFilter(flt);
        if (usu_perfil != null) {
            login_info = getLoginInfo(user);
            login_info.setId_perfil(usu_perfil.getId_perfil());
            login_info.setPerfil_tipo(usu_perfil.getPerfil().getTipo());
            login_info.setMenu(UserManager.loadSubmenu(null, Constants.APP_NAME, usu_perfil.getId_perfil()));
            login_info.setMobile(mobile);
            login_info.setUser_agent(request.getHeader("User-Agent"));
            if (checkUniqueSession()) {
                // Verifica si existe otra sesion con este usuario
                if (SessionManager.userHasSession(request.getSession().getId(), login_info, allowMobileAndWebSession())) {
                    if (uniqueSessionFirst()) {
                        actionResponse.setMensaje(getText("appbs.login.msg.session.exist"));
                        return null;
                    } else {
                        SessionManager.removePreviusSessions(login_info, allowMobileAndWebSession());
                        removeLoginCookie();
                    }
                }
            }
            // Recuerda el usuario a través de cookies
            if (remember) {
                Cookie c = new Cookie(Constants.APP_NAME.toLowerCase()+"_user", AlgorithmSHA1.calcSHA1(Converter.formatDate(new Date(), "dd/MM/yyyy_HH:mm:ss:SSS")+"_"+AlgorithmSHA1.generateKey()));
                c.setMaxAge(60 * 60 * 24 * Constants.COOKIE_MAX_AGE);
                ServletActionContext.getResponse().addCookie(c);
                user.setCookie(c.getValue());
            }
            user.setFecha_conexion(new Date());
            APPBS_USUARIOS_MANAGER.save(user);
            actionResponse.setSuccess(true);
            DBLogger.log(DBLogger.LEVEL_INFO, "LOGIN_ENTER", "Usuario válido: "+user.getLogin()+" - "+user.getNombre()+" - "+usu_perfil.getPerfil().getTipo());
        } else {
            actionResponse.setMensaje(getText("appbs.login.err.perfil_invalid"));
            DBLogger.log(DBLogger.LEVEL_ERROR, "LOGIN_PERFIL_ERR", "Perfil incorrecto para la el usuario: " + user.getLogin() + " - " + user.getNombre());
        }
        return login_info;
    }

    protected LOGIN_INFO loginUser(APPBS_USUARIOS user) throws Exception {
        return  loginUser(user, false);
    }

    /**
     * Valida si hay cookie de usuario almacenada
     * @return Verdadero si valido al usuario
     * @throws Exception Error en la DB
     */
    protected LOGIN_INFO checkLoginCookie() throws Exception {
        log.debug("checkCookie()");
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();
        if (cookies != null) {
            String cookie_name = Constants.APP_NAME.toLowerCase()+"_user";
            for (Cookie cookie : cookies) {
                if (cookie_name.equals(cookie.getName())) {
                    Map flt = new HashMap();
                    flt.put("cookie", cookie.getValue());
                    APPBS_USUARIOS t_user = APPBS_USUARIOS_MANAGER.findFirstByFilter(flt);
                    if (t_user == null)
                        return null;
                    return loginUser(t_user, false);
                }
            }
        }
        return null;
    }

    /**
     * Remueve la cookie de usuario almacenada
     * @throws Exception Error en la DB
     */
    protected void removeLoginCookie() throws Exception {
        log.debug("removeLoginCookie()");
        if (user != null) {
            APPBS_USUARIOS t_user = APPBS_USUARIOS_MANAGER.findByKey(user.getId_usuario());
            if (t_user != null) {
                t_user.setCookie(null);
                APPBS_USUARIOS_MANAGER.save(t_user);
            }
        }
    }

    /**
     * Recupera la clave de acceso
     * @param template Nombre del template html
     * @param email Direccion de email
     * @throws Exception Errores
     * @return Indica si se envio la solicitud correctamente
     */
    protected boolean recoverPassword(String template, String email) throws Exception {
        log.debug("recoverPassword("+email+")");
        Map flt = new HashMap();
        flt.put("email", email);
        APPBS_USUARIOS user = APPBS_USUARIOS_MANAGER.findFirstByFilter(flt);
        boolean ok = user != null;
        if (ok) {
            DBLogger.log(DBLogger.LEVEL_INFO, "LOGIN_RECOVER_PWD", "El usuario: "+user.getLogin()+" - "+user.getNombre()+" solicita un cambio de contraseña");
            String fecha = Converter.formatDate(new Date(), "dd-MM-yyyy");
            StringBuffer url = new StringBuffer();
            url.append("http://").append(request.getLocalName()).append(":").append(request.getLocalPort()).append(request.getContextPath());
            url.append("/Index.action?action=reset&email=").append(email).append("&fecha=").append(fecha).append("&hash=").append(AlgorithmSHA1.calcSHA1("MAIL_RECOVER_"+email+"_"+user.getClave()+"_"+user.getId_usuario()));
            log.debug(url.toString());
            // Enviar un mail con la solicitud de cambio de contraseña
            Map templateArguments = new HashMap();
            templateArguments.put("link", url);
            templateArguments.put("nombre", user.getNombre());
            ok = sendEmail(template, templateArguments, email, getText("appbs.login.recover.subject"));
            actionResponse.setMensaje(getText(ok ? "appbs.login.recover.ok" : "appbs.login.recover.err"));
        } else
            actionResponse.setMensaje(getText("appbs.login.recover.invalid"));
        actionResponse.setSuccess(ok);
        return ok;
    }

    /**
     * Inicia el reseteo de la contraseña
     * @param email Direccion de email
     * @param hash Hash de seguridad
     * @param fecha Fecha de solicitud
     * @return Id. del usuario
     */
    protected APPBS_USUARIOS resetPassword(String email, String hash, String fecha) {
        actionResponse.setSuccess(false);
        Map flt = new HashMap();
        flt.put("email", email);
        APPBS_USUARIOS user = APPBS_USUARIOS_MANAGER.findFirstByFilter(flt);
        if (user != null) {
            // Verifica el SHA
            String sha = AlgorithmSHA1.calcSHA1("MAIL_RECOVER_"+email+"_"+user.getClave()+"_"+user.getId_usuario());
            if (sha.equals(hash)) {
                // Verifica la fecha
                Date fec = Converter.getDate(fecha, "dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DAY_OF_YEAR, -1);
                if (fec.compareTo(cal.getTime()) >= 0) {
                    actionResponse.setSuccess(true);
                    return user;
                } else
                    actionResponse.setMensaje(getText("login.msg.recover.date"));
            } else
                actionResponse.setMensaje(getText("login.msg.recover.sha"));
        } else {
            actionResponse.setMensaje(getText("login.msg.recover.invalid"));
        }
        return null;
    }

    /**
     * Cambia la clave de acceso de un cliente
     * @param id_usuario Id. del usuario
     * @param password Nueva clave
     */
    public void changePassword(Long id_usuario, String password) {
        log.debug("changePassword(" + id_usuario + ")");
        APPBS_USUARIOS usu = APPBS_USUARIOS_MANAGER.findByKey(id_usuario);
        if (usu != null) {
            usu.setClave(AlgorithmSHA1.calcSHA1(password));
            APPBS_USUARIOS_MANAGER.save(usu);
            actionResponse.setSuccess(true);
            DBLogger.log(DBLogger.LEVEL_INFO, "LOGIN_CHANGE_PWD", "El usuario: "+usu.getLogin()+" - "+usu.getNombre()+" cambia la contraseña");
        } else {
            actionResponse.setSuccess(false);
            actionResponse.setMensaje(getText("appbs.login.msg.recover.invalid"));
        }
    }

    /**
     * Selecciona el idioma por defecto del navegador (se utiliza el siguiente orden de seleccion)
     *
     *    1. Primer idioma disponible de la lista de idiomas del navegador
     *    2. Idioma por defecto
     *    3. Primer idioma disponible
     */
    public void loadNavLanguage() {
        log.debug("loadNavLanguage()");
        APPEX_IDIOMAS idi = null;
        Map flt = new HashMap();
        flt.put("activo", true);
        String lang = request.getHeader("accept-language");
        if (lang != null) {
            lang = lang.toLowerCase();
            StringTokenizer st = new StringTokenizer(lang, ",");
            while (st.hasMoreTokens()) {
                String id_idioma = st.nextToken().substring(0, 2);
                flt.put("abreviatura", id_idioma);
                idi = APPEX_IDIOMAS_MANAGER.findFirstByFilter(flt);
                if (idi != null)
                    break;
            }
        }
        if (idi == null) {
            flt.clear();
            flt.put("activo", true);
            flt.put("id_idioma", Constants.DEFAULT_ID_IDIOMA);
            idi = APPEX_IDIOMAS_MANAGER.findFirstByFilter(flt);
            if (idi == null) {
                DBLogger.log(DBLogger.LEVEL_ERROR, "LOGIN_IDI_ERR", "No se encuentra el idioma por defecto: id_idioma = "+Constants.DEFAULT_ID_IDIOMA);
                log.error("No se encuentra el idioma por defecto");
            }
        }
        // Obtiene el primer idioma
        if (idi != null) {
            flt.clear();
            flt.put("activo", true);
            List<APPEX_IDIOMAS> idiomas = APPEX_IDIOMAS_MANAGER.findByFilter(flt);
            if (idiomas.size() > 0) {
                idi = idiomas.get(0);
            }
        }
        if (idi != null) {
            session.put("idioma", idi);
            session.put("id_idioma", idi.getId_idioma());
            Locale loc = new Locale(idi.getIso_2().toLowerCase().trim());
            session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, loc);
            ActionContext.getContext().setLocale(loc);
        }
    }

    /**
     * Envia un email con fichero adjuntos
     *
     * @param template Nombre del template html
     * @param templateValues Valores a utilizar en el template
     * @param to Dirección a la que se envia (lista separada por ;)
     * @param subject Asunto del mensaje
     * @param attachemts Lista de ficheros adjuntos
     * @param images Lista de images embebidas
     * @return Bandera que indica si se envío correctamente
     * @throws Exception Error al enviar
     */
    public boolean sendEmail(String template, Map templateValues, String to, String subject, String[] attachemts, MailMessageImages[] images) throws Exception {
        // Carga el template de email
        try {
            template = FileTool.loadTextFile(template);
        } catch (Exception e) {
            log.error("Error al cargar el template: "+template);
            log.error(e);
            writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_EMAIL_TEMPL, APPBS_USUARIOS_LOG.LEVEL_ERROR, template);
            return false;
        }
        StringTemplate email = new StringTemplate(template, DefaultTemplateLexer.class);
        for (Object o : templateValues.keySet()) {
            String key = (String) o;
            email.setAttribute(key, templateValues.get(key));
        }
        // Envia el email
        MailMessage msg = new MailMessage(to, subject, email.toString());
        if (attachemts != null && attachemts.length > 0)
            msg.setFileAttachments(attachemts);
        if (images != null && images.length > 0)
            msg.setImageAttachments(images);
        Mail mailer = new Mail(
                APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_SERVER"), APPBS_PARAMETROS_MANAGER.getIntParameter("SMTP_PORT", 25),
                APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_FROM"), APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_USER"),
                APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_PASSWD"), APPBS_PARAMETROS_MANAGER.getBooleanParameter("SMTP_USE_SSL"));
        boolean result = mailer.sendHTMLMsg(msg) != null;
        if (result)
            writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_EMAIL_OK, APPBS_USUARIOS_LOG.LEVEL_COMMON_INFO, to, subject);
        else
            writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_EMAIL_ERR, APPBS_USUARIOS_LOG.LEVEL_ERROR, to, subject);
        return result;
    }

    public boolean sendEmail(String template, Map templateValues, String to, String subject, String[] attachemts) throws Exception {
        return sendEmail(template, templateValues, to, subject, attachemts, null);
    }

    public boolean sendEmail(String template, Map templateValues, String to, String subject, MailMessageImages[] images) throws Exception {
        return sendEmail(template, templateValues, to, subject, null, images);
    }

    public boolean sendEmail(String template, Map templateValues, String to, String subject) throws Exception {
        return sendEmail(template, templateValues, to, subject, null, null);
    }

    public boolean sendEmail(String to, String subject, String text, String[] attachemts) throws Exception {
        // Envia el email
        MailMessage msg = new MailMessage(to, subject, text);
        if (attachemts != null && attachemts.length > 0)
            msg.setFileAttachments(attachemts);
        Mail mailer = new Mail(APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_SERVER"), APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_FROM"), APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_USER"), APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_PASSWD"));
        boolean result = mailer.sendHTMLMsg(msg) != null;
        if (result)
            writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_EMAIL_OK, APPBS_USUARIOS_LOG.LEVEL_COMMON_INFO, to, subject);
        else
            writeUserLog(APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_EMAIL_ERR, APPBS_USUARIOS_LOG.LEVEL_ERROR, to, subject);
        return result;
    }

    /**
     * Retorna la información de usario para almacenar en la sesion
     * @param user Informacion del usario (tabla APPBS_USUARIOS)
     * @return Informacion del usuario
     */
    public LOGIN_INFO getLoginInfo(APPBS_USUARIOS user) {
        return new LOGIN_INFO(user);
    }

    /**
     * Indica si se valida la multisesion
     * @return Verdarero si el usuario solo puede entrar una vez
     */
    public boolean checkUniqueSession() {
        return false;
    }

    /**
     * Indica el modo de tratar las sesiones unicas
     * @return
     *      true - La primera sesion es la que se mantiene, se rechaza el login
     *      false - Se acepta el login y se invalidan el resto de sessiones activas
     */
    public boolean uniqueSessionFirst() {
        return true;
    }

    /**
     * Indica si se permiten sesiones Web y Mobile al mismo tiempo
     * @return
     *      true - Permite una sesion Web y otra Mobile
     *      false - Solo permite una sesion
     */
    public boolean allowMobileAndWebSession() {
        return true;
    }

    /**
     * Hace un rollback de una transaccion
     * @param tx Transaccion
     * @param e Excepcion que provoco el rollback
     */
    public static void rollbackTransaction(Transaction tx, Throwable e) {
        if (e != null) {
            e.printStackTrace();
            log.error("Error al categorizar el mensaje: "+e.getMessage());
            log.error("Error", e);
        }
        try {
            tx.rollback();
        }
        catch (Throwable e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Comienza la ejecucion de una tarea
     *
     * @param element Elemento de control de estado de la ejecucion
     */
    public void startTask(ActionStateElement element) {
        log.debug("Iniciando la tarea: " + element.getElementId());
        ActionStateElement item = actionState.getStates(element.getElementId());
        if (item == null) {
            actionState.addState(element);
        } else {
            int status = actionState.getStatus(item.getElementId());
            if (status != ActionStateElement.STATE_RUNNING) {
                actionState.deleteState(item.getElementId());
                actionState.addState(element);
            } else {
                actionResponse.setSuccess(false);
                return;
            }
        }
        ActionThread actionThread = getActionThread(actionState, element);
        if (actionThread != null)
            actionThread.start();       
    }

    /**
     * Obtiene el hilo que ejecuta la tarea
     * 
     * @param actionState Estados de ejecucion de las acciones
     * @param element Elemento de control de estado de la ejecucion
     * @return Hilo
     */
    public ActionThread getActionThread(ActionState actionState, ActionStateElement element) {
        return null;
    }

    /**
     * Cancela el proceso de borrar
     * @param key Identificador de la tarea
     */
    public void cancelTask(String key) {
        log.debug("Cancelando la tarea: " + key);
        ActionStateElement element = null;
        element = actionState.getStates(key);
        if (element != null) {
            synchronized (actionState){
                actionState.setCancel(key, true);
                actionState.setStatus(key, ActionStateElement.STATE_CANCELED);
            }
        }
    }

    /**
     * Obtiene el progreso actual de una tarea
     * @param key Identificador de la tarea
     */
    public void getTaskCurrent(String key) {
        log.debug("Progreso de la tarea: " + key);
        ActionStateElement element = null;
        synchronized (actionState){
            element = actionState.getStates(key);
            if (element != null) {
                int status = actionState.getStatus(key);
                if (status == ActionStateElement.STATE_RUNNING) {
                    actionResponse.put("countTotal", actionState.getTotal(key));
                    actionResponse.put("countLeft", actionState.getCurrent(key));
                    actionResponse.put("status", "RUNNING");
                } else
                    actionResponse.put("status", status == ActionStateElement.STATE_FINISH_OK ? "FINISH_OK" : "FINISH_ERR");
            } else {
                actionResponse.put("status", "FINISH_ERR");
            }
        }
    }

    /*
     * Metodos Set/Get
     */
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        request = httpServletRequest;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        response = httpServletResponse;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ActionResponse getActionResponse() {
        return actionResponse;
    }

    public void setActionResponse(ActionResponse actionResponse) {
        this.actionResponse = actionResponse;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }


    public LOGIN_INFO getUser() {
        return user;
    }

    public void setUser(LOGIN_INFO user) {
        this.user = user;
    }

    public String getLoginfo() {
        return loginfo;
    }

    public void setLoginfo(String loginfo) {
        this.loginfo = loginfo;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }
}