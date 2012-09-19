package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Devtools.
 * Date: 22/09/2011
 */
@Entity
@Table(name = "APPEX_TRADUCCIONES")
public class APPEX_TRADUCCIONES extends EntityBean {

    @Id
    @Column(name = "ROWKEY")
    private String rowkey;

    @Id
    @Column(name = "ID_IDIOMA")
    private Long id_idioma;

    @Id
    @Column(name = "ID_TABLA")
    private Long id_tabla;


    @Column(name = "TEXTO")
    private String texto;

    @Transient
    private String idioma;

    /*
     * Set/Get Methods
     */
    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }
    public void setROWKEY(String rowkey) {
        this.rowkey = rowkey;
    }

    public Long getId_idioma() {
        return id_idioma;
    }

    public void setId_idioma(Long id_idioma) {
        this.id_idioma = id_idioma;
    }
    public void setID_IDIOMA(Integer id_idioma) {
        this.id_idioma = id_idioma.longValue();
    }
    public void setID_IDIOMA(BigDecimal id_idioma) {
        this.id_idioma = id_idioma.longValue();
    }
    public void setID_IDIOMA(BigInteger id_idioma) {
        this.id_idioma = id_idioma.longValue();
    }

    public Long getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(Long id_tabla) {
        this.id_tabla = id_tabla;
    }
    public void setID_TABLA(Integer id_tabla) {
        this.id_tabla = id_tabla.longValue();
    }
    public void setID_TABLA(BigDecimal id_tabla) {
        this.id_tabla = id_tabla.longValue();
    }
    public void setID_TABLA(BigInteger id_tabla) {
        this.id_tabla = id_tabla.longValue();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    public void setTEXTO(String texto) {
        this.texto = texto;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public void setNOMBRE(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "rowkey", "id_idioma", "id_tabla"
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
            "rowkey", "id_idioma", "id_tabla", "texto"
        };
    }

}

