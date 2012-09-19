package com.befasoft.common.tools;

import com.befasoft.common.servlet.ConfLoader;

public class Constants {

    public static String realAppPath        = "";               // Directorio fisico donde se almacena la aplicacion
    
    // Constantes para SHA1
    public static int MIN_LENGTH_KEY    = 6;    // Longitud manima de la Llave
    public static int MAX_LENGTH_KEY    = 10;   // Longitud maxima de la Llave
    public static int MIN_CHAR_CODE     = 97;   // Menor codigo ASCII Aceptado (a)
    public static int MAX_CHAR_CODE     = 122;  // Mayor codigo ASCII Aceptado (z)
    public static String PHRASE_KEY     = "Que le pasa a la mariposa que no se posa en la flor de la calabaza";
    public static String SECURITY_KEY   = "B4K8b$k()sD%3";

    // Gestión de sesiones
    public static long SESSION_MAX_IDLE_TIME_VALUE  = (30 * 60 * 1000); // 30 minutos (en milisegundos: 30 * 60 * 1000)
    public static int SESSION_MAX_TRY               = 10;               // Cantidad maxima de intentos para generar un ID de sesion

    // Constantes para la gestion de licencias
    public static int MAX_UPDATE_DAYS   = 7;    // Cantidad maxima de días sin actualizar la licencia

    // Constantes de DB
    public static final String MAIN_MENU    = "MAIN_MENU";      // Id. del menu principal
    public static final int DB_TYPE_MYSQL   = 1;                // Servidor de DB MySQL
    public static final int DB_TYPE_MSSQL   = 2;                // Servidor de DB Microsft SQL Server
    public static final int DB_TYPE_ORACLE  = 3;                // Servidor de DB Oracle
    public static int DB_TYPE               = DB_TYPE_MYSQL;    // Tipo de la DB
    public static String DB_SYSDATE         = "sysdate()";      // Funcion para obtener la fecha actual
    public static String SQL_DATE_OUTPUT_FORMAT     = "yyyy-MM-dd";
    public static String SQL_DATE_INPUT_FORMAT      = "dd/MM/yyyy";
    public static String SQL_DATETIME_OUTPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String SQL_DATETIME_INPUT_FORMAT  = "dd/MM/yyyy HH:mm:ss";
    public static String SQL_SELECT_LASTID          = "SELECT LAST_INSERT_ID()";

    public static long ID_PAIS_ESPANA               = 1;
    public static long PASSWORD_CHECK               = 1;
    public static long DEFAULT_ID_IDIOMA            = 1;
    public static boolean USER_CHECK_UNIQUE_EMAIL   = true;

    // Id. de las tablas
    public static Long TABLEID_APPEX_PAISES;

    // Codigo de excepciones tratadas
    public static int DB_ERROR_PRIMARYKEY;
    public static int DB_ERROR_FOREINGKEY;


    public static String APP_NAME;                          // Nombre de la aplicacion
    public static ConfLoader confLoader     = null;         // Clase que carga la configuracion
    public static int PAGE_NUMBERS          = 5;            // Numero de paginas a mostrar
    public static int MAX_LENGTH_READ       = 50 * 1024;    // Gestion de ficheros
    public static int COOKIE_MAX_AGE        = 31;           // Maximo numero de dias que es valida una Cookie

    // Datos del servidor de correo
    public static String SMTP_SERVER;
    public static String SMTP_USER;
    public static String SMTP_PASSWD;
    public static String SMTP_FROM;
    public static int SMTP_PORT = 25;

    /*
     * Selecciona las opciones segun el tipo de base de datos
     */
    public static void setDBOptions(int dbType) {
        DB_TYPE = dbType;
        switch (DB_TYPE) {
            case DB_TYPE_MYSQL:
                DB_SYSDATE                  = "sysdate()";
                SQL_DATE_OUTPUT_FORMAT      = "yyyy-MM-dd";
                SQL_DATE_INPUT_FORMAT       = "dd/MM/yyyy";
                SQL_DATETIME_OUTPUT_FORMAT  = "yyyy-MM-dd HH:mm:ss";
                SQL_DATETIME_INPUT_FORMAT   = "dd/MM/yyyy HH:mm:ss";
                SQL_SELECT_LASTID           = "SELECT LAST_INSERT_ID()";
                DB_ERROR_PRIMARYKEY         = 1062;
                DB_ERROR_FOREINGKEY         = 1451;
                break;

            case DB_TYPE_MSSQL:
                DB_SYSDATE                  = "getdate()";
                SQL_DATE_OUTPUT_FORMAT      = "yyyy-MM-dd";
                SQL_DATE_INPUT_FORMAT       = "dd/MM/yyyy";
                SQL_DATETIME_OUTPUT_FORMAT  = "yyyy-MM-dd HH:mm:ss";
                SQL_DATETIME_INPUT_FORMAT   = "dd/MM/yyyy HH:mm:ss";
                SQL_SELECT_LASTID           = "SELECT SCOPE_IDENTITY()";
                DB_ERROR_PRIMARYKEY         = 2627;
                DB_ERROR_FOREINGKEY         = 547;
                break;
        }

    }
}
