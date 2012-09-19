package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseManagerAction;
import com.befasoft.common.filters.FilterAdvanced;
import com.befasoft.common.filters.FilterInfo;
import com.befasoft.common.filters.FilterItem;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_PAISES;
import com.befasoft.common.model.manager.APPEX_PAISES_MANAGER;
import com.befasoft.common.model.manager.APPEX_TRADUCCIONES_MANAGER;
import com.befasoft.common.tools.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 07/11/2011
 */
public class PaisesAction extends BaseManagerAction {

    Boolean activo, geolocalizacion;

    /**
     * Indica si se puede borrar este registro
     *
     * @param values Map con los valores recibidos
     * @param bean Bean que se va a borrar
     * @return <code>null</code> Si se puede borrar este registro
     */
    @Override
    protected String canDelete(Map values, EntityBean bean)  throws Exception {
        APPEX_PAISES pais = (APPEX_PAISES) bean;
        APPEX_TRADUCCIONES_MANAGER.deleteTraducciones("APPEX_PAISES", Long.toString(pais.getId_pais()));
        return null;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPEX_PAISES.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_PAISES_MANAGER.class;
    }

    /**
     * Retorna el mapa de los parametros del filtro
     *
     * @return Filtros
     */
    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        if (activo != null)
            filter.put("activo", activo);
        if (geolocalizacion != null) {
            FilterAdvanced fltAd = new FilterAdvanced();
            fltAd.addItem(new FilterItem("geolocalizacion", FilterInfo.FLT_NOTNULL, ""), true);
            fltAd.addItem(new FilterItem("geolocalizacion", FilterInfo.FLT_NOTEQUAL, ""), true);
            filter.put("advanced", fltAd);
        }

        return filter;
    }

    /*
     * Metodos Get/Set
     */

    public Boolean getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(Boolean geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }
}


