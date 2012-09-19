package com.befasoft.common.business;

import com.befasoft.common.model.appbs.APPBS_LOG;
import com.befasoft.common.model.manager.APPBS_LOG_MANAGER;
import com.befasoft.common.tools.Constants;

import java.util.Date;

public class DBLogger {

    public static final long LEVEL_ERROR     = 1;
    public static final long LEVEL_WARNING   = 2;
    public static final long LEVEL_INFO      = 3;
    public static final long LEVEL_TRACE     = 4;
    public static final long LEVEL_5         = 5;
    public static final long LEVEL_6         = 6;

    static long level = 4;

    /**
     * Log a la base de datos
     * @param lev Nivel de log
     * @param typ Tipo de Log
     * @param log Informacion de log
     */
    public static void log(long lev, String typ, String log) {
        if (level < lev)
            return;
        if (log != null&& log.length() > 1999)
            log = log.substring(0, 1990) + " [...]";
        APPBS_LOG appbs_log = new APPBS_LOG();
        appbs_log.setId_aplicacion(Constants.APP_NAME);
        appbs_log.setNivel(lev);
        appbs_log.setTipo(typ);
        appbs_log.setFecha(new Date());
        appbs_log.setLog(log);
        APPBS_LOG_MANAGER.save(appbs_log);
    }

    public static void log(long lev, String typ, Throwable error) {
        log(lev, typ, error.getCause() != null ? error.getCause().getMessage() : error.getLocalizedMessage());
    }

    public static void log(long lev, String typ, Exception error) {
        log(lev, typ, error.getCause() != null ? error.getCause().getMessage() : error.getLocalizedMessage());
    }

    /*
     * Metodos Set/Get
     */
    public static long getLevel() {
        return level;
    }

    public static void setLevel(long level) {
        DBLogger.level = level;
    }
}
