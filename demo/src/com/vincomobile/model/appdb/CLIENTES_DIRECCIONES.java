package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_PAISES;
import com.befasoft.common.model.appbs.APPEX_PROVINCIAS;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 14/04/2011
 */
@Entity
@Table(name = "CLIENTES_DIRECCIONES")
public class CLIENTES_DIRECCIONES extends EntityBean {

    @Id
    @Column(name="ID_CLIENTE")
    private Long id_cliente;

    @Id
    @Column(name="ID_DIRECCION")
    private Long id_direccion;

    @Column(name = "ID_CONDPAGO")
    private Long id_condpago;

    @Column(name = "ID_IVA")
    private String id_iva;

    @Column(name = "REF_DIR_WEB")
    private String ref_dir_web;

    @Column(name = "CIUDAD")
    private String ciudad;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ID_LISTA_PREC")
    private Long id_lista_prec;

    @Column(name = "REF_CLI_WEB")
    private String ref_cli_web;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "PRINCIPAL")
    private String principal;

    @Column(name = "ID_PROVINCIA")
    private String id_provincia;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "ID_VENDEDOR")
    private Long id_vendedor;

    @Column(name = "CODIGO_POSTAL")
    private String codigo_postal;

    @Column(name = "ID_PAIS")
    private String id_pais;

    @Column(name = "EXPORTABLE")
    private String exportable;

    @Column(name = "FUENTE")
    private String fuente;

    @Column(name = "MOBILE_ID_CLIENTE")
    private Long mobile_id_cliente;

    @Column(name = "MOBILE_ID_DIRECCION")
    private Long mobile_id_direccion;

    @Column(name = "MOBILE_ID")
    private Long mobile_id;

    @ManyToOne
    @JoinColumn(name="ID_PAIS", insertable=false, updatable=false)
    private APPEX_PAISES pais;

    @Transient
    private APPEX_PROVINCIAS provincia;

    /*
     * Set/Get Methods
     */
    public String getFullAddress() {
        StringBuilder dir = new StringBuilder();
        dir.append(direccion).append(", ").append(codigo_postal).append(" ");
        if (ciudad != null)
            dir.append(ciudad).append(", ");
        if ("ES".equals(id_pais)) 
            dir.append(provincia != null ? provincia.getNombre() : " - ").append(", ");
        dir.append(pais != null ? pais.getNombre() : "");
        return dir.toString();
    }

    public String getFullAddress2() {
        StringBuilder dir = new StringBuilder();
        dir.append(direccion).append("<br>").append(codigo_postal).append(" ");
        if (ciudad != null)
            dir.append(ciudad).append(", ");
        if ("ES".equals(id_pais))
            dir.append(provincia != null ? provincia.getNombre() : " - ").append(", ");
        dir.append(pais != null ? pais.getNombre() : "");
        return dir.toString();
    }

    public String getFullAddress3() {
        StringBuilder dir = new StringBuilder();
        dir.append(tipo).append(" - ").append(getFullAddress());
        return dir.toString();
    }

    public String getProvinciaEstado() {
        if ("ES".equals(id_pais))
            return provincia != null ? provincia.getId_provincia()+" - "+provincia.getNombre() : id_provincia;
        else
            return estado;
    }

    public String getProvinciaEstado2() {
        if ("ES".equals(id_pais))
            return provincia != null ? provincia.getNombre() : id_provincia;
        else
            return estado;
    }

    public boolean isValid() {
        return "ES".equals(id_pais) ? provincia != null : true;
    }

    public Long getId_condpago() {
        return id_condpago;
    }

    public void setId_condpago(Long id_condpago) {
        this.id_condpago = id_condpago;
    }

    public String getId_iva() {
        return id_iva;
    }

    public void setId_iva(String id_iva) {
        this.id_iva = id_iva;
    }

    public String getRef_dir_web() {
        return ref_dir_web;
    }

    public void setRef_dir_web(String ref_dir_web) {
        this.ref_dir_web = ref_dir_web;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId_lista_prec() {
        return id_lista_prec;
    }

    public void setId_lista_prec(Long id_lista_prec) {
        this.id_lista_prec = id_lista_prec;
    }

    public String getRef_cli_web() {
        return ref_cli_web;
    }

    public void setRef_cli_web(String ref_cli_web) {
        this.ref_cli_web = ref_cli_web;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(String id_provincia) {
        this.id_provincia = id_provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(Long id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public Long getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(Long id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getExportable() {
        return exportable;
    }

    public void setExportable(String exportable) {
        this.exportable = exportable;
    }

    public APPEX_PROVINCIAS getProvincia() {
        return provincia;
    }

    public void setProvincia(APPEX_PROVINCIAS provincia) {
        this.provincia = provincia;
    }

    public APPEX_PAISES getPais() {
        return pais;
    }

    public void setPais(APPEX_PAISES pais) {
        this.pais = pais;
    }

    public String getProvincia_nombre() {
        return provincia != null ? provincia.getNombre() : "";
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Long getMobile_id_cliente() {
        return mobile_id_cliente;
    }

    public void setMobile_id_cliente(Long mobile_id_cliente) {
        this.mobile_id_cliente = mobile_id_cliente;
    }

    public Long getMobile_id_direccion() {
        return mobile_id_direccion;
    }

    public void setMobile_id_direccion(Long mobile_id_direccion) {
        this.mobile_id_direccion = mobile_id_direccion;
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
        return new String[]{
            "id_cliente", "id_direccion"
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
            "id_condpago", "id_iva", "ref_dir_web", "ciudad", "id_cliente", "estado", "id_lista_prec", "ref_cli_web", "tipo",
            "principal", "id_provincia", "direccion", "id_vendedor", "codigo_postal", "id_pais", "id_direccion",
            "exportable", "fuente", "mobile_id_cliente", "mobile_id_direccion", "mobile_id"
        };
    }

}




