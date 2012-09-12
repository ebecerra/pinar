package com.vincomobile;

public class Constants {
    public static final String APP_NAME             = "DEMO";
    public static final String DEFAULT_ID_IDIOMA    = "ES";

    public static final long FIRST_ID_CLIENTES          = 100000000;
    public static final long FIRST_MOBILE_PED_NUM       = 200000000;
    public static final long FIRST_MOBILE_ID_CLIENTE    = 200000000;
    public static final long FIRST_MOBILE_ID_DIRECCION  = 200000000;
    public static final long FIRST_MOBILE_ID_CONTACTO   = 200000000;

    public static Long PEDIDOS_ID_ORIGEN        = (long) 1043;
    public static Long DEFAULT_ID_LISTA_PREC    = (long) 124619;
    public static Long DEFAULT_ID_CONDPAGO      = (long) 3583;

    public static int PAGE_SIZE_CLIENTES        = 25;
    public static int PAGE_SIZE_INF_PEDIDOS     = 25;
    public static int PAGE_SIZE_INF_FACTURAS    = 25;

    // Log de usuario del CatalogAction
    public static long LOG_CATALOG_FAMILIAS             = 1000;
    public static long LOG_CATALOG_FAMILIA_LIST_PG      = 1001;
    public static long LOG_CATALOG_SUBFAMILIA_LIST_PG   = 1002;
    public static long LOG_CATALOG_ARTICULO_LOAD        = 1003;
    public static long LOG_CATALOG_NOVEDADES            = 1004;
    public static long LOG_CATALOG_ART_PROMO            = 1005;
    public static long LOG_CATALOG_PROMOCIONES          = 1006;
    public static long LOG_CATALOG_FAMILIA_LIST         = 1007;
    public static long LOG_CATALOG_SUBFAMILIA_LIST      = 1008;

    // Log de usuario de ContactoAction
    public static long LOG_CONTACTO_FILLFORM        = 1100;
    public static long LOG_CONTACTO_SENDFORM        = 1101;

    // Log de usuario de LoginAction
    public static long LOG_LOGIN_OK                 = 1200;
    public static long LOG_LOGIN_OUT                = 1201;

    // Log de usuario de PerfilAction
    public static long LOG_PERFIL_LOAD              = 1300;
    public static long LOG_PERFIL_CHANGE_EMAIL      = 1301;
    public static long LOG_PERFIL_SEND_EMAIL_ERR    = 1302;
    public static long LOG_PERFIL_CHANGE_ADDRESS    = 1303;
    public static long LOG_PERFIL_CHANGE_PASSWORD   = 1304;

    // Log de usuario de PedidosAction
    public static long LOG_PEDIDO_PED_PRINT         = 1400;
    public static long LOG_PEDIDO_PRESUP_PRINT      = 1401;
    public static long LOG_PEDIDO_ADD_ITEM          = 1402;
    public static long LOG_PEDIDO_DEL_ITEM          = 1403;
    public static long LOG_PEDIDO_CHANGE_ITEM       = 1404;
    public static long LOG_PEDIDO_CLOSE_PED         = 1405;
    public static long LOG_PEDIDO_UPDATE_PED        = 1406;
    public static long LOG_PEDIDO_LOAD_PED          = 1407;
    public static long LOG_PEDIDO_EMPTY_PED         = 1408;

    // Log de usuario de MultiplicadoresAction
    public static long LOG_MULT_LIST                = 1500;
    public static long LOG_MULT_UPDATE              = 1501;
    public static long LOG_MULT_UPDATE_PRECIOS      = 1502;

    // Log de usuario de ClientesAction
    public static long LOG_CLIENTES_SELECT          = 1600;
    public static long LOG_CLIENTES_LIST            = 1601;

    // Log de usuario de Informes
    public static long LOG_INF_PEDIDOS_LIST         = 1700;
    public static long LOG_INF_PEDIDOS_DETAIL       = 1701;
    public static long LOG_INF_PEDIDOS_PEND         = 1702;

    public static long LOG_INF_FACTURAS_LIST        = 1800;

    public static long LOG_INF_EXP_PED_LIST         = 1900;
    public static long LOG_INF_EXP_PEDLIN_LIST      = 1901;
}
