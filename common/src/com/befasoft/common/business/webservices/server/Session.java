package com.befasoft.common.business.webservices.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface Session {

    /**
     * Genera y retorna una serverKey, un sessionID y crea una nueva sesion asociada a este
     * @param clientKey Cadena a partir de la cual se generaro la Llave del Servidor
     * @param useDB Indica si se accede a DB
     * @return Llave del servidor
     * @throws Exception Error
     */
    @WebMethod
    public String createServerKey(@WebParam(name = "clientKey")String clientKey,
                                  @WebParam(name = "useDB")Boolean useDB) throws Exception;

    /**
     * Termina la sesion y cierra la conexion a BBDD
     * @param sessionID ID de Sesion
     * @param commitTrans Define el modo de terminar la conexion, True hace commit, False rollback
     */
    @WebMethod
    public void finishSession(@WebParam(name = "sessionID")String sessionID,
                              @WebParam(name = "commitTrans")Boolean commitTrans);

    /**
     * Verifica si la sesion es validad
     * @param sessionID ID de Sesion
     * @return Verdadero/Falso si la session es valida o no
     */
    @WebMethod
    public boolean isValidSession(@WebParam(name = "sessionID")String sessionID);
}
