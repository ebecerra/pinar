package com.befasoft.common.actions;

import com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG;
import com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_MANAGER;
import com.opensymphony.xwork2.Action;

import java.util.Date;

/**
 * Created by Devtools.
 * Date: 02/11/2011
 */
public class Usuarios_logAction extends BaseManagerAction {

    Long id_tipo, id_usuario, nivel;
    Date fec_from, fec_to;

    /**
     * Retorna el listado de elementos siguiente el filtro y el paginado
     *
     * @return Lista de elementos
     * @throws Exception Error en la llamada a los bean
     */
    @Override
    protected String executeList() throws Exception {
        log.debug("listUsuariosLog()");
        int count = APPBS_USUARIOS_LOG_MANAGER.listLogCount(id_usuario, id_tipo, nivel, fec_from, fec_to);
        setReturn(APPBS_USUARIOS_LOG_MANAGER.listLog(id_usuario, id_tipo, nivel, fec_from, fec_to, start, limit, sort, dir), count, start, limit);
        return Action.SUCCESS;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Bean de datos
     */
    @Override
    protected Class getBeanClass() {
        return APPBS_USUARIOS_LOG.class;
    }

    /**
     * Retorna una entidad vacia de lo que se esta trabajando..
     *
     * @return Manager del bean de datos
     */
    @Override
    protected Class getManagerClass() {
        return APPBS_USUARIOS_LOG_MANAGER.class;
    }

    /*
     * Metodos Get/Set
     */

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Date getFec_from() {
        return fec_from;
    }

    public void setFec_from(Date fec_from) {
        this.fec_from = fec_from;
    }

    public Date getFec_to() {
        return fec_to;
    }

    public void setFec_to(Date fec_to) {
        this.fec_to = fec_to;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }
}


