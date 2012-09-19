package com.befasoft.common.actions;

import com.befasoft.common.beans.TreeNode;
import com.befasoft.common.beans.UTL_TREE_MENU;
import com.befasoft.common.beans.UTL_TREE_MENU_BASE;
import com.befasoft.common.beans.UTL_TREE_SUBMENU;
import com.befasoft.common.business.UserManager;
import com.befasoft.common.model.appbs.APPBS_MENU;
import com.befasoft.common.model.appbs.APPBS_MENU_PERFILES;
import com.befasoft.common.model.appbs.APPBS_PERFILES;
import com.befasoft.common.model.manager.APPBS_MENU_MANAGER;
import com.befasoft.common.model.manager.APPBS_MENU_PERFILES_MANAGER;
import com.befasoft.common.model.manager.APPBS_PERFILES_MANAGER;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.opensymphony.xwork2.Action;
import org.apache.commons.logging.LogFactory;
import org.svenson.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Devtools.
 * Date: 05/05/2011
 */
public class PerfilesAction extends BaseManagerAction {

    private List allMenusTree;
    private Long id_perfil;
    private String menus;
    private String tipo;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    public String execute() throws Exception {

        log = LogFactory.getLog(PerfilesAction.class);
        super.executeInit();

        if (Converter.isEmpty(action)) {
            return executeList();
        } else if ("save".equals(action)) {
            savePerfilMenus();
            return executeSave();
        } else if ("allMenusTree".equals(action) && id_perfil!= null ) {
            loadMenusTreeNodes();
            return "allMenusTree";
        }

        return Action.SUCCESS;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_PERFILES.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_PERFILES_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(tipo))
            filter.put("tipo", tipo);

        return filter;
    }


    /**
     * Carga los menus del perfil
     * @throws Exception Error en la DB
     */
    private void loadMenu() throws Exception {
        log.debug("loadMenu()");
        List<APPBS_MENU> menus = UserManager.loadSubmenu(null, Constants.APP_NAME, 2);
        List<UTL_TREE_MENU> tree = new ArrayList<UTL_TREE_MENU>();
        for (APPBS_MENU menu : menus) {
            UTL_TREE_MENU item = new UTL_TREE_MENU("["+menu.getId_menu()+"] - "+menu.getNombre());
            tree.add(item);
            List<APPBS_MENU> submenus = menu.getSubMenus();
            if (submenus.size() > 0) {
                List<UTL_TREE_MENU_BASE> children = new ArrayList<UTL_TREE_MENU_BASE>();
                item.setChildren(children);
                for (APPBS_MENU submenu : submenus) {
                    children.add(new UTL_TREE_SUBMENU("["+submenu.getId_menu()+"] - "+submenu.getNombre()));
                }
            }
        }
        bodyResult.setElements(tree);
    }

    private void loadMenusTreeNodes() throws Exception {
       List<APPBS_MENU> menusApp= APPBS_MENU_MANAGER.findMenusByAppId(Constants.APP_NAME, "MAIN_MENU");
       List<APPBS_MENU> perfilMenus = UserManager.loadSubmenu(null, Constants.APP_NAME, id_perfil);
       allMenusTree = loadTreeNodes(menusApp, perfilMenus);
    }



    private  List loadTreeNodes(List<APPBS_MENU> menusApp, List<APPBS_MENU> perfilMenus) throws Exception {
      List treeNodes = new ArrayList();
      for(APPBS_MENU menu: menusApp){
            TreeNode menuNode = new TreeNode();
            menuNode.setText(menu.getNombre());
            menuNode.setId(menu.getId_menu());

            List<APPBS_MENU> menuSubmenus= APPBS_MENU_MANAGER.findMenusByAppId(Constants.APP_NAME, menu.getId_menu());
            menu.setSubMenus(menuSubmenus);

            if (menu.getSubMenus().size() > 0) {
                List<APPBS_MENU> menuPerfilSubMenus = UserManager.loadSubmenu(menu, Constants.APP_NAME, id_perfil);
                List submenus= loadTreeNodes(menu.getSubMenus(), menuPerfilSubMenus);
                menuNode.setChildren(submenus);
            } else {

                menuNode.setLeaf(true);
                if(perfilMenus.contains(menu)){
                    menuNode.setChecked(true);
                }else{
                    menuNode.setChecked(false);
                }
            }

            treeNodes.add(menuNode);

      }
      return treeNodes;
    }

    public static void main(String[] args) throws Exception {
    }

    /*
     * Metodos Get/Set
     */


    public List getAllMenusTree() {
        return allMenusTree;
    }

    public void setAllMenusTree(List allMenusTree) {
        this.allMenusTree = allMenusTree;
    }

    public Long getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public void savePerfilMenus(){

       String idAplicacion = Constants.APP_NAME;

       List<HashMap> dataList = JSONParser.defaultJSONParser().parse( List.class, menus );
       APPBS_PERFILES perfil= APPBS_PERFILES_MANAGER.findByKey(id_perfil);

       List<APPBS_MENU_PERFILES> perfilMenus = APPBS_MENU_PERFILES_MANAGER.findByPerfil(id_perfil);

       for(APPBS_MENU_PERFILES asignedMenu : perfilMenus ){
           APPBS_MENU_PERFILES_MANAGER.delete(asignedMenu);
       }

       Map filters = null;

       for (HashMap dataRow : dataList) {

           String id_menu = (String)dataRow.get("id_menu");

           filters = new HashMap();
           filters.put("id_perfil", id_perfil);
           filters.put("id_aplicacion", idAplicacion);
           filters.put("id_menu", id_menu);

           APPBS_MENU_PERFILES asignedMenu= APPBS_MENU_PERFILES_MANAGER.findByKey(id_perfil, id_menu, id_menu);

           if(asignedMenu== null){
                //hay que asignar el menu al perfil
              asignedMenu= new APPBS_MENU_PERFILES();
              asignedMenu.setId_aplicacion(idAplicacion);
              asignedMenu.setId_menu(id_menu);
              asignedMenu.setId_perfil(id_perfil);
           }

           APPBS_MENU_PERFILES_MANAGER.save( asignedMenu );

           asignedMenu.setMenu( APPBS_MENU_MANAGER.findByKey(Constants.APP_NAME, id_menu) );
           // APPBS_MENU_MANAGER.findByKey(Constants.APP_NAME, id_menu);


           APPBS_MENU_PERFILES padreMenu=  APPBS_MENU_PERFILES_MANAGER.findByKey(id_perfil, idAplicacion, asignedMenu.getMenu().getId_padre());


           if(padreMenu == null){
               //hay que asignar el menu al perfil
              padreMenu= new APPBS_MENU_PERFILES();
              padreMenu.setId_aplicacion(idAplicacion);
              padreMenu.setId_menu(asignedMenu.getMenu().getId_padre());
              padreMenu.setId_perfil(id_perfil);

              APPBS_MENU_PERFILES_MANAGER.save( padreMenu );
           }


       }

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}