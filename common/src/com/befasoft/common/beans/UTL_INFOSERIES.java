package com.befasoft.common.beans;

public class UTL_INFOSERIES {

    String nombre, valor, id_cadena;
    Long id;

    public UTL_INFOSERIES(int index, Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.valor = "value"+index;
    }

    public UTL_INFOSERIES(int index, String nombre) {
        this.nombre = nombre;
        this.valor = "value"+index;
    }

    public UTL_INFOSERIES(int index, String nombre, String id_cadena) {
        this.nombre = nombre;
        this.id_cadena = id_cadena;
        this.valor = "value"+index;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_cadena() {
        return id_cadena;
    }

    public void setId_cadena(String id_cadena) {
        this.id_cadena = id_cadena;
    }
}
