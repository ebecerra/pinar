package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.business.DBLogger;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.model.appbs.APPEX_IDIOMAS;
import com.befasoft.common.model.manager.APPBS_USUARIOS_MANAGER;
import com.befasoft.common.model.manager.APPEX_IDIOMAS_MANAGER;
import com.befasoft.common.tools.Converter;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 13/10/2011
 */
public class IdiomasAction extends BaseManagerAction {

    Long id_idioma;
    Boolean activo;


    /**
     * Metodo que se llama por defecto al ejecutar la accion
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    public String execute() throws Exception {
        super.execute();

        if ("change".equals(action)) {
            log.debug("Idioma anterior = "+ ActionContext.getContext().getLocale().getLanguage());
            APPEX_IDIOMAS idi = APPEX_IDIOMAS_MANAGER.findByKey(id_idioma);
            if (idi != null) {
                Locale loc = new Locale(idi.getIso_2().toLowerCase().trim());
                session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, loc);
                session.put("idioma", idi);
                session.put("id_idioma", idi.getId_idioma());
                ActionContext.getContext().setLocale(loc);
                log.debug("Idioma nuevo = "+ ActionContext.getContext().getLocale().getLanguage());
                if (user != null) {
                    // Almacena el cambio de idioma
                    APPBS_USUARIOS usuario = APPBS_USUARIOS_MANAGER.findByKey(user.getId_usuario());
                    if (usuario != null) {
                        usuario.setId_idioma(idi.getId_idioma());
                        APPBS_USUARIOS_MANAGER.save(usuario);
                    }
                }
            } else {
                log.error("No se encuentra el idioma "+id_idioma);
                DBLogger.log(DBLogger.LEVEL_ERROR, "IDI_NOTFOUND", "No se encuentra el idioma: " + id_idioma);
            }
            return "index";
        }

        return Action.SUCCESS;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_IDIOMAS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_IDIOMAS_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (!Converter.isEmpty(id_idioma))
            filter.put("id_idioma", id_idioma);
        if (activo != null)
            filter.put("activo", activo);

        return filter;
    }

    /*
     * Metodos Get/Set
     */

    public Long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(Long id_idioma) {
        this.id_idioma = id_idioma;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}


