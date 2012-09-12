package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.tools.Converter;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 06/04/2011
 */
@Entity
@Table(name = "ARTICULOS")
public class ARTICULOS extends EntityBean {

    @Id
    @Column(name = "ITEM_ID")
    private Long item_id;

    @Column(name = "ID_FAMILIA")
    private String id_familia;

    @Column(name = "ID_SUBFAMILIA")
    private String id_subfamilia;

    @Column(name = "STOCK")
    private Long stock;

    @Column(name = "NOVEDAD")
    private String novedad;

    @Column(name = "ID_MATERIAL")
    private String id_material;

    @Column(name = "ID_TIPO")
    private String id_tipo;

    @Column(name = "ID_COLOR_1")
    private String id_color_1;

    @Column(name = "ID_COLOR_2")
    private String id_color_2;

    @Column(name = "ID_COLOR_3")
    private String id_color_3;

    @Column(name = "BAR_CODE")
    private Long bar_code;

    @Column(name = "REFERENCIA")
    private String referencia;

    @Column(name = "FECHA_DISP")
    private String fecha_disp;

    @Column(name = "DIMENSION")
    private String dimension;

    @OneToMany()
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    private List<ARTICULOS_FOTOS> fotos = new ArrayList<ARTICULOS_FOTOS>();

    @Transient
    private String color_1;

    @Transient
    private String color_2;

    @Transient
    private String color_3;

    @Transient
    private String descripcion;

    @Transient
    private String full_descripcion;

    @Transient
    private String moneda;

    @Transient
    private double precio;

    @Transient
    private double descuento;

    @Transient
    private List<ARTICULOS> asociados;

    @Transient
    private List<ARTICULOS> agrupadosList;

    @Transient
    private String desc_operador;

    @Transient
    private Long agrupados;

    /*
     * Set/Get Methods
     */

    public String getId_familia() {
        return id_familia;
    }
    public void setId_familia(String id_familia) {
        this.id_familia = id_familia;
    }
    public void setID_FAMILIA(String id_familia) {
        this.id_familia = id_familia;
    }

    public Long getStock() {
        return stock;
    }
    public void setStock(Long stock) {
        this.stock = stock;
    }
    public void setSTOCK(BigDecimal stock) {
        this.stock = stock != null ? stock.longValue() : 0;
    }

    public String getNovedad() {
        return novedad;
    }
    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }
    public void setNOVEDAD(String novedad) {
        this.novedad = novedad;
    }

    public String getId_material() {
        return id_material;
    }
    public void setId_material(String id_material) {
        this.id_material = id_material;
    }
    public void setID_MATERIAL(String id_material) {
        this.id_material = id_material;
    }

    public Long getItem_id() {
        return item_id;
    }
    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }
    public void setITEM_ID(BigDecimal item_id) {
        this.item_id = item_id.longValue();
    }                                                       

    public String getId_tipo() {
        return id_tipo;
    }
    public void setId_tipo(String id_tipo) {
        this.id_tipo = id_tipo;
    }
    public void setID_TIPO(String id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getId_color_1() {
        return id_color_1;
    }
    public void setId_color_1(String id_color_1) {
        this.id_color_1 = id_color_1;
    }
    public void setID_COLOR_1(String id_color_1) {
        this.id_color_1 = id_color_1;
    }

    public String getId_color_2() {
        return id_color_2;
    }
    public void setId_color_2(String id_color_2) {
        this.id_color_2 = id_color_2;
    }
    public void setID_COLOR_2(String id_color_2) {
        this.id_color_2 = id_color_2;
    }

    public String getId_color_3() {
        return id_color_3;
    }
    public void setId_color_3(String id_color_3) {
        this.id_color_3 = id_color_3;
    }
    public void setID_COLOR_3(String id_color_3) {
        this.id_color_3 = id_color_3;
    }

    public String getId_subfamilia() {
        return id_subfamilia;
    }
    public void setId_subfamilia(String id_subfamilia) {
        this.id_subfamilia = id_subfamilia;
    }
    public void setID_SUBFAMILIA(String id_subfamilia) {
        this.id_subfamilia = id_subfamilia;
    }

    public Long getBar_code() {
        return bar_code;
    }
    public void setBar_code(Long bar_code) {
        this.bar_code = bar_code;
    }
    public void setBAR_CODE(BigDecimal bar_code) {
        this.bar_code = bar_code.longValue();
    }

    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    public void setREFERENCIA(String referencia) {
        this.referencia = referencia;
    }

    public String getFecha_disp() {
        return fecha_disp;
    }
    public void setFecha_disp(String fecha_disp) {
        this.fecha_disp = fecha_disp;
    }
    public void setFECHA_DISP(String fecha_disp) {
        this.fecha_disp = fecha_disp;
    }

    public String getDimension() {
        return dimension;
    }
    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
    public void setDIMENSION(String dimension) {
        this.dimension = dimension;
    }

    public List<ARTICULOS_FOTOS> getFotos() {
        return fotos;
    }

    public void setFotos(List<ARTICULOS_FOTOS> fotos) {
        this.fotos = fotos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setDESCRIPCION(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFull_descripcion() {
        return full_descripcion;
    }

    public void setFull_descripcion(String full_descripcion) {
        this.full_descripcion = full_descripcion;
    }
    public void setFULL_DESCRIPCION(String full_descripcion) {
        this.full_descripcion = full_descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getPrecio_desc() {
        if (descuento != 0) {
            if ("%".equals(desc_operador) || "PORCENTAJE".equals(desc_operador))
                return precio - precio * descuento / 100;
        }
        return precio;
    }

    public Long getAgrupados() {
        return agrupados;
    }
    public void setAgrupados(Long agrupados) {
        this.agrupados = agrupados;
    }
    public void setAGRUPADOS(BigDecimal agrupados) {
        this.agrupados = agrupados.longValue();
    }

    public List<ARTICULOS> getAsociados() {
        return asociados;
    }
    public void setAsociados(List<ARTICULOS> asociados) {
        this.asociados = asociados;
    }

    public String getDesc_operador() {
        return desc_operador;
    }
    public void setDesc_operador(String desc_operador) {
        this.desc_operador = desc_operador;
    }

    public String getColor_1() {
        return color_1;
    }
    public void setCOLOR_1(String color_1) {
        this.color_1 = color_1;
    }

    public String getColor_2() {
        return color_2;
    }
    public void setCOLOR_2(String color_2) {
        this.color_2 = color_2;
    }

    public String getColor_3() {
        return color_3;
    }
    public void setCOLOR_3(String color_3) {
        this.color_3 = color_3;
    }

    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public List<ARTICULOS> getAgrupadosList() {
        return agrupadosList;
    }

    public void setAgrupadosList(List<ARTICULOS> agrupadosList) {
        this.agrupadosList = agrupadosList;
    }

    public String getColor() {
        String colors = "";
        if (color_1 != null && !"NA".equals(id_color_1))
            colors += "/ "+Converter.capitalize(color_1);
        if (color_2 != null && !"NA".equals(id_color_2)) {
            colors += "".equals(colors) ? "/ " : ", ";
            colors += Converter.capitalize(color_2);
        }
        if (color_3 != null && !"NA".equals(id_color_3)) {
            colors += "".equals(colors) ? "/ " : ", ";
            colors += Converter.capitalize(color_3);
        }
        return colors;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "item_id"
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
            "id_familia", "stock", "novedad", "id_material", "item_id", "dimension",
            "id_tipo", "id_color", "id_subfamilia", "bar_code", "referencia", "fecha_disp"
        };
    }

    @Override
    public String toString() {
        return "ARTICULOS{" +
                "item_id=" + item_id +
                ", stock=" + stock +
                ", novedad='" + novedad + '\'' +
                ", id_material='" + id_material + '\'' +
                ", id_tipo='" + id_tipo + '\'' +
                ", id_color_1='" + id_color_1 + '\'' +
                ", id_color_2='" + id_color_2 + '\'' +
                ", id_color_3='" + id_color_3 + '\'' +
                ", bar_code=" + bar_code +
                ", referencia='" + referencia + '\'' +
                ", fecha_disp='" + fecha_disp + '\'' +
                ", dimension='" + dimension + '\'' +
                ", fotos=" + fotos +
                ", color_1=" + color_1 +
                ", color_2=" + color_2 +
                ", color_3=" + color_3 +
                ", descripcion='" + descripcion + '\'' +
                ", full_descripcion='" + full_descripcion + '\'' +
                ", moneda='" + moneda + '\'' +
                ", precio=" + precio +
                ", descuento=" + descuento +
                ", asociados=" + asociados +
                ", desc_operador='" + desc_operador + '\'' +
                '}';
    }
}

