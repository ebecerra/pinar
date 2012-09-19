package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "APPBS_CAMPOS")
public class APPBS_CAMPOS extends EntityBean {

    public APPBS_CAMPOS() {
    }

    public APPBS_CAMPOS(Long id_campo, String nombre, char tipo, String value) {
        this.id_campo = id_campo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.value = value;
    }

    @Id
    @Column(name="ID_CAMPO")
    private Long id_campo;

    @Id
    @Column(name="ID_TABLA")
    private Long id_tabla;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TIPO")
    private char tipo;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "LLAVE_PRIMARIA")
    private char llave_primaria;

    @Column(name = "ORG_NOMBRE")
    private String org_nombre;

    @Column(name = "ORG_CAMPO")
    private String org_campo;

    @Column(name = "ORG_FORMATO")
    private String org_formato;

    @Column(name = "ORG_TIPO")
    private Character org_tipo;

    @Column(name = "FUENTE")
    private Character fuente;

    @Column(name = "FUENTE_VALOR1")
    private String fuente_valor1;

    @Column(name = "FUENTE_VALOR2")
    private String fuente_valor2;

    @Column(name = "EXPORTABLE")
    private Boolean exportable;

    @Column(name = "IMPORTABLE")
    private Boolean importable;

    @Column(name = "PARAM_1")
    private String param_1;

    @Column(name = "PARAM_2")
    private String param_2;

    @Column(name = "PARAM_3")
    private String param_3;

    @Column(name = "PARAM_4")
    private String param_4;

    @Transient
    private String value;

    @Transient
    private String old_value;

    @Transient
    private Object org_value;

    public Long getId_campo() {
        return id_campo;
    }

    public void setId_campo(Long id_campo) {
        this.id_campo = id_campo;
    }

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

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public char getLlave_primaria() {
        return llave_primaria;
    }

    public void setLlave_primaria(char llave_primaria) {
        this.llave_primaria = llave_primaria;
    }

    public String getLowerName(){
        return nombre.toLowerCase();
    }

    public boolean isDateField(){
        return  tipo =='D';
    }

    public boolean isBooleanField(){
        return  tipo == 'B';
    }

    public boolean isPk() {
        return 'S' == llave_primaria;   
    }

    public String getOrg_nombre() {
        return org_nombre;
    }

    public void setOrg_nombre(String org_nombre) {
        this.org_nombre = org_nombre;
    }

    public String getOrg_campo() {
        return org_campo;
    }

    public void setOrg_campo(String org_campo) {
        this.org_campo = org_campo;
    }

    public String getOrg_select() {
        return org_campo == null ? org_nombre : org_campo;
    }

    public String getOrg_formato() {
        return org_formato;
    }

    public void setOrg_formato(String org_formato) {
        this.org_formato = org_formato;
    }

    public Character getOrg_tipo() {
        return org_tipo;
    }

    public void setOrg_tipo(Character org_tipo) {
        this.org_tipo = org_tipo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Character getFuente() {
        return fuente;
    }

    public void setFuente(Character fuente) {
        this.fuente = fuente;
    }

    public String getFuente_valor1() {
        return fuente_valor1;
    }

    public void setFuente_valor1(String fuente_valor1) {
        this.fuente_valor1 = fuente_valor1;
    }

    public String getFuente_valor2() {
        return fuente_valor2;
    }

    public void setFuente_valor2(String fuente_valor2) {
        this.fuente_valor2 = fuente_valor2;
    }

    public Boolean getExportable() {
        return exportable;
    }

    public void setExportable(Boolean exportable) {
        this.exportable = exportable;
    }

    public Boolean getImportable() {
        return importable;
    }

    public void setImportable(Boolean importable) {
        this.importable = importable;
    }

    public boolean isNumberfield(){
        return  tipo == 'I' || tipo == 'F';
    }

    /**
     * Obtiene el tipo Java
     * @return Nombre del tipo
     */
    public String getJavaType(){
        switch (tipo) {
            case 'S': return "String";
            case 'I': return "Long";
            case 'F': return "Double";
            case 'D': return "Date";
            case 'B': return "Boolean";
        }

        return "ERROR";
    }

    /**
     * Obtiene el valor para utilizar en una SQL
     * @return Valor SQL
     */
    public String getSQLValue() {
        switch (tipo) {
            case 'S': return Converter.getSQLString(value);
            case 'I': return Converter.getSQLInteger(value);
            case 'F': return Converter.getSQLFloat(Converter.getFloat(value));
            case 'D':
                if (Converter.isEmpty(value))
                    return "null";
                String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATE_INPUT_FORMAT : org_formato;
                if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
                    return "to_date('"+value+"', '"+format+"')";
                } else {
                    return Converter.getSQLDate(Converter.getDate(value, format), Constants.SQL_DATE_OUTPUT_FORMAT, true);
                }
            case 'T':
                if (Converter.isEmpty(value))
                    return "null";
                format = Converter.isEmpty(org_formato) ? Constants.SQL_DATETIME_INPUT_FORMAT : org_formato;
                if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE) {
                    return "to_date('"+value+"', '"+format+"')";
                } else {
                    return Converter.getSQLDate(Converter.getDate(value, format), Constants.SQL_DATETIME_OUTPUT_FORMAT, true);
                }
            case 'B': return Converter.getSQLBoolean(Converter.getBoolean(value));
        }
        return "null";
    }

    /**
     * Obtiene el valor para exportar (Mobile)
     * @return Valor para el export
     */
    public String getSQLExportValue() {
        switch (tipo) {
            case 'S': return Converter.getSQLString(value);
            case 'I': return Converter.getSQLInteger(value);
            case 'F': return Converter.getSQLFloat(Converter.getFloat(value));
            case 'D':
                if (Converter.isEmpty(value))
                    return "null";
                String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATE_INPUT_FORMAT : org_formato;
                return Converter.getSQLDate(Converter.getDate(value, format), format, true);
            case 'T':
                if (Converter.isEmpty(value))
                    return "null";
                format = Converter.isEmpty(org_formato) ? Constants.SQL_DATETIME_INPUT_FORMAT : org_formato;
                return Converter.getSQLDate(Converter.getDate(value, format), format, true);
            case 'B': return Converter.getSQLBoolean(Converter.getBoolean(value));
        }
        return "null";
    }

    /**
     * Retorna un objeto con el valor del campo segun su tipo
     * @param val Valor a convertir
     * @return Objeto con el valor
     */
    protected Object getObjectValue(String val) {
        switch (tipo) {
            case 'S': return val;
            case 'I': return Converter.getInt(val);
            case 'F': return Converter.getFloat(val);
            case 'D':
                if (Converter.isEmpty(val))
                    return null;
                String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATE_INPUT_FORMAT : org_formato;
                return Converter.getDate(val, format);
            case 'T':
                if (Converter.isEmpty(val))
                    return null;
                format = Converter.isEmpty(org_formato) ? Constants.SQL_DATETIME_INPUT_FORMAT : org_formato;
                return Converter.getDate(val, format);
            case 'B': return Converter.getBoolean(val);
        }
        return null;
    }

    public Object getObjectValue() {
        return getObjectValue(value);
    }

    public Object getSqliteValue() {
        if (value == null)
            return null;
        char c_tipo = org_tipo != null ? org_tipo : tipo;
        switch (c_tipo) {
            case 'S': return value;
            case 'I': return Converter.getInt("true".equals(value) ? "1" : value);
            case 'F': return Converter.getFloat(value);
            case 'D':
                if (Converter.isEmpty(value))
                    return null;
                String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATE_INPUT_FORMAT : org_formato;
                return Converter.getDate(value, format);
            case 'T':
                if (Converter.isEmpty(value))
                    return null;
                format = Converter.isEmpty(org_formato) ? Constants.SQL_DATETIME_INPUT_FORMAT : org_formato;
                return Converter.getDate(value, format);
            case 'B': return Converter.getBoolean(value) ? 1 : 0;
        }
        return null;
    }

    public Object getObjectOld_value() {
        return getObjectValue(old_value);
    }

    public String getSQLValue(String value) {
        this.value = value;
        return getSQLValue();
    }

    public String getCapitalizeName(){
        return Converter.capitalize(nombre.toLowerCase());
    }

    public void setValue(Object value) {
        org_value = value;
        if (value == null) {
            this.value = null;
            return;
        }
        if (tipo == 'D') {
            String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATE_INPUT_FORMAT : org_formato;
            this.value = Converter.getSQLDateTime((Date) value, format, false);
        } else if (tipo == 'T') {
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE)
                this.value = (String) value;
            else {
                String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATETIME_INPUT_FORMAT : org_formato;
                this.value = Converter.getSQLDateTime((Date) value, format, false);
            }
        } else if (tipo == 'B') {
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE)
                this.value = ((BigDecimal) value).intValue() == 1 ? "1" : "0";
            else
                this.value = (Boolean) value ? "1" : "0";
        }
        else
            this.value = value.toString();
    }

    public void setOld_value(Object value) {
        if (value == null) {
            this.old_value = null;
            return;
        }
        if (tipo == 'D') {
            String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATE_INPUT_FORMAT : org_formato;
            this.old_value = Converter.getSQLDateTime((Date) value, format, false);
        } else if (tipo == 'T') {
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE)
                this.old_value = (String) value;
            else {
                String format = Converter.isEmpty(org_formato) ? Constants.SQL_DATETIME_INPUT_FORMAT : org_formato;
                this.old_value = Converter.getSQLDateTime((Date) value, format, false);
            }
        } else if (tipo == 'B') {
            if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE)
                this.old_value = ((BigDecimal) value).intValue() == 1 ? "1" : "0";
            else
                this.old_value = (Boolean) value ? "1" : "0";
        }
        else
            this.old_value = value.toString();
    }

    public Object getOrg_value() {
        return org_value;
    }

    /**
    * Nombre de los campos Key del bean
    *
    * @return Lista de campos llave
    */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_tabla", "id_campo"
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
            "id_campo", "id_tabla", "org_campo", "orden", "llave_primaria", "param_1", "param_2", "param_3", "param_4", "org_nombre", "nombre",
            "tipo", "org_formato", "org_tipo", "fuente_valor1", "fuente_valor2", "fuente", "exportable", "importable"
        };
    }
}
