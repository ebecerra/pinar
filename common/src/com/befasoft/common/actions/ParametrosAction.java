package com.befasoft.common.actions;

import com.befasoft.common.model.appbs.APPBS_PARAMETROS;
import com.befasoft.common.model.appbs.APPBS_PARAMETROS_VALORES;
import com.befasoft.common.model.manager.APPBS_PARAMETROS_MANAGER;
import com.befasoft.common.model.manager.APPBS_PARAMETROS_VALORES_MANAGER;
import com.befasoft.common.tools.AlgorithmSHA1;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Action;
import org.svenson.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Devtools.
 * Date: 05/05/2011
 */
public class ParametrosAction extends BaseManagerAction {
    String id_aplicacion, id_parametro;

    /**
     * Ejecuta guardar una entidad.
     *
     * @return Action result
     * @throws Exception Error
     */
    public String executeSave() throws Exception {
        if ("save_enum".equals(action)) {
            if (!Converter.isEmpty(dataToSave) && !"[]".equals(dataToSave)) {
                List<HashMap> maps = injectParams(dataToSave);
                updateBeanElements(new APPBS_PARAMETROS_VALORES(), maps);
            }

            if (!Converter.isEmpty(elementsToDelete)) {
                List<HashMap> maps = injectParams(elementsToDelete);
                deleteBeanElements(new APPBS_PARAMETROS_VALORES(), maps);
            }
        } else
            super.executeSave();
        if (com.befasoft.common.tools.Constants.confLoader != null)
            com.befasoft.common.tools.Constants.confLoader.loadDBParams();
        return Action.SUCCESS;
    }

    private List<HashMap> injectParams(String data) {
        List<HashMap> dataList = JSONParser.defaultJSONParser().parse(List.class, data);

        // iteramos por los elementos a Actualizar
        for (HashMap dataRow : dataList) {
            dataRow.put("id_aplicacion", id_aplicacion);
            dataRow.put("id_parametro", id_parametro);
        }

        return dataList;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_PARAMETROS.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_PARAMETROS_MANAGER.class;
    }

    @Override
    protected Map<String, String> getFilter() {
        Map<String, String> filter = new HashMap<String, String>();
        filter.put("id_aplicacion", Constants.APP_NAME);
        if (!"MANAGER".equals(user.getPerfil_tipo()))
            filter.put("visible", "S");
        return filter;
    }

    /*
     * Metodos Get/Set
     */
    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public String getId_parametro() {
        return id_parametro;
    }

    public void setId_parametro(String id_parametro) {
        this.id_parametro = id_parametro;
    }

}
