package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 11/04/2011
 */
@Entity
@Table(name = "PEDIDOS")
public class PEDIDOS extends EntityBean {

    @Id
    @Column(name = "PEDIDO_NUM")
    private Long pedido_num;

    @Column(name = "ID_LISTA_PREC")
    private Long id_lista_prec;

    @Column(name = "REF_DIR_FACT_WEB")
    private String ref_dir_fact_web;

    @Column(name = "ID_DIR_ENV")
    private Long id_dir_env;

    @Column(name = "REF_DIR_ENV_WEB")
    private String ref_dir_env_web;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "ID_ORIGEN")
    private Long id_origen;

    @Column(name = "ID_DIR_FACT")
    private Long id_dir_fact;

    @Column(name = "ID_MONEDA")
    private String id_moneda;

    @Column(name = "REF_CLI_WEB")
    private String ref_cli_web;

    @Column(name = "ID_CLIENTE")
    private Long id_cliente;

    @Column(name = "ID_VENDEDOR")
    private Long id_vendedor;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "EMAIL_CONFIRM")
    private String email_confirm;

    @Column(name = "ID_IVA")
    private String id_iva;

    @Column(name = "MOBILE_PEDIDO_NUM")
    private Long mobile_pedido_num;

    @Column(name = "MOBILE_ID")
    private Long mobile_id;

    @OneToOne(targetEntity = PEDIDOS_ORIGEN.class)
    @JoinColumn(name = "ID_ORIGEN", referencedColumnName = "ID_ORIGEN", insertable=false, updatable=false)
    private PEDIDOS_ORIGEN origen;

    @ManyToOne()
    @JoinColumn(name = "ID_IVA", referencedColumnName = "ID_IVA", insertable=false, updatable=false)
    private IVA iva;

    @ManyToOne()
    @JoinColumn(name = "ID_MONEDA", referencedColumnName = "ID_MONEDA", insertable=false, updatable=false)
    private MONEDAS moneda;

    @OneToMany()
    @JoinColumn(name = "PEDIDO_NUM", referencedColumnName = "PEDIDO_NUM")
    @OrderBy("linea_num")
    private List<PEDIDOS_LINEAS> lineas = new ArrayList<PEDIDOS_LINEAS>();

    @Transient
    private long cli_id_condpago;
    @Transient
    private long cantidad;
    @Transient
    private double importe;
    @Transient
    private double importe_transp;
    @Transient
    private CLIENTES_DIRECCIONES dir_fact;
    @Transient
    private CLIENTES_DIRECCIONES dir_env;
    @Transient
    private List<CLIENTES_DIRECCIONES> dir_fact_list;
    @Transient
    private List<CLIENTES_DIRECCIONES> dir_env_list;
    @Transient
    private double pp_id_condpago;
    @Transient
    private boolean show_descuentos;
    @Transient
    private double transporte;

    /*
     * Set/Get Methods
     */

    public Long getId_lista_prec() {
        return id_lista_prec;
    }

    public void setId_lista_prec(Long id_lista_prec) {
        this.id_lista_prec = id_lista_prec;
    }

    public Long getId_dir_env() {
        return id_dir_env;
    }

    public void setId_dir_env(Long id_dir_env) {
        this.id_dir_env = id_dir_env;
    }

    public Long getId_origen() {
        return id_origen;
    }

    public void setId_origen(Long id_origen) {
        this.id_origen = id_origen;
    }

    public Long getId_dir_fact() {
        return id_dir_fact;
    }

    public void setId_dir_fact(Long id_dir_fact) {
        this.id_dir_fact = id_dir_fact;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(Long id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getRef_dir_fact_web() {
        return ref_dir_fact_web;
    }

    public void setRef_dir_fact_web(String ref_dir_fact_web) {
        this.ref_dir_fact_web = ref_dir_fact_web;
    }

    public String getRef_dir_env_web() {
        return ref_dir_env_web;
    }

    public void setRef_dir_env_web(String ref_dir_env_web) {
        this.ref_dir_env_web = ref_dir_env_web;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(String id_moneda) {
        this.id_moneda = id_moneda;
    }

    public String getRef_cli_web() {
        return ref_cli_web;
    }

    public void setRef_cli_web(String ref_cli_web) {
        this.ref_cli_web = ref_cli_web;
    }

    public Long getPedido_num() {
        return pedido_num;
    }

    public void setPedido_num(Long pedido_num) {
        this.pedido_num = pedido_num;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public MONEDAS getMoneda() {
        return moneda;
    }

    public void setMoneda(MONEDAS moneda) {
        this.moneda = moneda;
    }

    public PEDIDOS_ORIGEN getOrigen() {
        return origen;
    }

    public void setOrigen(PEDIDOS_ORIGEN origen) {
        this.origen = origen;
    }

    public List<PEDIDOS_LINEAS> getLineas() {
        return lineas;
    }

    public void setLineas(List<PEDIDOS_LINEAS> lineas) {
        this.lineas = lineas;
    }

    public CLIENTES_DIRECCIONES getDir_fact() {
        return dir_fact;
    }

    public void setDir_fact(CLIENTES_DIRECCIONES dir_fact) {
        this.dir_fact = dir_fact;
    }

    public CLIENTES_DIRECCIONES getDir_env() {
        return dir_env;
    }

    public void setDir_env(CLIENTES_DIRECCIONES dir_env) {
        this.dir_env = dir_env;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEmail_confirm() {
        return email_confirm;
    }

    public void setEmail_confirm(String email_confirm) {
        this.email_confirm = email_confirm;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporte() {
        return importe;
    }

    public double getImporte_transp() {
        return importe_transp;
    }

    public void setImporte_transp(double importe_transp) {
        this.importe_transp = importe_transp;
    }

    public double getImporte_iva() {
        double imp_iva = 0;
        if (iva != null) {
            imp_iva = (importe + transporte) * (iva.getPorc_iva() + iva.getPorc_rep()) / 100;
        }
        return imp_iva;
    }

    public double getImporte_total() {
        return importe + transporte + getImporte_iva();
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getCli_id_condpago() {
        return cli_id_condpago;
    }

    public void setCli_id_condpago(long cli_id_condpago) {
        this.cli_id_condpago = cli_id_condpago;
    }

    public String getId_iva() {
        return id_iva;
    }

    public void setId_iva(String id_iva) {
        this.id_iva = id_iva;
    }

    public IVA getIva() {
        return iva;
    }

    public void setIva(IVA iva) {
        this.iva = iva;
    }

    public double getPp_id_condpago() {
        return pp_id_condpago;
    }

    public void setPp_id_condpago(long pp_id_condpago) {
        this.pp_id_condpago = pp_id_condpago;
    }

    public Long getIdcondpago() {
        return
            (dir_env != null && dir_env.getId_condpago() != null) ? dir_env.getId_condpago() :
            (dir_fact != null && dir_fact.getId_condpago() != null) ? dir_fact.getId_condpago() : cli_id_condpago;
    }

    public List<CLIENTES_DIRECCIONES> getDir_fact_list() {
        return dir_fact_list;
    }

    public void setDir_fact_list(List<CLIENTES_DIRECCIONES> dir_fact_list) {
        this.dir_fact_list = dir_fact_list;
        if (id_dir_fact != null && id_dir_fact > 0) {
            for (int i = 0; i < dir_fact_list.size(); i++) {
                CLIENTES_DIRECCIONES dir = dir_fact_list.get(i);
                if (dir.getId_direccion().equals(id_dir_fact)) {
                    this.dir_fact = dir;
                    break;
                }
            }
        }
        if (dir_fact == null && dir_fact_list != null && dir_fact_list.size() > 0) {
            this.dir_fact = dir_fact_list.get(0);
            this.id_dir_fact = dir_fact.getId_direccion();
        }
    }

    public List<CLIENTES_DIRECCIONES> getDir_env_list() {
        return dir_env_list;
    }

    public void setDir_env_list(List<CLIENTES_DIRECCIONES> dir_env_list) {
        this.dir_env_list = dir_env_list;
        if (id_dir_env != null && id_dir_env > 0) {
            for (int i = 0; i < dir_env_list.size(); i++) {
                CLIENTES_DIRECCIONES dir = dir_env_list.get(i);
                if (dir.getId_direccion().equals(id_dir_env)) {
                    this.dir_env = dir;
                    break;
                }
            }
        }
        if (dir_env == null && dir_env_list != null && dir_env_list.size() > 0) {
            this.dir_env = dir_env_list.get(0);
            this.id_dir_env = dir_env.getId_direccion();
        }
    }

    public boolean isValid() {
       return lineas.size() > 0 && dir_env != null && dir_fact != null && dir_env.isValid() && dir_fact.isValid();
    }

    public boolean isValidDir_fact() {
        return dir_fact != null && dir_fact.isValid();
    }

    public boolean isValidDir_env() {
        return dir_env != null && dir_env.isValid();
    }

    public boolean isShow_descuentos() {
        return show_descuentos;
    }

    public void setShow_descuentos(boolean show_descuentos) {
        this.show_descuentos = show_descuentos;
    }

    public double getTransporte() {
        return transporte;
    }

    public void setTransporte(double transporte) {
        this.transporte = transporte;
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

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "pedido_num"
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
            "id_lista_prec", "ref_dir_fact_web", "id_dir_env", "ref_dir_env_web", "fecha", "id_tipo", "id_origen",
            "id_dir_fact", "id_moneda", "ref_cli_web", "id_cliente", "id_vendedor", "pedido_num",
            "id_iva", "anticipo", "mobile_pedido_num", "mobile_id"
        };
    }

}

