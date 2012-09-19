package com.befasoft.common.business;

import com.befasoft.common.model.appbs.APPBS_MENU;
import com.befasoft.common.model.manager.APPBS_MENU_MANAGER;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager para la gestion de usuarios
 */
public class UserManager {

    private static Log log = LogFactory.getLog(UserManager.class);

    /**
     * Carga de forma recursiva los submenus
     * @param menu Elemento del menu
     * @param id_aplicacion Id. de la aplicacion
     * @param id_perfil Perfil del usuario
     * @throws Exception Excepcion general
     * @return Lista con los items del menu
     */
    public static List<APPBS_MENU> loadSubmenu(APPBS_MENU menu, String id_aplicacion, long id_perfil) throws Exception {
        if (menu == null)
            log.debug("loadSubmenu(MAIN_MENU, "+id_aplicacion+", "+id_perfil+")");
        else
            log.debug("loadSubmenu("+menu.getId_menu()+", "+id_aplicacion+", "+id_perfil+")");
        if (menu == null) {
            List<APPBS_MENU> mainMenu = APPBS_MENU_MANAGER.findSubmenu(id_aplicacion, "MAIN_MENU", id_perfil);
            for (APPBS_MENU mnu : mainMenu) {
                mnu.setSubMenus(loadSubmenu(mnu, id_aplicacion, id_perfil));
            }
            return mainMenu;
        } else {
            return APPBS_MENU_MANAGER.findSubmenu(id_aplicacion, menu.getId_menu(), id_perfil);
        }
    }

    public static void main(String[] args) throws Exception {
        List<APPBS_MENU> menus = loadSubmenu(null, "CLINICA", 1);
        System.out.println("menus = " + menus);
    }
}
