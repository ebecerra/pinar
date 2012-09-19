package com.befasoft.common.beans;

import java.util.ArrayList;
import java.util.List;

public class SINCRO_TABLE {
    String table, insertSt, deleteSt;
    Long version;

    List values, sql;

    public SINCRO_TABLE(String table, Long version) {
        this.table = table;
        this.version = version;
        this.values = new ArrayList();
        this.sql = new ArrayList();
    }

    public void add(List value) {
        values.add(value);
    }
    public void add(String value) {
        sql.add(value);
    }

    /*
     * Metodos Set/Get
     */
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getInsertSt() {
        return insertSt;
    }

    public void setInsertSt(String insertSt) {
        this.insertSt = insertSt;
    }

    public String getDeleteSt() {
        return deleteSt;
    }

    public void setDeleteSt(String deleteSt) {
        this.deleteSt = deleteSt;
    }

    public List getValues() {
        return values;
    }

    public void setValues(List values) {
        this.values = values;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List getSql() {
        return sql;
    }

    public void setSql(List sql) {
        this.sql = sql;
    }
}
