package com.befasoft.common.model.appbs;

import com.befasoft.common.business.ActionTrigger;
import com.befasoft.common.business.DBLogger;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.googlecode.jsonplugin.annotations.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APPBS_TABLAS")
public class APPBS_TABLAS extends EntityBean implements ActionTrigger {

    protected static Log log = LogFactory.getLog(APPBS_TABLAS.class);

    @Id
    @Column(name = "ID_TABLA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id_tabla;

    @Column(name = "NOMBRE")
    protected String nombre;

    @Column(name = "ORIGEN")
    protected String origen;

    @Column(name = "REPLICA")
    protected String replica;

    @Column(name = "SINCRO")
    protected Boolean sincro;

    @Column(name = "CREACION")
    protected Boolean creacion;

    @Column(name = "IMPORTABLE")
    protected Boolean importable;

    @Column(name = "EXPORTABLE")
    protected Boolean exportable;

    @Column(name = "ORDEN")
    protected Long orden;

    @Column(name = "VERSION")
    protected Long version;

    @Column(name = "XSELECT")
    protected String xselect;

    @Column(name = "XFROM")
    protected String xfrom;

    @Column(name = "XWHERE")
    protected String xwhere;

    @Column(name = "XGROUP")
    protected String xgroup;

    @Column(name = "EXTRA_WHERE")
    protected String extra_where;

    @Column(name = "EXTRA_SELECT")
    protected String extra_select;

    @Column(name = "EXTRA_UPDATE")
    protected String extra_update;

    @Column(name = "DELETE_WHERE")
    protected String delete_where;

    @Column(name = "TRIGGERS")
    protected String triggers;

    @Column(name = "PARAM_1")
    protected String param_1;

    @Column(name = "PARAM_2")
    protected String param_2;

    @Column(name = "PARAM_3")
    protected String param_3;

    @Column(name = "PARAM_4")
    protected String param_4;

    @OneToMany
    @JoinColumn(name = "ID_TABLA", insertable = false, updatable = false)
    @org.hibernate.annotations.OrderBy(clause="ORDEN")
    protected List<APPBS_CAMPOS> campos;

    @Transient
    @JSON(serialize = false)
    protected List<APPBS_CAMPOS> keys;

    @Transient
    @JSON(serialize = false)
    protected List<APPBS_CAMPOS> nokeys;

    @Transient
    protected String campos_names;

    @Transient
    protected String campos_select;

    @Transient
    ActionTrigger triggerClass = null;

    private void loadTriggerClass() {
        if (triggerClass == null && !Converter.isEmpty(triggers)) {
            ClassLoader classLoader = APPBS_TABLAS.class.getClassLoader();
            try {
                Class trgClass = classLoader.loadClass(triggers);
                triggerClass = (ActionTrigger) trgClass.newInstance();
            } catch (Exception e) {
                log.error("Error al cargar la clase con el triggers: "+e.getMessage());
                DBLogger.log(DBLogger.LEVEL_ERROR, "COMMON_TABLE", "Error al cargar la clase con el triggers: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean hasTriggers() {
        return triggers != null;
    }

    /**
     * Lista los campos que no son llaves
     * @return Lista de campos
     */
    public List<APPBS_CAMPOS> getNoKeyCampos() {
        List<APPBS_CAMPOS> results = new ArrayList<APPBS_CAMPOS>();
        if (campos != null) {
            for (APPBS_CAMPOS campo : campos) {
                if (campo.getLlave_primaria() == 'N') {
                    results.add(campo);
                }
            }
        }
        return results;
    }

    public List<APPBS_CAMPOS> getNokeys() {
        if (nokeys == null)
            nokeys = getNoKeyCampos();
        return nokeys;
    }

    public void setNokeys(List<APPBS_CAMPOS> nokeys) {
        this.nokeys = nokeys;
    }

    /**
     * Lista los campos llaves
     * @return Lista de campos
     */
    public List<APPBS_CAMPOS> getKeyCampos() {
        List<APPBS_CAMPOS> results = new ArrayList<APPBS_CAMPOS>();

        if (campos != null) {
            for (APPBS_CAMPOS campo : campos) {
                if (campo.getLlave_primaria() == 'S') {
                    results.add(campo);
                }
            }
        }

        return results;
    }

    public List<APPBS_CAMPOS> getKeys() {
        if (keys == null)
            keys = getKeyCampos();
        return keys;
    }

    public void setKeys(List<APPBS_CAMPOS> keys) {
        this.keys = keys;
    }

    /**
     * Obtiene los nombre de los campos reales separados por comas
     * @return Lista de nombres reales
     */
    public String getCampos_names() {
        if (campos_names == null) {
            campos_names = "";
            if (campos != null) {
                for (APPBS_CAMPOS campo : campos) {
                    if (campo.getFuente() != null && campo.getFuente() == 'B' && campo.getExportable() != null && campo.getExportable()) {
                        if (campos_names.length() > 0)
                            campos_names += ", ";
                        campos_names += campo.getNombre();
                    }
                }
            }
        }
        return campos_names;
    }

    public void setCampos_names(String campos_names) {
        this.campos_names = campos_names;
    }

    public int getCampos_names_count() {
        int result = 0;
        if (campos != null) {
            for (APPBS_CAMPOS campo : campos) {
                if (campo.getFuente() != null && campo.getFuente() == 'B' && campo.getExportable() != null && campo.getExportable()) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * Obtiene los nombre de los campos reales separados por comas
     * @return Lista de nombres reales
     */
    public String getCampos_select() {
        if (campos_select == null) {
            campos_select = "";
            if (campos != null) {
                for (APPBS_CAMPOS campo : campos) {
                    if (campo.getFuente() != null && campo.getFuente() == 'B' && campo.getExportable() != null && campo.getExportable()) {
                        if (campos_select.length() > 0)
                            campos_select += ", ";
                        if ('T' == campo.getTipo() && Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
                            campos_select += "TO_CHAR("+campo.getNombre()+", '"+campo.getParam_1()+"')";
                        } else
                            campos_select += campo.getNombre();
                    }
                }
            }
        }
        return campos_select;
    }

    public void setCampos_select(String campos_select) {
        this.campos_select = campos_select;
    }

    /**
     * Lista de todos los nombres separados por comas
     * @return Lista de todos los nombres (Primero los campoos reales)
     */
    public String getAll_names() {
        String all_names = getCampos_names();
        if (campos != null) {
            for (APPBS_CAMPOS campo : campos) {
                if (campo.getFuente() != null && campo.getFuente() != 'B' && campo.getExportable() != null && campo.getExportable()) {
                    if (all_names.length() > 0)
                        all_names += ", ";
                    all_names += campo.getNombre();
                }
            }
        }
        return all_names;
    }

    /**
     * Lista de todos los parametros (?) correspondientes a los nombres separados por comas
     * @return Lista de parametros (?)
     */
    public String getAll_names_params() {
        int count = getCampos_names_count();
        for (APPBS_CAMPOS campo : campos) {
            if (campo.getFuente() != null && campo.getFuente() != 'B' && campo.getExportable() != null && campo.getExportable()) {
                count++;
            }
        }
        String all_params = "";
        for (int i = 0; i < count; i++) {
            if (i != 0)
                all_params += ", ";
            all_params += "?";
        }
        return all_params;
    }

    /**
     * Obtiene un campo por el nombre
     * @param nombre Nombre del campo
     * @return Informacion del campo
     */
    public APPBS_CAMPOS getCampo(String nombre) {
        if (campos != null) {
            for (APPBS_CAMPOS campo : campos) {
                if (campo.getNombre().equals(nombre)) {
                    return campo;
                }
            }
        }
        return null;
    }

    /**
     * Retorna una cadena con los valores de los campos
     * @return Informacion de los  campos
     */
    public String printCamposValores() {
        StringBuilder text = new StringBuilder();
        if (campos != null) {
            for (APPBS_CAMPOS campo : campos) {
                text.append(campo.getNombre()).append("=").append(campo.getValue()).append(", ");
            }
        }
        return text.toString();
    }

    /**
     * Indica si es una llave simple
     * @return Verdadero si es una llave simple
     */
    public boolean isSimpleKey() {
       return getKeyCampos().size() == 1;
    }

    /*
     * Metodos de capitalizacion del nombre
     */
    public String getLowerName(){
        return nombre.toLowerCase();
    }

    public String getUpperCaseName(){
        return nombre.toUpperCase();
    }

    public String getCapitalizeName(){
        return Converter.capitalize(nombre.toLowerCase());
    }

    /*
     * Metodos Get/Set
     */
    public Long getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(Long id_tabla) {
        this.id_tabla = id_tabla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getReplica() {
        return replica;
    }

    public void setReplica(String replica) {
        this.replica = replica;
    }

    public String getExtra_where() {
        return extra_where;
    }

    public void setExtra_where(String extra_where) {
        this.extra_where = extra_where;
    }

    public String getExtra_select() {
        return extra_select;
    }

    public void setExtra_select(String extra_select) {
        this.extra_select = extra_select;
    }

    public String getExtra_update() {
        return extra_update;
    }

    public void setExtra_update(String extra_update) {
        this.extra_update = extra_update;
    }

    public String getDelete_where() {
        return delete_where;
    }

    public void setDelete_where(String delete_where) {
        this.delete_where = delete_where;
    }

    public String getXselect() {
        return xselect;
    }

    public void setXselect(String xselect) {
        this.xselect = xselect;
    }

    public String getXfrom() {
        return xfrom;
    }

    public void setXfrom(String xfrom) {
        this.xfrom = xfrom;
    }

    public String getXwhere() {
        return xwhere;
    }

    public void setXwhere(String xwhere) {
        this.xwhere = xwhere;
    }

    public String getXgroup() {
        return xgroup;
    }

    public void setXgroup(String xgroup) {
        this.xgroup = xgroup;
    }

    public String getTriggers() {
        return triggers;
    }

    public void setTriggers(String triggers) {
        this.triggers = triggers;
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

    public Boolean getSincro() {
        return sincro;
    }

    public void setSincro(Boolean sincro) {
        this.sincro = sincro;
    }

    public Boolean getImportable() {
        return importable;
    }

    public void setImportable(Boolean importable) {
        this.importable = importable;
    }

    public Boolean getExportable() {
        return exportable;
    }

    public void setExportable(Boolean exportable) {
        this.exportable = exportable;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Boolean getCreacion() {
        return creacion;
    }

    public void setCreacion(Boolean creacion) {
        this.creacion = creacion;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<APPBS_CAMPOS> getCampos() {
        return campos;
    }

    public void setCampos(List<APPBS_CAMPOS> campos) {
        this.campos = campos;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_tabla"
        };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista con todos los campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[] {
            "id_tabla", "nombre", "orden", "creacion", "version", "origen", "sincro", "replica", "exportable", "importable", "triggers",
            "xfrom", "xwhere", "xselect", "xgroup", "extra_where", "extra_select", "extra_update", "param_1", "param_2", "param_3", "param_4"
        };
    }

    @Override
    public String toString() {
        return "APPBS_TABLAS{" +
                "id_tabla=" + id_tabla +
                ", nombre='" + nombre + '\'' +
                ", origen='" + origen + '\'' +
                ", replica='" + replica + '\'' +
                ", xselect='" + xselect + '\'' +
                ", xfrom='" + xfrom + '\'' +
                ", xwhere='" + xwhere + '\'' +
                ", xgroup='" + xgroup + '\'' +
                ", extra_where='" + extra_where + '\'' +
                ", extra_select='" + extra_select + '\'' +
                '}';
    }

    /**
     * Triggers que se llaman en una operacion de UPDATE
     *
     * @param session Session Hibernate
     * @param fields  Campos (value = nuevo valor, old_value = valores almacenados en DB)
     * @param nombre  Nombre de la tabla
     */
    public boolean beforeUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        loadTriggerClass();

        return !hasTriggers() || this.triggerClass.beforeUpdate(session, fields, nombre);
    }

    public void afterUpdate(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        loadTriggerClass();

        if (hasTriggers()) {
            this.triggerClass.afterUpdate(session, fields, nombre);
        }
    }

    /**
     * Triggers que se llaman en una operacion de INSERT
     *
     * @param session Session Hibernate
     * @param fields  Campos (value = nuevo valor, old_value = null)
     * @param nombre  Nombre de la tabla
     */
    public boolean beforeInsert(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        loadTriggerClass();

        return !hasTriggers() || this.triggerClass.beforeInsert(session, fields, nombre);
    }

    public void afterInsert(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        loadTriggerClass();

        if (hasTriggers()) {
            this.triggerClass.afterInsert(session, fields, nombre);
        }
    }

    /**
     * Triggers que se llaman en una operacion de DELETE
     *
     * @param session Session Hibernate
     * @param fields  Campos (value = nuevo valor, old_value = null)
     * @param nombre  Nombre de la tabla
     */
    public boolean beforeDelete(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        loadTriggerClass();

        return !hasTriggers() || this.triggerClass.beforeDelete(session, fields, nombre);
    }

    public void afterDelete(Session session, List<APPBS_CAMPOS> fields, String nombre) {
        loadTriggerClass();

        if (hasTriggers()) {
            this.triggerClass.afterDelete(session, fields, nombre);
        }
    }
}
