package com.befasoft.common.actions;

import com.befasoft.common.filters.FilterAdvanced;
import com.befasoft.common.filters.FilterInfo;
import com.befasoft.common.filters.FilterItem;
import com.befasoft.common.model.appbs.APPBS_LOG;
import com.befasoft.common.model.manager.APPBS_LOG_MANAGER;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.StringUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Devtools.
 * Date: 06/05/2011
 */
public class LogAction extends BaseManagerAction {

    // Filtros de la grid
    protected String tipo;
    protected Long nivel;
    protected Date fecha;

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_LOG.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_LOG_MANAGER.class;
    }

    @Override
    protected Map getFilter() {
        Map filter = new HashMap();
        filter.put("id_aplicacion", Constants.APP_NAME);
        if (!StringUtil.empty(tipo)) {
            filter.put("tipo", tipo+"%");
        }
        if (nivel != null && nivel > 0) {
            filter.put("nivel", nivel);
        }
        if (fecha != null) {
            FilterAdvanced fltAd = new FilterAdvanced();
            fltAd.addItem(new FilterItem("fecha", FilterInfo.FLT_GREATEQUAL, fecha), true);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            fltAd.addItem(new FilterItem("fecha", FilterInfo.FLT_LESSEQUAL, cal.getTime()), true);
            filter.put("fecha", fltAd);
        }

        return filter;
    }

    /*
    * Metodos Get/Set
    */

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
