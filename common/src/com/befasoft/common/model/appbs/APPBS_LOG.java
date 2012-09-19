package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Devtools.
 * Date: 07/04/2011
 */
@Entity
@Table(name = "APPBS_LOG")
public class APPBS_LOG extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_LOG")
    private Long id_log;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "ID_APLICACION")
    private String id_aplicacion;

    @Column(name = "NIVEL")
    private Long nivel;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "LOG")
    private String log;

    /*
     * Set/Get Methods
     */

    public Long getId_log() {
        return id_log;
    }

    public void setId_log(Long id_log) {
        this.id_log = id_log;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "id_log"
        };
    }

    /**
     * Nombre de todos los campos del bean
     *
     * @return Lista con todos los campos
     */
    @JSON(serialize = false)
    public String[] getAllFields() {
        return new String[]{
            "tipo", "id_aplicacion", "nivel", "fecha", "log"
        };
    }

}



