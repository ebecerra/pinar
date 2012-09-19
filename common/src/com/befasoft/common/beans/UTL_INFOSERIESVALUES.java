package com.befasoft.common.beans;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Converter;
import com.googlecode.jsonplugin.annotations.JSON;

import java.util.Date;

public class UTL_INFOSERIESVALUES extends EntityBean {
    int mes;
    Date fecha;
    String nombre, dimension;
    double value1, value2, value3, value4, value5, value6, value7, value8, value9, value10;
    long id1, id2, id3, id4, id5, id6, id7, id8, id9, id10;
    String sent1, sent2, sent3, sent4, sent5, sent6, sent7, sent8, sent9, sent10;
    String tipo;

    public UTL_INFOSERIESVALUES() {
    }

    public UTL_INFOSERIESVALUES(String tipo) {
        this.tipo = tipo;
    }

    public UTL_INFOSERIESVALUES(int mes, String nombre) {
        this.mes = mes;
        this.nombre = nombre;
    }

    public UTL_INFOSERIESVALUES(Date fecha, String nombre) {
        this.fecha = fecha;
        this.nombre = nombre;
    }

    public UTL_INFOSERIESVALUES(String dimension, String nombre) {
        this.dimension = dimension;
        this.nombre = nombre;
        this.value1 = 0;
        this.value2 = 0;
        this.value3 = 0;
        this.value4 = 0;
        this.value5 = 0;
        this.value6 = 0;
        this.value7 = 0;
        this.value8 = 0;
        this.value9 = 0;
        this.value10 = 0;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getFecha_str() {
        return Converter.formatDate(fecha);
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public double getValue3() {
        return value3;
    }

    public void setValue3(double value3) {
        this.value3 = value3;
    }

    public double getValue4() {
        return value4;
    }

    public void setValue4(double value4) {
        this.value4 = value4;
    }

    public double getValue5() {
        return value5;
    }

    public void setValue5(double value5) {
        this.value5 = value5;
    }

    public double getValue6() {
        return value6;
    }

    public void setValue6(double value6) {
        this.value6 = value6;
    }

    public double getValue7() {
        return value7;
    }

    public void setValue7(double value7) {
        this.value7 = value7;
    }

    public double getValue8() {
        return value8;
    }

    public void setValue8(double value8) {
        this.value8 = value8;
    }

    public double getValue9() {
        return value9;
    }

    public void setValue9(double value9) {
        this.value9 = value9;
    }

    public double getValue10() {
        return value10;
    }

    public void setValue10(double value10) {
        this.value10 = value10;
    }

    public void setValue(int indx, double valor) {
        switch (indx) {
            case 1: value1 = valor; break;
            case 2: value2 = valor; break;
            case 3: value3 = valor; break;
            case 4: value4 = valor; break;
            case 5: value5 = valor; break;
            case 6: value6 = valor; break;
            case 7: value7 = valor; break;
            case 8: value8 = valor; break;
            case 9: value9 = valor; break;
            case 10: value10 = valor; break;
        }
    }

    public double getValueX(int indx) {
        double aux = 0;
        switch (indx) {
            case 1: aux = value1; break;
            case 2: aux = value2; break;
            case 3: aux = value3; break;
            case 4: aux = value4; break;
            case 5: aux = value5; break;
            case 6: aux = value6; break;
            case 7: aux = value7; break;
            case 8: aux = value8; break;
            case 9: aux = value9; break;
            case 10: aux = value10; break;
        }
        return aux;
    }

    public long getId1() {
        return id1;
    }

    public long getId2() {
        return id2;
    }

    public long getId4() {
        return id4;
    }

    public long getId3() {
        return id3;
    }

    public long getId5() {
        return id5;
    }

    public long getId6() {
        return id6;
    }

    public long getId7() {
        return id7;
    }

    public long getId9() {
        return id9;
    }

    public long getId8() {
        return id8;
    }

    public long getId10() {
        return id10;
    }

    public void setId(int indx, long valor) {
        switch (indx) {
            case 1: id1 = valor; break;
            case 2: id2 = valor; break;
            case 3: id3 = valor; break;
            case 4: id4 = valor; break;
            case 5: id5 = valor; break;
            case 6: id6 = valor; break;
            case 7: id7 = valor; break;
            case 8: id8 = valor; break;
            case 9: id9 = valor; break;
            case 10: id10 = valor; break;
        }
    }

    public String getSent1() {
        return sent1;
    }

    public String getSent2() {
        return sent2;
    }

    public String getSent3() {
        return sent3;
    }

    public String getSent4() {
        return sent4;
    }

    public String getSent5() {
        return sent5;
    }

    public String getSent6() {
        return sent6;
    }

    public String getSent7() {
        return sent7;
    }

    public String getSent8() {
        return sent8;
    }

    public String getSent9() {
        return sent9;
    }

    public String getSent10() {
        return sent10;
    }
    
    public void setSent(int indx, String valor) {
        switch (indx) {
            case 1: sent1 = valor; break;
            case 2: sent2 = valor; break;
            case 3: sent3 = valor; break;
            case 4: sent4 = valor; break;
            case 5: sent5 = valor; break;
            case 6: sent6 = valor; break;
            case 7: sent7 = valor; break;
            case 8: sent8 = valor; break;
            case 9: sent9 = valor; break;
            case 10: sent10 = valor; break;
        }
    }

    /**
     * Nombre de los campos que se van a utilizar en una exportación
     *
     * @return Lista de campos
     */
    @JSON(serialize = false)
    public String[] getExportFields() {
        if ("FECHA".equals(tipo))
            return new String[] {"fecha", "nombre", "value1" };
        else
            return new String[] {"nombre", "dimension", "value1"};
    }

    /**
     * Obtiene el ancho de los campos que se exportan
     * @return Lista con el ancho
     */
    @Override
    public int[] getColWidth() {
        return new int[] {30, 30, 20};
    }
}
