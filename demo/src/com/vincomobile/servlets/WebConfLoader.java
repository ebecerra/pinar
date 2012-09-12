package com.vincomobile.servlets;

import com.befasoft.common.model.manager.APPBS_PARAMETROS_MANAGER;
import com.befasoft.common.servlet.ConfLoader;
import com.vincomobile.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.File;
import java.util.Date;

/**
 * Servlet de inicio de la aplicacion
 */
public class WebConfLoader extends ConfLoader {

    private static Log log = LogFactory.getLog(WebConfLoader.class);

    public void init(ServletConfig config) throws ServletException {
        com.befasoft.common.tools.Constants.APP_NAME = Constants.APP_NAME;
        com.befasoft.common.tools.Constants.DB_TYPE = com.befasoft.common.tools.Constants.DB_TYPE_MYSQL;
        com.befasoft.common.tools.Constants.SQL_DATE_INPUT_FORMAT = "dd/MM/yyyy";
        com.befasoft.common.tools.Constants.confLoader = this;
        super.init(config);

        // Carga los parametros de la DB
        try {
            log.info("-----------------------------------------------------------------");
            log.info("-- "+config.getInitParameter("APP_NAME"));
            log.info("--  "+new Date());
            log.info("-----------------------------------------------------------------");
            // Parametros de la DB
            loadDBParams();

            // Crea los directorios
            File dir = new File(com.befasoft.common.tools.Constants.realAppPath+"/WEB-INF/pedidos");
            if (!dir.exists())
                dir.mkdirs();
        } catch (Exception e) {
            System.out.println("ERROR al iniciar la aplicacion");
            System.out.println(e.getMessage());
            log.error("ERROR al iniciar la aplicacion");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * Carga los parametros desde la DB
     */
    @Override
    public void loadDBParams() throws Exception {
        super.loadDBParams();
        Constants.DEFAULT_ID_LISTA_PREC = APPBS_PARAMETROS_MANAGER.getLongParameter("DEFAULT_ID_LISTA_PREC", Constants.DEFAULT_ID_LISTA_PREC);
        Constants.DEFAULT_ID_CONDPAGO = APPBS_PARAMETROS_MANAGER.getLongParameter("DEFAULT_ID_CONDPAGO", Constants.DEFAULT_ID_CONDPAGO);
        Constants.PAGE_SIZE_CLIENTES = APPBS_PARAMETROS_MANAGER.getIntParameter("PAGE_SIZE_CLIENTES", Constants.PAGE_SIZE_CLIENTES);
        Constants.PAGE_SIZE_INF_PEDIDOS = APPBS_PARAMETROS_MANAGER.getIntParameter("PAGE_SIZE_INF_PEDIDOS", Constants.PAGE_SIZE_INF_PEDIDOS);
        Constants.PAGE_SIZE_INF_FACTURAS = APPBS_PARAMETROS_MANAGER.getIntParameter("PAGE_SIZE_INF_FACTURAS", Constants.PAGE_SIZE_INF_FACTURAS);
    }

}
