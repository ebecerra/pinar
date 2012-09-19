package com.befasoft.common.beans;

public class UTL_INFO {
    int i_id;
    String id;
    String name, str1, str2, str3, str4;
    double value1, value2, value3, value4;

    /*
     * Contructores
     */

    public UTL_INFO(int id, String name, double value1, double value2, double value3, double value4) {
        this.i_id = id;
        this.id = Integer.toString(id);
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }

    public UTL_INFO(int id, String name, double value1, double value2, double value3) {
        this.i_id = id;
        this.id = Integer.toString(id);
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public UTL_INFO(int id, String name, double value1, double value2) {
        this.i_id = id;
        this.id = Integer.toString(id);
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
    }

    public UTL_INFO(int id, String name, double value1) {
        this.i_id = id;
        this.id = Integer.toString(id);
        this.name = name;
        this.value1 = value1;
    }

    public UTL_INFO(int id, String name, String str1, double value1) {
        this.i_id = id;
        this.id = Integer.toString(id);
        this.name = name;
        this.str1 = str1;
        this.value1 = value1;
    }

    public UTL_INFO(int id, String name) {
        this.i_id = id;
        this.id = Integer.toString(id);
        this.name = name;
    }

    public UTL_INFO(String id, String name, double value1, double value2) {
        this.id = id;
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
    }

    public UTL_INFO(String id, String name, String str1) {
        this.id = id;
        this.name = name;
        this.str1 = str1;
    }

    public UTL_INFO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /*
     * Metodos Get/Set
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getValue1() {
        return value1;
    }
    public void setValue1(double value1) {
        this.value1 = value1;
    }
    public void addValue1(double value1) {
        this.value1 += value1;
    }

    public double getValue2() {
        return value2;
    }
    public void setValue2(double value2) {
        this.value2 = value2;
    }
    public void addValue2(double value2) {
        this.value2 += value2;
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

    public String getStr1() {
        return str1;
    }
    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }
    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }
    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr4() {
        return str4;
    }
    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getI_id() {
        return i_id;
    }
    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

}
