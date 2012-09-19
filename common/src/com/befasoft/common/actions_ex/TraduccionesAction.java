package com.befasoft.common.actions_ex;

import com.befasoft.common.actions.BaseUploadManager;
import com.befasoft.common.model.appbs.APPEX_TRADUCCIONES;
import com.befasoft.common.model.manager.APPBS_TABLAS_MANAGER;
import com.befasoft.common.model.manager.APPEX_TRADUCCIONES_MANAGER;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.opensymphony.xwork2.Action;
import org.svenson.JSONParser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 10/10/2011
 */
public class TraduccionesAction extends BaseUploadManager {

    protected String tabla, rowkey, mode, path;
    protected Long id_idioma;

    /**
     * Metodo que se llama por defecto al ejecutar la accion
     *
     * @return Retorna el valor que se utiliza para enlazar con el result de STRUTS
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();

        if ("getTraducciones".equals(action)) {
            log.debug("getTraducciones("+tabla+", "+rowkey+", "+mode+")");
            Long id_tabla = APPBS_TABLAS_MANAGER.getTableId(tabla);
            bodyResult.setElements(APPEX_TRADUCCIONES_MANAGER.getTraducciones(id_tabla, rowkey));
            bodyResult.setTotalCount(bodyResult.getElements().size());
            result = Action.SUCCESS;
        }

        return result;
    }

    /**
     * Procesa el contenido del fichero
     * @return Verdadero si se proceso correctamente
     */
    @Override
    protected boolean processFile() throws Exception {
        Long id_tabla = APPBS_TABLAS_MANAGER.getTableId(tabla);
        APPEX_TRADUCCIONES trad = APPEX_TRADUCCIONES_MANAGER.findByKey(id_idioma, id_tabla, rowkey);
        if (trad == null) {
            trad = new APPEX_TRADUCCIONES();
            trad.setId_idioma(id_idioma);
            trad.setId_tabla(id_tabla);
            trad.setRowkey(rowkey);
        }
        trad.setTexto(uploadFileName);
        APPEX_TRADUCCIONES_MANAGER.save(trad);
        return true;
    }

    /**
     * Valores extras para retornar en el JSON
     * @return Cadena con los valores extra
     */
    @Override
    protected String getResultExtra() {
        return ""+id_idioma;
    }

    /**
     * Ejecuta guardar una entidad.
     *
     * @return Action result
     * @throws Exception Error
     */
    @Override
    public String executeSave() throws Exception {
        List<HashMap> dataList = JSONParser.defaultJSONParser().parse(List.class, this.dataToSave);
        if (dataList != null && dataList.size() > 0) {
            Map dataRow = dataList.get(0);
            Long id_tabla = APPBS_TABLAS_MANAGER.getTableId((String) dataRow.get("tabla"));
            String rowkey = (String) dataRow.get("rowkey");
            if (id_tabla != 0 && !Converter.isEmpty(rowkey)) {
                Iterator it = dataRow.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    if (key.startsWith("trad_")) {
                        Long id_idioma = Converter.getLong(key.substring(5));
                        String text = (String) dataRow.get(key);
                        APPEX_TRADUCCIONES trad = APPEX_TRADUCCIONES_MANAGER.findByKey(id_idioma, id_tabla, rowkey);
                        boolean insert = trad == null;
                        if (trad == null) {
                            trad = new APPEX_TRADUCCIONES();
                            trad.setId_idioma(id_idioma);
                            trad.setId_tabla(id_tabla);
                            trad.setRowkey(rowkey);
                        }
                        if (Converter.isEmpty(text)) {
                            if (!insert)
                                APPEX_TRADUCCIONES_MANAGER.delete(trad);
                        } else {
                            trad.setTexto(text);
                            APPEX_TRADUCCIONES_MANAGER.save(trad);
                        }
                    }
                }
            }
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
        return APPEX_TRADUCCIONES.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPEX_TRADUCCIONES_MANAGER.class;
    }

    @Override
    protected String getDestFileName() {
        return Constants.realAppPath+path+"/"+id_idioma+"/"+uploadFileName;
    }

    /*
     * Metodos Get/Set
     */

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(Long id_idioma) {
        this.id_idioma = id_idioma;
    }
}


