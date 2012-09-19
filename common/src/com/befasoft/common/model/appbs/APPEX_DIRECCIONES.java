package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
@Entity
@Table(name = "APPEX_DIRECCIONES")
public class APPEX_DIRECCIONES extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_DIRECCION")
    private Long id_direccion;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "ID_PROVINCIA")
    private Long id_provincia;


    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "CP")
    private String cp;

    @Column(name = "POBLACION")
    private String poblacion;

    @Column(name = "ID_PAIS")
    private Long id_pais;

    @Column(name = "FISCAL")
    private Boolean fiscal;

    @Column(name = "ENVIO")
    private Boolean envio;

    @Column(name = "FACTURACION")
    private Boolean facturacion;

    @Column(name = "ACTIVA")
    private Boolean activa;

    @Column(name = "PRINCIPAL")
    private Boolean principal;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID_PAIS", insertable = false, updatable = false),
        @JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_PROVINCIA", insertable = false, updatable = false)
    })
    private APPEX_PROVINCIAS provincia = new APPEX_PROVINCIAS();

    /*
     * Set/Get Methods
     */
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public Long getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(Long id_provincia) {
        this.id_provincia = id_provincia;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
    
    public Long getId_pais() {
        return id_pais;
    }

    public void setId_pais(Long id_pais) {
        this.id_pais = id_pais;
    }
    
    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
    
    public Long getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(Long id_direccion) {
        this.id_direccion = id_direccion;
    }

    public Boolean getFiscal() {
        return fiscal;
    }

    public void setFiscal(Boolean fiscal) {
        this.fiscal = fiscal;
    }

    public Boolean getEnvio() {
        return envio;
    }

    public void setEnvio(Boolean envio) {
        this.envio = envio;
    }

    public Boolean getFacturacion() {
        return facturacion;
    }

    public void setFacturacion(Boolean facturacion) {
        this.facturacion = facturacion;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public APPEX_PROVINCIAS getProvincia() {
        return provincia;
    }

    public void setProvincia(APPEX_PROVINCIAS provincia) {
        this.provincia = provincia;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_direccion"
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
            "principal", "direccion", "id_provincia", "facturacion", "observaciones", "cp", "id_pais", "fiscal", "envio", "poblacion", "activa", "id_direccion"
        };
    }

}

