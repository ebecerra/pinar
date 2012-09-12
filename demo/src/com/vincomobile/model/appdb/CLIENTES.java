package com.vincomobile.model.appdb;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devtools.
 * Date: 14/04/2011
 */
@Entity
@Table(name = "CLIENTES")
public class CLIENTES extends EntityBean {

    @Id
    @Column(name = "ID_CLIENTE")
    private Long id_cliente;

    @Column(name = "REF_WEB")
    private String ref_web;

    @Column(name = "ID_CONDPAGO")
    private Long id_condpago;

    @Column(name = "ID_IVA")
    private String id_iva;

    @Column(name = "NUMERO", nullable = true)
    //@Nullable
    private Long numero;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CLASE")
    private String clase;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ID_LISTA_PREC")
    private Long id_lista_prec;

    @Column(name = "PERSONA_FLAG")
    private String persona_flag;

    @Column(name = "PERSONA_NOMBRE")
    private String persona_nombre;

    @Column(name = "PERSONA_APELLIDOS")
    private String persona_apellidos;

    @Column(name = "EMAIL_COMERCIAL")
    private String email_comercial;

    @Column(name = "PRECIO_PUNTO")
    private Double precio_punto;

    @Column(name = "EXPORTABLE")
    private String exportable;

    @Column(name = "MULTIPLICADOR")
    private Double multiplicador;

    @Column(name = "AGENTE_FLAG")
    private String agente_flag;

    @Column(name = "CB_ENTIDAD")
    private String cb_entidad;

    @Column(name = "CB_SUCURSAL")
    private String cb_sucursal;

    @Column(name = "CB_DC")
    private String cb_dc;

    @Column(name = "CB_CUENTA")
    private String cb_cuenta;

    @Column(name = "ID_AGENTE")
    private Long id_agente;

    @Column(name = "VALIDO")
    private String valido;

    @Column(name = "FUENTE")
    private String fuente;

    @Column(name = "MOBILE_ID_CLIENTE")
    private Long mobile_id_cliente;

    @Column(name = "MOBILE_ID")
    private Long mobile_id;


    @OneToMany()
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", insertable=false, updatable=false)
    private List<CLIENTES_DIRECCIONES> direcciones = new ArrayList<CLIENTES_DIRECCIONES>();

    @Transient
    private boolean nacional;

    /*
     * Set/Get Methods
     */
    public void setROWNUM_(BigDecimal rownum) {

    }
    
    public String getRef_web() {
        return ref_web;
    }
    public void setRef_web(String ref_web) {
        this.ref_web = ref_web;
    }
    public void setREF_WEB(String ref_web) {
        this.ref_web = ref_web;
    }

    public Long getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }
    public void setID_CLIENTE(BigDecimal id_cliente) {
        this.id_cliente = id_cliente == null ? null :id_cliente.longValue();
    }

    public Long getId_condpago() {
        return id_condpago;
    }
    public void setId_condpago(Long id_condpago) {
        this.id_condpago = id_condpago;
    }
    public void setID_CONDPAGO(BigDecimal id_condpago) {
        this.id_condpago = id_condpago == null ? null : id_condpago.longValue();
    }

    public String getId_iva() {
        return id_iva;
    }
    public void setId_iva(String id_iva) {
        this.id_iva = id_iva;
    }
    public void setID_IVA(String id_iva) {
        this.id_iva = id_iva;
    }

    public Long getNumero() {
        return numero;
    }
    public void setNumero(Long numero) {
        this.numero = numero;
    }
    public void setNUMERO(BigDecimal numero) {
        this.numero = numero == null ? null : numero.longValue();
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setNOMBRE(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }
    public void setClase(String clase) {
        this.clase = clase;
    }
    public void setCLASE(String clase) {
        this.clase = clase;
    }

    public String getCif() {
        return cif;
    }
    public void setCif(String cif) {
        this.cif = cif;
    }
    public void setCIF(String cif) {
        this.cif = cif;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEMAIL(String email) {
        this.email = email;
    }

    public Long getId_lista_prec() {
        return id_lista_prec;
    }
    public void setId_lista_prec(Long id_lista_prec) {
        this.id_lista_prec = id_lista_prec;
    }
    public void setID_LISTA_PREC(BigDecimal id_lista_prec) {
        this.id_lista_prec = id_lista_prec == null ? null : id_lista_prec.longValue();
    }

    public String getPersona_flag() {
        return persona_flag;
    }
    public void setPersona_flag(String persona_flag) {
        this.persona_flag = persona_flag;
    }
    public void setPERSONA_FLAG(String persona_flag) {
        this.persona_flag = persona_flag;
    }

    public String getPersona_nombre() {
        return persona_nombre;
    }
    public void setPersona_nombre(String persona_nombre) {
        this.persona_nombre = persona_nombre;
    }
    public void setPERSONA_NOMBRE(String persona_nombre) {
        this.persona_nombre = persona_nombre;
    }

    public String getPersona_apellidos() {
        return persona_apellidos;
    }
    public void setPersona_apellidos(String persona_apellidos) {
        this.persona_apellidos = persona_apellidos;
    }
    public void setPERSONA_APELLIDOS(String persona_apellidos) {
        this.persona_apellidos = persona_apellidos;
    }

    public String getEmail_comercial() {
        return email_comercial;
    }
    public void setEmail_comercial(String email_comercial) {
        this.email_comercial = email_comercial;
    }
    public void setEMAIL_COMERCIAL(String email_comercial) {
        this.email_comercial = email_comercial;
    }

    public String getExportable() {
        return exportable;
    }
    public void setExportable(String exportable) {
        this.exportable = exportable;
    }
    public void setEXPORTABLE(String exportable) {
        this.exportable = exportable;
    }

    public String getAgente_flag() {
        return agente_flag;
    }
    public void setAgente_flag(String agente_flag) {
        this.agente_flag = agente_flag;
    }
    public void setAGENTE_FLAG(String agente_flag) {
        this.agente_flag = agente_flag;
    }

    public Double getPrecio_punto() {
        return precio_punto == null ? 0 : precio_punto;
    }
    public void setPrecio_punto(Double precio_punto) {
        this.precio_punto = precio_punto;
    }
    public void setPRECIO_PUNTO(BigDecimal precio_punto) {
        this.precio_punto = precio_punto == null ? null : precio_punto.doubleValue();
    }

    public Long getId_agente() {
        return id_agente;
    }
    public void setId_agente(Long id_agente) {
        this.id_agente = id_agente;
    }
    public void setID_AGENTE(BigDecimal id_agente) {
        this.id_agente = id_agente == null ? null : id_agente.longValue();
    }

    public List<CLIENTES_DIRECCIONES> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<CLIENTES_DIRECCIONES> direcciones) {
        this.direcciones = direcciones;
    }

    public Double getMultiplicador() {
        return multiplicador == null ? 0 : multiplicador;
    }
    public void setMultiplicador(Double multiplicador) {
        this.multiplicador = multiplicador;
    }
    public void setMULTIPLICADOR(BigDecimal multiplicador) {
        this.multiplicador = multiplicador == null ? null : multiplicador.doubleValue();
    }

    public String getFullname() {
        if ("Persona".equals(persona_flag))
            return persona_nombre+" "+persona_apellidos;
        else
            return nombre;
    }

    public boolean isNacional() {
        return nacional;
    }

    public void setNacional(boolean nacional) {
        this.nacional = nacional;
    }

    public String getCb_entidad() {
        return cb_entidad;
    }
    public void setCb_entidad(String cb_entidad) {
        this.cb_entidad = cb_entidad;
    }
    public void setCB_ENTIDAD(String cb_entidad) {
        this.cb_entidad = cb_entidad;
    }

    public String getCb_sucursal() {
        return cb_sucursal;
    }
    public void setCb_sucursal(String cb_sucursal) {
        this.cb_sucursal = cb_sucursal;
    }
    public void setCB_SUCURSAL(String cb_sucursal) {
        this.cb_sucursal = cb_sucursal;
    }

    public String getCb_dc() {
        return cb_dc;
    }
    public void setCb_dc(String cb_dc) {
        this.cb_dc = cb_dc;
    }
    public void setCB_DC(String cb_dc) {
        this.cb_dc = cb_dc;
    }

    public String getCb_cuenta() {
        return cb_cuenta;
    }
    public void setCb_cuenta(String cb_cuenta) {
        this.cb_cuenta = cb_cuenta;
    }
    public void setCB_CUENTA(String cb_cuenta) {
        this.cb_cuenta = cb_cuenta;
    }

    public String getValido() {
        return valido;
    }
    public void setValido(String valido) {
        this.valido = valido;
    }
    public void setVALIDO(String valido) {
        this.valido = valido;
    }

    public String getFuente() {
        return fuente;
    }
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
    public void setFUENTE(String fuente) {
        this.fuente = fuente;
    }

    public Long getMobile_id() {
        return mobile_id;
    }

    public void setMobile_id(Long mobile_id) {
        this.mobile_id = mobile_id;
    }
    
    public void setMOBILE_ID(BigDecimal mobile_id) {
        this.mobile_id = mobile_id != null ? mobile_id.longValue() : null;
    }

    public Long getMobile_id_cliente() {
        return mobile_id_cliente;
    }

    public void setMobile_id_cliente(Long mobile_id_cliente) {
        this.mobile_id_cliente = mobile_id_cliente;
    }
    
    public void setMOBILE_ID_CLIENTE(BigDecimal mobile_id_cliente) {
        this.mobile_id_cliente = mobile_id_cliente != null ? mobile_id_cliente.longValue() : null;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[]{
            "id_cliente"
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
            "ref_web", "id_condpago", "id_cliente", "exportable", "cif", "numero", "nombre", "email", "valido",
            "clase", "email_comercial", "persona_flag", "persona_apellidos", "id_iva", "persona_nombre", "id_lista_prec",
            "id_agente", "fuente", "precio_punto", "mobile_id_cliente", "mobile_id"
        };
    }

}

