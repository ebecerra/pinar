package com.befasoft.common.beans;

public class SINCRO_ERROR {
    int result = 0;
    String table, field, sql_error;
    int row, index;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSql_error() {
        return sql_error;
    }

    public void setSql_error(String sql_error) {
        this.sql_error = sql_error;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void incrementRow() {
        row++;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void incrementIndex() {
        index++;
    }
}
