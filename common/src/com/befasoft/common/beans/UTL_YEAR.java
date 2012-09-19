package com.befasoft.common.beans;

/**
 * Date: 26-may-2005
 * Time: 22:45:17
 */
public class UTL_YEAR {
    int cod, mes;
    String nom;

    public UTL_YEAR(int cod) {
        this.cod = cod;
        nom = Integer.toString(cod);
    }

    /*
     * Metodos Get/Set
     */
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
}
