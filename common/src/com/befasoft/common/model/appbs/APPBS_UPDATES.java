package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 20/12/2011
 */
@Entity
@Table(name = "APPBS_UPDATES")
public class APPBS_UPDATES extends EntityBean {

    @Id
    @Column(name = "ID_UPDATE")
    private Long id_update;

  
    @Column(name = "FECHA")
    private Timestamp fecha;

    @Column(name = "DATE_PK2")
    private Date date_pk2;

    @Column(name = "OPERACION")
    private String operacion;

    @Column(name = "ID_TABLA")
    private Long id_tabla;

    @Column(name = "DATE_PK1")
    private Date date_pk1;

    @Column(name = "STR_PK3")
    private String str_pk3;

    @Column(name = "INT_PK2")
    private Long int_pk2;

    @Column(name = "INT_PK3")
    private Long int_pk3;

    @Column(name = "STR_PK2")
    private String str_pk2;

    @Column(name = "INT_PK1")
    private Long int_pk1;

    @Column(name = "DATE_PK3")
    private Date date_pk3;

    @Column(name = "STR_PK1")
    private String str_pk1;

    /**
     * Obtiene la condicion con las llaves
     * @param keys Lista de campos llaves
     * @return SQL para el WHERE
     */
    public String fillKeyValues(List<APPBS_CAMPOS> keys) {
        StringBuilder sql = new StringBuilder();
        int ipk = 1, spk = 1, dpk = 1;
        for (APPBS_CAMPOS campo : keys) {
            if (sql.length() > 0)
                sql.append(" and ");
            sql.append(campo.getNombre()).append(" = ");
            if ('I' == campo.getTipo()) {
                sql.append(getInt_pk(ipk));
                ipk++;
            } else if ('S' == campo.getTipo()) {
                sql.append(Converter.getSQLString(getStr_pk(spk)));
                spk++;
            } else {
                sql.append(Converter.getSQLDateTime(getDate_pk(dpk), true));
                dpk++;
            }
        }
        return sql.toString();
    }

    public Long getInt_pk(int indx) {
        switch (indx) {
            case 1: return int_pk1;
            case 2: return int_pk2;
            case 3: return int_pk3;
            default: return 0L;
        }
    }

    public String getStr_pk(int indx) {
        switch (indx) {
            case 1: return str_pk1;
            case 2: return str_pk2;
            case 3: return str_pk3;
            default: return "";
        }
    }

    public Date getDate_pk(int indx) {
        switch (indx) {
            case 1: return date_pk1;
            case 2: return date_pk2;
            case 3: return date_pk3;
            default: return null;
        }
    }

    /*
     * Set/Get Methods
     */

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Date getDate_pk2() {
        return date_pk2;
    }

    public void setDate_pk2(Date date_pk2) {
        this.date_pk2 = date_pk2;
    }
    
    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
    
    public Long getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(Long id_tabla) {
        this.id_tabla = id_tabla;
    }
    
    public Date getDate_pk1() {
        return date_pk1;
    }

    public void setDate_pk1(Date date_pk1) {
        this.date_pk1 = date_pk1;
    }
    
    public String getStr_pk3() {
        return str_pk3;
    }

    public void setStr_pk3(String str_pk3) {
        this.str_pk3 = str_pk3;
    }
    
    public Long getId_update() {
        return id_update;
    }

    public void setId_update(Long id_update) {
        this.id_update = id_update;
    }
    
    public Long getInt_pk2() {
        return int_pk2;
    }

    public void setInt_pk2(Long int_pk2) {
        this.int_pk2 = int_pk2;
    }
    
    public Long getInt_pk3() {
        return int_pk3;
    }

    public void setInt_pk3(Long int_pk3) {
        this.int_pk3 = int_pk3;
    }
    
    public String getStr_pk2() {
        return str_pk2;
    }

    public void setStr_pk2(String str_pk2) {
        this.str_pk2 = str_pk2;
    }
    
    public Long getInt_pk1() {
        return int_pk1;
    }

    public void setInt_pk1(Long int_pk1) {
        this.int_pk1 = int_pk1;
    }
    
    public Date getDate_pk3() {
        return date_pk3;
    }

    public void setDate_pk3(Date date_pk3) {
        this.date_pk3 = date_pk3;
    }
    
    public String getStr_pk1() {
        return str_pk1;
    }

    public void setStr_pk1(String str_pk1) {
        this.str_pk1 = str_pk1;
    }


    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_update"
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
            "fecha", "date_pk2", "operacion", "id_tabla", "date_pk1", "str_pk3", "id_update", "int_pk2", "int_pk3", "str_pk2", "int_pk1", "date_pk3", "str_pk1"
        };
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("ID_UPDATE: "); str.append(id_update);
        str.append(", ID_TABLA: "); str.append(id_tabla);
        str.append(", OPERACION: "); str.append(operacion);
        str.append(", INT_PK: "); str.append(int_pk1);
        str.append(", STR_PK: "); str.append(str_pk1);
        return str.toString();
    }
}

