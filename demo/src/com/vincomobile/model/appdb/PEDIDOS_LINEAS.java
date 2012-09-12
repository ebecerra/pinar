package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 11/04/2011
 */
@Entity
@Table(name = "PEDIDOS_LINEAS")
public class PEDIDOS_LINEAS extends EntityBean {

    @Id
    @Column(name = "LINEA_NUM")
    Long linea_num;

    @Id
    @Column(name="PEDIDO_NUM")
    Long pedido_num;

    @Column(name = "PRECIO")
    private Double precio;

    @Column(name = "ITEM_ID")
    private Long item_id;

    @Column(name = "CANTIDAD")
    private Long cantidad;

    @Column(name = "ID_PROMOCION")
    private Long id_promocion;

    @Column(name = "ID_PROMO_LINEA")
    private Long id_promo_linea;

    @Column(name = "DESCUENTO")
    private Double descuento;

    @Column(name = "OPERADOR")
    private String operador;

    @Column(name = "EN_TIENDA")
    private String en_tienda;

    @Column(name = "ITEM_ID_WEB")
    private Long item_id_web;

    @Column(name = "MOBILE_PEDIDO_NUM")
    private Long mobile_pedido_num;

    @Column(name = "MOBILE_ID")
    private Long mobile_id;

    @Column(name = "ID_VENDEDOR")
    private Long id_vendedor;

    @ManyToOne
    @JoinColumn(name="ITEM_ID", insertable=false, updatable=false)
    @Immutable
    private ARTICULOS articulo;

    @Transient
    private String descripcion;

    @Transient
    private List<ARTICULOS_FOTOS> fotos = new ArrayList<ARTICULOS_FOTOS>();

    @Transient
    private Double precio_visible;

    /*
     * Set/Get Methods
     */

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
        this.precio_visible = precio;
    }

    public Double getPrecio_visible() {
        if (precio_visible == null)
            precio_visible = precio;
        return precio_visible;
    }

    public void setPrecio_visible(Double precio_visible) {
        this.precio_visible = precio_visible;
    }

    public Long getPedido_num() {
        return pedido_num;
    }

    public void setPedido_num(Long pedido_num) {
        this.pedido_num = pedido_num;
    }

    public Long getLinea_num() {
        return linea_num;
    }

    public void setLinea_num(Long linea_num) {
        this.linea_num = linea_num;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getId_promocion() {
        return id_promocion;
    }

    public void setId_promocion(Long id_promocion) {
        this.id_promocion = id_promocion;
    }

    public Long getId_promo_linea() {
        return id_promo_linea;
    }

    public void setId_promo_linea(Long id_promo_linea) {
        this.id_promo_linea = id_promo_linea;
    }

    public double getImporte() {
        if (precio_visible == null)
            precio_visible = precio;
        double prec = precio_visible;
        if (descuento != 0) {
            if ("%".equals(operador) || "PORCENTAJE".equals(operador))
                prec = precio - precio * descuento / 100;
        }
        return prec * cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ARTICULOS_FOTOS> getFotos() {
        return fotos;
    }

    public void setFotos(List<ARTICULOS_FOTOS> fotos) {
        this.fotos = fotos;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public ARTICULOS getArticulo() {
        return articulo;
    }

    public void setArticulo(ARTICULOS articulo) {
        this.articulo = articulo;
    }

    public String getEn_tienda() {
        return en_tienda;
    }

    public void setEn_tienda(String en_tienda) {
        this.en_tienda = en_tienda;
    }

    public Long getItem_id_web() {
        return item_id_web;
    }

    public void setItem_id_web(Long item_id_web) {
        this.item_id_web = item_id_web;
    }

    public Long getMobile_pedido_num() {
        return mobile_pedido_num;
    }

    public void setMobile_pedido_num(Long mobile_pedido_num) {
        this.mobile_pedido_num = mobile_pedido_num;
    }

    public Long getMobile_id() {
        return mobile_id;
    }

    public void setMobile_id(Long mobile_id) {
        this.mobile_id = mobile_id;
    }

    public Long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(Long id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "linea_num", "pedido_num"
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
            "precio", "item_id", "cantidad", "id_promocion", "linea_num", "pedido_num", "id_promo_linea", "descuento",
            "operador", "en_tienda", "item_id_web", "id_vendedor", "mobile_pedido_num", "mobile_id"
        };
    }

}



