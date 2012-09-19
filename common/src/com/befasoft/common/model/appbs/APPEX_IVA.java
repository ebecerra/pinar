package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 03/11/2011
 */
@Entity
@Table(name = "APPEX_IVA")
public class APPEX_IVA extends EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_IVA")
    private Long id_iva;

  
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "VALOR")
    private Double valor;

    @Column(name = "RECARGO")
    private Double recargo;


    /*
     * Set/Get Methods
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public Double getRecargo() {
        return recargo;
    }

    public void setRecargo(Double recargo) {
        this.recargo = recargo;
    }
    
    public Long getId_iva() {
        return id_iva;
    }

    public void setId_iva(Long id_iva) {
        this.id_iva = id_iva;
    }
    
    public Double getTotal() {
        return (valor == null ? 0 : valor) + (recargo == null ? 0 : recargo);
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_iva"
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
            "nombre", "valor", "recargo", "id_iva"
        };
    }

}

