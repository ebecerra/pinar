package com.befasoft.common.model.appbs;

import com.befasoft.common.model.EntityBean;
import com.googlecode.jsonplugin.annotations.JSON;

import javax.persistence.*;

/**
 * Created by Devtools.
 * Date: 28/12/2011
 */
@Entity
@Table(name = "APPBS_FOREINGKEYS_VALUES")
public class APPBS_FOREINGKEYS_VALUES extends EntityBean {

    @Id
    @Column(name = "ID_KEY")
    private Long id_key;

    @Id
    @Column(name = "ID_TABLA")
    private Long id_tabla;

    @Id
    @Column(name = "ID_CAMPO")
    private Long id_campo;

  
    @Column(name = "ID_REFCAMPO")
    private Long id_refcampo;

    @Column(name = "ID_REFTABLA")
    private Long id_reftabla;

    @ManyToOne()
    @JoinColumns({
        @JoinColumn(name = "ID_TABLA", referencedColumnName = "ID_TABLA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_CAMPO", referencedColumnName = "ID_CAMPO", insertable = false, updatable = false)
    })
    private APPBS_CAMPOS campo;

    @ManyToOne()
    @JoinColumns({
        @JoinColumn(name = "ID_REFTABLA", referencedColumnName = "ID_TABLA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_REFCAMPO", referencedColumnName = "ID_CAMPO", insertable = false, updatable = false)
    })
    private APPBS_CAMPOS fk_campo;

    @ManyToOne()
    @JoinColumn(name = "ID_KEY", referencedColumnName = "ID_KEY", insertable = false, updatable = false)
    private APPBS_FOREINGKEYS fk;

    /*
     * Set/Get Methods
     */
    public Long getId_key() {
        return id_key;
    }

    public void setId_key(Long id_key) {
        this.id_key = id_key;
    }
    
    public Long getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(Long id_tabla) {
        this.id_tabla = id_tabla;
    }
    
    public Long getId_campo() {
        return id_campo;
    }

    public void setId_campo(Long id_campo) {
        this.id_campo = id_campo;
    }
    
    public Long getId_refcampo() {
        return id_refcampo;
    }

    public void setId_refcampo(Long id_refcampo) {
        this.id_refcampo = id_refcampo;
    }
    
    public Long getId_reftabla() {
        return id_reftabla;
    }

    public void setId_reftabla(Long id_reftabla) {
        this.id_reftabla = id_reftabla;
    }

    public APPBS_CAMPOS getCampo() {
        return campo;
    }

    public void setCampo(APPBS_CAMPOS campo) {
        this.campo = campo;
    }

    public APPBS_CAMPOS getFk_campo() {
        return fk_campo;
    }

    public void setFk_campo(APPBS_CAMPOS fk_campo) {
        this.fk_campo = fk_campo;
    }

    public APPBS_FOREINGKEYS getFk() {
        return fk;
    }

    public void setFk(APPBS_FOREINGKEYS fk) {
        this.fk = fk;
    }

    /**
     * Nombre de los campos Key del bean
     *
     * @return Lista de campos llave
     */
    @JSON(serialize = false)
    public String[] getKeyFields() {
        return new String[] {
            "id_key", "id_tabla", "id_campo"
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
            "id_key", "id_tabla", "id_campo", "id_refcampo", "id_reftabla"
        };
    }

}

