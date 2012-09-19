package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Converter;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "APPBS_PARAMETROS")
public class APPBS_PARAMETROS extends EntityBean {

    @Id
    @Column(name="ID_APLICACION")
    private String id_aplicacion;

    @Id
    @Column(name="ID_PARAMETRO")
    private String id_parametro;

  /*  @ManyToOne
    @JoinColumn(name="ID_APLICACION")
    private APPBS_APLICACIONES appbs_aplicaciones;
  */

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "MIN_INT")
    private Long min_int;

    @Column(name = "MAX_INT")
    private Long max_int;

    @Column(name = "MIN_FLOAT")
    private Float min_float;

    @Column(name = "MAX_FLOAT")
    private Float max_float;

    @Column(name = "MIN_DATE")
    private Date min_date;

    @Column(name = "MAX_DATE")
    private Date max_date;

    @Column(name = "CHECKRANGE")
    private String checkrange;

    @Column(name = "EDIT")
    private String edit;

    @Column(name = "VISIBLE")
    private String visible;

    @OneToMany()
    @JoinColumns({
            @JoinColumn(name = "ID_APLICACION", referencedColumnName = "ID_APLICACION"),
            @JoinColumn(name = "ID_PARAMETRO", referencedColumnName = "ID_PARAMETRO")
    })
    private List<APPBS_PARAMETROS_VALORES> valores = new ArrayList<APPBS_PARAMETROS_VALORES>();
    
    public boolean isVisible2() {
        return "S".equalsIgnoreCase(visible);
    }

    public void setVisible2(boolean visible2) {
        visible = visible2 ? "S": "N";
    }

    public boolean isEditable2() {
        return "S".equalsIgnoreCase(edit);
    }

    public void setEditable2(boolean editable2) {
        edit = editable2 ? "S": "N";
    }

    public boolean isCheckable2() {
        return "S".equalsIgnoreCase(checkrange);
    }

    public void setCheckable2(boolean checkable2) {
        checkrange = checkable2 ? "S": "N";
    }

    public String getId_aplicacion() {
        return id_aplicacion;
    }

    public void setId_aplicacion(String id_aplicacion) {
        this.id_aplicacion = id_aplicacion;
    }

    public String getId_parametro() {
        return id_parametro;
    }

    public void setId_parametro(String id_parametro) {
        this.id_parametro = id_parametro;
    }

/*    public APPBS_APLICACIONES getAppbs_aplicaciones() {
        return appbs_aplicaciones;
    }

    public void setAppbs_aplicaciones(APPBS_APLICACIONES appbs_aplicaciones) {
        this.appbs_aplicaciones = appbs_aplicaciones;
    }
*/
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

    public String getValor2() {
        if ("E".equals(tipo)) {
            for (APPBS_PARAMETROS_VALORES val : valores) {
                if (val.getId_valor() == Converter.getInt(valor))
                    return val.getNombre();
            }
        } else if ("P".equals(tipo))
            return "******";
        return valor;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getMin_int() {
        return min_int;
    }

    public void setMin_int(Long min_int) {
        this.min_int = min_int;
    }

    public Long getMax_int() {
        return max_int;
    }

    public void setMax_int(Long max_int) {
        this.max_int = max_int;
    }

    public Float getMin_float() {
        return min_float;
    }

    public void setMin_float(Float min_float) {
        this.min_float = min_float;
    }

    public Float getMax_float() {
        return max_float;
    }

    public void setMax_float(Float max_float) {
        this.max_float = max_float;
    }

    public Date getMin_date() {
        return min_date;
    }

    public void setMin_date(Date min_date) {
        this.min_date = min_date;
    }

    public Date getMax_date() {
        return max_date;
    }

    public void setMax_date(Date max_date) {
        this.max_date = max_date;
    }

    public String getCheckrange() {
        return checkrange;
    }

    public void setCheckrange(String checkrange) {
        this.checkrange = checkrange;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public boolean getEditable() {
        return "S".equalsIgnoreCase(edit) || "Y".equalsIgnoreCase(edit); 
    }

    public List<APPBS_PARAMETROS_VALORES> getValores() {
        return valores;
    }

    public void setValores(List<APPBS_PARAMETROS_VALORES> valores) {
        this.valores = valores;
    }

    @Override
    public String toString() {
        return "APPBS_PARAMETROS{" +
                "id_aplicacion='" + id_aplicacion + '\'' +
                ", id_parametro='" + id_parametro + '\'' +
//                ", appbs_aplicaciones=" + appbs_aplicaciones +
                ", nombre='" + nombre + '\'' +
                ", valor='" + valor + '\'' +
                ", tipo=" + tipo +
                ", min_int=" + min_int +
                ", max_int=" + max_int +
                ", min_float=" + min_float +
                ", max_float=" + max_float +
                ", min_date='" + min_date + '\'' +
                ", max_date='" + max_date + '\'' +
                ", checkrange='" + checkrange + '\'' +
                ", edit='" + edit + '\'' +
                ", visible='" + visible + '\'' +
                '}';
    }

    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
                "id_aplicacion",
                "id_parametro"
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
                "id_aplicacion", "id_parametro", "nombre", "valor", "tipo", "min_int", "max_int", 
                "min_float", "max_float", "min_date", "max_date", "checkrange", "edit", "visible",
                "visible2", "editable2","checkable2"
        };
    }
}
