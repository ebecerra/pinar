package com.befasoft.common.actions;

import com.befasoft.common.beans.SINCRO_ERROR;
import com.befasoft.common.beans.SINCRO_TABLE;
import com.befasoft.common.filters.FilterAdvanced;
import com.befasoft.common.filters.FilterInfo;
import com.befasoft.common.filters.FilterItem;
import com.befasoft.common.model.appbs.APPBS_TABLAS;
import com.befasoft.common.model.appbs.APPBS_UPDATES;
import com.befasoft.common.model.manager.APPBS_FOREINGKEYS_MANAGER;
import com.befasoft.common.model.manager.APPBS_PARAMETROS_MANAGER;
import com.befasoft.common.model.manager.APPBS_TABLAS_MANAGER;
import com.befasoft.common.model.manager.APPBS_UPDATES_MANAGER;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.Datetool;
import com.befasoft.common.tools.HibernateUtil;
import com.opensymphony.xwork2.Action;
import org.apache.catalina.util.Base64;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.svenson.JSONParser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.*;

public class DBSincroAction extends BaseManagerAction {

    Date lastUpdate;
    String tabla, pk, exportdb, filters, foto;
    String param_1, param_2, param_3, param_4, param_5;
    boolean indefinidas;
    int index, total;
    long senderId;

    /**
     * Obtiene la informacion para crear el esquema de la DB
     * @return Enlace con el Struts.xml
     */
    public String getSchema() {
        log.debug("getSchema("+tabla+")");
        Map flt = new HashMap();
        flt.put("creacion", true);
        if (!Converter.isEmpty(tabla))
            flt.put("nombre", tabla);
        setReturn(APPBS_TABLAS_MANAGER.findByFilter(flt, "ORDEN", "ASC"));
        bodyResult.put("db_version", APPBS_PARAMETROS_MANAGER.getLongParameter("APPBS_SYNC_DB_VERSION", 1));
        return Action.SUCCESS;
    }

    /**
     * Obtiene los datos de una tabla
     * @return Enlace con el Struts.xml
     */
    public String getTable() {
        log.debug("getTable("+tabla+", "+filters+", "+param_1+", "+param_2+", "+param_3+", "+param_4+", "+param_5+")");
        Map flt = new HashMap();
        flt.put("nombre", tabla);
        APPBS_TABLAS table = APPBS_TABLAS_MANAGER.findFirstByFilter(flt);
        if (table != null) {
            Date currentTime = HibernateUtil.getDatabaseDatetime();
            SINCRO_TABLE sincro = new SINCRO_TABLE(table.getNombre(), table.getVersion());
            APPBS_UPDATES_MANAGER.getTableUpdateSQL(sincro, table, filters, param_1, param_2, param_3, param_4, param_5);
            bodyResult.put("update", sincro);
            bodyResult.put("lastUpdate", currentTime);
        } else
            bodyResult.setSuccess(false);
        return Action.SUCCESS;
    }

    /**
     * Obtiene un listado de actualizacion
     * @return Enlace con el Struts.xml
     */
    public String getUpdate() {
        if (lastUpdate == null)
            lastUpdate = Datetool.getDate(1, 11, 2011);
        Date currentTime = HibernateUtil.getDatabaseDatetime();
        log.debug("getUpdate(" + lastUpdate + ", " + currentTime + ")");
        // Tablas que se sincronizan
        Map flt = new HashMap();
        flt.put("sincro", true);
        if (!Converter.isEmpty(tabla))
            flt.put("nombre", tabla);
        List<APPBS_TABLAS> tablas = APPBS_TABLAS_MANAGER.findByFilter(flt);
        List<SINCRO_TABLE> sincro_tables = new ArrayList<SINCRO_TABLE>();
        // Busca las actualizaciones de cada tabla
        for (APPBS_TABLAS table : tablas) {
            APPBS_UPDATES_MANAGER.initFields(table);
            flt.clear();
            FilterAdvanced fltAd = new FilterAdvanced();
            boolean greatEqual = false;
            if (!Converter.isEmpty(table.getExtra_update())) {
                if ("@NOW".equals(table.getExtra_update()) || "@TODAY".equals(table.getExtra_update())) {
                    Date today = currentTime;
                    if ("@TODAY".equals(table.getExtra_update())) {
                        today = Datetool.clearTime(today);
                    }
                    if (today.after(lastUpdate)) {
                        greatEqual = true;
                        fltAd.addItem(new FilterItem("fecha", FilterInfo.FLT_GREATEQUAL, new Timestamp(today.getTime())), true);
                    }
                }
            }
            if (!greatEqual)
                fltAd.addItem(new FilterItem("fecha", FilterInfo.FLT_GREATEQUAL, new Timestamp(lastUpdate.getTime())), true);
            fltAd.addItem(new FilterItem("fecha", FilterInfo.FLT_LESS, new Timestamp(currentTime.getTime())), true);
            flt.put("advanced", fltAd);
            flt.put("id_tabla", table.getId_tabla());
            List<APPBS_UPDATES> updates = APPBS_UPDATES_MANAGER.findByFilter(flt);
            // Genera el SQL de actualizacion
            SINCRO_TABLE sincro = new SINCRO_TABLE(table.getNombre(), table.getVersion());
            for (APPBS_UPDATES update : updates) {
                String sql = APPBS_UPDATES_MANAGER.getUpdateSQL(table, update);
                if (sql.length() > 0)
                    sincro.add(sql);
            }
            sincro_tables.add(sincro);
        }
        setReturn(sincro_tables);
        bodyResult.put("lastUpdate", currentTime);
        return Action.SUCCESS;
    }

    /**
     * Realiza la actualizacion de datos
     * @return Enlace con el Struts.xml
     */
    public String makeUpdate() {
        log.debug("makeUpdate("+senderId+", "+index+", "+total+")");
        bodyResult.put("index", index);
        HashMap values = (HashMap) session.get("mkUpdateValues_"+senderId);
        if (values == null) {
            values = new HashMap();
            session.put("mkUpdateValues_"+senderId, values);
        }
        values.put("v_"+index, exportdb);
        if (values.size() == total) {
            exportdb = "";
            for (int i = 1; i <= total; i++) {
                exportdb += values.get("v_"+i);
            }
            session.put("mkUpdateValues_"+senderId, null);
            BufferedReader br = new BufferedReader(new StringReader(exportdb));
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();
            SINCRO_ERROR result = null;
            try {
                result = APPBS_UPDATES_MANAGER.makeUpdate(session, br, indefinidas, "\t");
                bodyResult.setSuccess(result.getResult() == 0);
                log.debug("makeUpdate -> "+result.getResult());
                if (result.getResult() == 0)
                    tx.commit();
                else {
                    tx.rollback();
                    switch (result.getResult()) {
                        case 1:
                            bodyResult.setMessage(getText("appbs_sincro.error.1", new String[] { result.getTable() }));
                            break;
                        case 2:
                            bodyResult.setMessage(getText("appbs_sincro.error.2", new String[] { result.getTable(), result.getField() }));
                            break;
                        case 3:
                            bodyResult.setMessage(getText("appbs_sincro.error.3", new String[] { result.getTable(), ""+result.getRow(), result.getSql_error() }));
                            break;
                    }
                    bodyResult.setMessage(getText("appbs_sincro.error."+result.getResult(), new String[] { result.getTable(), ""+result.getRow(), result.getSql_error() }));
                }
            } catch (Exception e) {
                log.error("Error en la actualización");
                log.error("Error", e);
                tx.rollback();
                bodyResult.setMessage(getText("appbs_sincro.error.generic"));
                bodyResult.setSuccess(false);
            }
        }
        return Action.SUCCESS;
    }

    /**
     * Obtiene la actualización segun el foreingkey
     * @return Enlace con el Struts.xml
     */
    public String getFKData() {
        log.debug("getFKData("+tabla+", "+pk+")");
        List<HashMap> dataList = JSONParser.defaultJSONParser().parse(List.class, pk);
        List<SINCRO_TABLE> result = new ArrayList<SINCRO_TABLE>();
        Long id_tabla = APPBS_TABLAS_MANAGER.getTableId(tabla);
        if (!Converter.isEmpty(dataList) && id_tabla != 0)
            APPBS_FOREINGKEYS_MANAGER.getFKData(result, id_tabla, dataList.get(0));
        setReturn(result);
        return Action.SUCCESS;
    }

    /**
     * Obtiene la actualización segun el foreingkey
     * @return Enlace con el Struts.xml
     */
    public String getDB_version() {
        log.debug("getDB_version("+tabla+", "+pk+")");
        bodyResult.put("db_version", APPBS_PARAMETROS_MANAGER.getLongParameter("APPBS_SYNC_DB_VERSION", 1));
        return Action.SUCCESS;
    }

    /**
     * Codifica un JPG en base64
     * @return Enlace con el Struts.xml
     */
    public String encodeJPGBase64() {
        log.debug("encodeJPGBase64("+foto+")");
        File photo = new File(Constants.realAppPath+foto);
        bodyResult.put("name", photo.getName());
        RenderedImage image = null;
        try {
            image = ImageIO.read(photo);
            java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            String ss = "data:image/jpeg;base64,"+ Base64.encode(os.toByteArray());
            bodyResult.put("url", ss);
            bodyResult.put("load", true);
            //log.debug(ss);
        } catch (IOException e) {
            log.error("Error al cargar/codificar la foto: "+foto);
            bodyResult.put("load", false);
        }
        return Action.SUCCESS;
    }


    /**
     * Retorna la clase asociada al bean..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return null;
    }

    /**
     * Retorna la clase asociada al manager del bean..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return null;
    }

    /*
     * Metodos Set/Get
     */

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public boolean isIndefinidas() {
        return indefinidas;
    }

    public void setIndefinidas(boolean indefinidas) {
        this.indefinidas = indefinidas;
    }

    public String getExportdb() {
        return exportdb;
    }

    public void setExportdb(String exportdb) {
        this.exportdb = exportdb;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getParam_1() {
        return param_1;
    }

    public void setParam_1(String param_1) {
        this.param_1 = param_1;
    }

    public String getParam_2() {
        return param_2;
    }

    public void setParam_2(String param_2) {
        this.param_2 = param_2;
    }

    public String getParam_3() {
        return param_3;
    }

    public void setParam_3(String param_3) {
        this.param_3 = param_3;
    }

    public String getParam_4() {
        return param_4;
    }

    public void setParam_4(String param_4) {
        this.param_4 = param_4;
    }

    public String getParam_5() {
        return param_5;
    }

    public void setParam_5(String param_5) {
        this.param_5 = param_5;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public static void main(String[] args) throws Exception {
        DBSincroAction a = new DBSincroAction();
        a.setLogger();
/*
        a.getSchema();
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 11, 19);
        a.setLastUpdate(cal.getTime());
        a.getUpdate();
        a.setDataToSave(
            "escuela\n"+
            "Nombre\tDireccion\tTelefono\tlongitud\tlatitud\n"+
            "Escuela 1\tCalle 1\t91 000 001\t\t\n"+
            "Escuela 2\tCalle 2\t91 000 002\t\t12.89\n"
        );
        a.makeUpdate();
*/
        a.setTabla("recorridodato");
        a.getFKData();
    }
}
