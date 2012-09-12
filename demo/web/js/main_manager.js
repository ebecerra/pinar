function abrirTab(title, idMenu, enlace) {

    if (idMenu == 'APPBS_ADMIN_PASSWD') {
        formPassword();
    } else if (idMenu == 'APPBS_ADMIN_USER') {
        openTabUsuarios(title, idMenu);
    } else if (idMenu == 'APPBS_ADMIN_PERFIL') {
        openTabPerfiles(title, idMenu);
    } else if (idMenu == 'APPBS_ADMIN_PARAM') {
        openTabParams(title, idMenu);
    } else if (idMenu == 'APPBS_ADMIN_LOG') {
        openTabLog(title, idMenu);
    } else if (idMenu == 'APPBS_ADMIN_LOOKUPS') {
        openTabAppbs_lookups(title, idMenu);
    } else if (idMenu == 'APPBS_ADMIN_USR_LOG') {
        openTabAppbs_usuarios_log(title, idMenu);
    } else if (idMenu == 'APPBS_ADMIN_USR_LEG') {
        openTabAppbs_usuarios_log_legend(title, idMenu);
    } else if (idMenu == 'APPBS_ADMIN_SESSION') {
        openTabSesiones(title, idMenu);
    } else{
        Ext.example.msg("Error", "Opción de menú no implementada: "+idMenu);
    }
}

