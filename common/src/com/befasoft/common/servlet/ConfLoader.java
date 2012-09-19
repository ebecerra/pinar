package com.befasoft.common.servlet;

import com.befasoft.common.business.*;
import com.befasoft.common.model.manager.APPBS_PARAMETROS_MANAGER;
import com.befasoft.common.model.manager.APPBS_TABLAS_MANAGER;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Environment;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.Time;
import java.util.Date;


/**
 * Servlet para cargar la configuracion global de la aplicacion
 */
public class ConfLoader extends HttpServlet {

    protected static boolean db_enabled = true;
    private static Log log = LogFactory.getLog(ConfLoader.class);

    /**
     * Inicializacion del Servlet
     */
    public void init(ServletConfig config) throws ServletException {
        // Registra los convertidores personalizados para la librerias de Apache BeanUtils
        ConvertUtils.register(new DateConverter(), Date.class);
        ConvertUtils.register(new TimeConverter(), Time.class);
        ConvertUtils.register(new IntegerConverter(), Integer.class);
        ConvertUtils.register(new LongConverter(), Long.class);
        // Genera informacion en los logs
        Constants.realAppPath = config.getServletContext().getRealPath("/");
        String param = config.getInitParameter("APP_NAME");
        if (param == null)
            param = "";
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-- Iniciando aplicacion: "+param);
        System.out.println("--  "+new Date());
        try {
            Environment logConfig = new Environment();
            param = config.getInitParameter("LOG4J");
            logConfig.load(Constants.realAppPath + param);
            logConfig.put("appHome", Constants.realAppPath);
            param = config.getInitParameter("LOG4J_FILE");
            if (param == null)
                param = "application";
            logConfig.put("logfile", param);
            logConfig.plainReferences();

            System.out.println("-- Log Conf. File: "+Constants.realAppPath+param);
            PropertyConfigurator.configure(logConfig);
        }
        catch (Throwable e) {
            System.out.println("Error cargando log4j"+e.getMessage());
            throw new ServletException("Error cargando log4j", e);
        }
        
        log.debug("Log4j loaded");
        System.out.println("-----------------------------------------------------------------");
        if (db_enabled)
            DBLogger.log(DBLogger.LEVEL_INFO, "APP_START", "Iniciando aplicación: " + config.getInitParameter("APP_NAME"));
    }

    /**
     * Carga los parametros desde la DB
     * @throws Exception Error de DB
     */
    public void loadDBParams() throws Exception {
        // Nivel de log
        DBLogger.setLevel(APPBS_PARAMETROS_MANAGER.getLongParameter("DBLOG_LEVEL", DBLogger.LEVEL_INFO));
        // Servidor de correo
        Constants.SMTP_SERVER = APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_SERVER");
        Constants.SMTP_USER = APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_USER");
        Constants.SMTP_PASSWD = APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_PASSWD");
        Constants.SMTP_FROM = APPBS_PARAMETROS_MANAGER.getStrParameter("SMTP_FROM");
        Constants.SMTP_PORT = APPBS_PARAMETROS_MANAGER.getIntParameter("SMTP_PORT", Constants.SMTP_PORT);
        // Valores por defecto
        Constants.ID_PAIS_ESPANA = APPBS_PARAMETROS_MANAGER.getLongParameter("ID_PAIS_ESPANA", Constants.ID_PAIS_ESPANA);
        Constants.TABLEID_APPEX_PAISES = APPBS_TABLAS_MANAGER.getTableId("APPEX_PAISES");
        Constants.PASSWORD_CHECK = APPBS_PARAMETROS_MANAGER.getLongParameter("PASSWORD_CHECK", Constants.PASSWORD_CHECK);
        Constants.COOKIE_MAX_AGE = APPBS_PARAMETROS_MANAGER.getIntParameter("COOKIE_MAX_AGE", Constants.COOKIE_MAX_AGE);
        Constants.DEFAULT_ID_IDIOMA = APPBS_PARAMETROS_MANAGER.getLongParameter("DEFAULT_ID_IDIOMA", Constants.DEFAULT_ID_IDIOMA);
        Constants.USER_CHECK_UNIQUE_EMAIL = APPBS_PARAMETROS_MANAGER.getBooleanParameter("USER_CHECK_UNIQUE_EMAIL", Constants.USER_CHECK_UNIQUE_EMAIL);
    }

}
