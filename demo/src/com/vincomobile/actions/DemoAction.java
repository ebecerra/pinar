package com.vincomobile.actions;

import com.befasoft.common.actions.BaseUploadManager;
import com.befasoft.common.business.DBLogger;
import com.befasoft.common.model.appbs.APPBS_USUARIOS;
import com.befasoft.common.model.appbs.APPEX_IDIOMAS;
import com.befasoft.common.model.manager.APPBS_USUARIOS_MANAGER;
import com.befasoft.common.model.manager.APPEX_IDIOMAS_MANAGER;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import com.vincomobile.Constants;

import java.util.*;

public abstract class DemoAction extends BaseUploadManager {

    protected String idioma_iso_code;
    protected Long id_idioma;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        id_idioma = (Long) session.get("id_idioma");
        if (id_idioma == null) {
            setNavLanguage();
        }
        idioma_iso_code = (String) session.get("idioma_iso_code");
        return result;
    }

    /**
     * Selecciona el idioma lo almacena en la session
     * @param idi Idioma seleccionado
     * @param saveUser Indica si se almacena el idioma en la informacion del usuario
     */
    protected void setIdioma(APPEX_IDIOMAS idi, boolean saveUser) {
        Locale loc = new Locale(idi.getIso_2());
        session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, loc);
        session.put("id_idioma", idi.getId_idioma());
        session.put("idioma_iso_code", idi.getIso_2().toUpperCase());
        ActionContext.getContext().setLocale(loc);
        if (user != null && saveUser) {
            // Almacena el cambio de idioma
            APPBS_USUARIOS usuario = APPBS_USUARIOS_MANAGER.findByKey(user.getId_usuario());
            if (usuario != null) {
                usuario.setParam_3(idi.getId_idioma().toString());
                APPBS_USUARIOS_MANAGER.save(usuario);
            }
        }
    }

    /**
     * Selecciona el idioma por defecto del navegador (se utiliza el siguiente orden de seleccion)
     *
     *    1. Primer idioma disponible de la lista de idiomas del navegador
     *    2. Idioma por defecto
     *    3. Primer idioma disponible
     */
    protected void setNavLanguage() {
        log.debug("setNavLanguage()");
        APPEX_IDIOMAS idi = null;
        Map flt = new HashMap();
        String lang = request.getHeader("accept-language");
        if (lang != null) {
            lang = lang.toLowerCase();
            StringTokenizer st = new StringTokenizer(lang, ",");
            while (st.hasMoreTokens()) {
                String idi_cod = st.nextToken().substring(0, 2);
                flt.put("iso_2", idi_cod);
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
                DBLogger.log(DBLogger.LEVEL_ERROR, "LOGIN_IDI_ERR", "No se encuentra el idioma por defecto: id_idioma = " + Constants.DEFAULT_ID_IDIOMA);
                log.error("No se encuentra el idioma por defecto");
            }
        }
        
        // Obtiene el primer idioma
        if (idi == null) {
            flt.clear();
            List<APPEX_IDIOMAS> idiomas = APPEX_IDIOMAS_MANAGER.findByFilter(flt);
            if (idiomas.size() > 0) {
                idi = idiomas.get(0);
            }
        }
        if (idi != null) {
            setIdioma(idi, false);
        }
    }

    /**
     * Selecciona el idioma por defecto del navegador (se utiliza el siguiente orden de seleccion)
     *
     *    1. Primer idioma disponible de la lista de idiomas del navegador
     *    2. Idioma por defecto
     *    3. Primer idioma disponible
     */
    protected void setRequestLanguage() {
        log.debug("setRequestLanguage()");
        APPEX_IDIOMAS idi = null;
        Map flt = new HashMap();
        String lang = request.getParameter("id_idioma");
        if (lang != null) {
            lang = lang.toLowerCase();
            StringTokenizer st = new StringTokenizer(lang, ",");
            while (st.hasMoreTokens()) {
                String idi_cod = st.nextToken().substring(0, 2);
                flt.put("iso_2", idi_cod);
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
                DBLogger.log(DBLogger.LEVEL_ERROR, "LOGIN_IDI_ERR", "No se encuentra el idioma por defecto: id_idioma = " + Constants.DEFAULT_ID_IDIOMA);
                log.error("No se encuentra el idioma por defecto");
            }
        }
        // Obtiene el primer idioma
        if (idi == null) {
            flt.clear();
            List<APPEX_IDIOMAS> idiomas = APPEX_IDIOMAS_MANAGER.findByFilter(flt);
            if (idiomas.size() > 0) {
                idi = idiomas.get(0);
            }
        }
        if (idi != null) {
            setIdioma(idi, false);
        }
    }


    @Override
    protected String getDestFileName() {
        return null;
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
}
