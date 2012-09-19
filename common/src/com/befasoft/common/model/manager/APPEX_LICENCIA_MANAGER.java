package com.befasoft.common.model.manager;

import com.befasoft.common.actions.BaseAction;
import com.befasoft.common.business.webservices.SessionWS;
import com.befasoft.common.business.webservices.license.Licencia;
import com.befasoft.common.business.webservices.license.License;
import com.befasoft.common.business.webservices.license.LicenseAPIService;
import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPEX_LICENCIA;
import com.befasoft.common.model.appbs.APPEX_LICENCIA_ITEMS;
import com.befasoft.common.model.business.RSA_KEYS;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.xml.namespace.QName;
import java.lang.Exception;
import java.math.BigInteger;
import java.net.URL;
import java.security.*;
import java.util.*;

/**
 * Created by Devtools.
 * Date: 29/05/2012
 */
public class APPEX_LICENCIA_MANAGER extends BeanManager {

    private static String BEAN_NAME ="APPEX_LICENCIA";
    
    public static final int LICENCE_OK             = 0;
    public static final int LICENCE_ERROR          = 1;
    public static final int LICENCE_TRIAL_FINISH   = 2;
    public static final int LICENCE_FINISH         = 3;
    public static final int LICENCE_ABSOLETE       = 4;
    

    /**
     * Lista todos los elementos
     */
    public static List listAll() {
        return listAll(BEAN_NAME);
    }

    /**
     * Lista los elementos que cumplan con el criterio de filtrado
     */
    public static List findByFilter(Map filters) {
        return findByFilter(BEAN_NAME, filters);
    }

    public static List findByFilter(Session session, Map filters) {
        return findByFilter(session, BEAN_NAME, filters);
    }

    public static int findCountByFilter(Map<String, String> filter) {
        return findCountByFilter(HibernateUtil.currentSession(), BEAN_NAME, filter);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static APPEX_LICENCIA findFirstByFilter(Map filters) {
        return  (APPEX_LICENCIA) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_licencia"
     */
    public static APPEX_LICENCIA findByKey(Long id_licencia ) {
        Map filters = new HashMap();
        filters.put("id_licencia", id_licencia);

        return findFirstByFilter(filters);
    }

    public static APPEX_LICENCIA findByKey(Session session, Long id_licencia ) {
        Map filters = new HashMap();
        filters.put("id_licencia", id_licencia);

        return (APPEX_LICENCIA) findFirstByFilter(session, BEAN_NAME, filters);
    }

    public static APPEX_LICENCIA findByKey(Map keyValues) {
        EntityBean bean = new APPEX_LICENCIA();
        return (APPEX_LICENCIA) findByKey(bean, keyValues);
    }

    /**
     * Lista las licencias del cliente
     *
     * @param id_distribuidor Id. del distribuidor
     * @param id_aplicacion Id. de la aplicacion
     * @param id_cliente Id. de cliente
     * @return Licencias
     */
    public static List<APPEX_LICENCIA> listLicencias(Long id_distribuidor, String id_aplicacion, Long id_cliente) {
        Map filters = new HashMap();
        filters.put("id_distribuidor", id_distribuidor);
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_cliente", id_cliente);

        return findByFilter(filters);
    }

    /**
     * Busca la licencia
     *
     * @param id_distribuidor Id. del distribuidor
     * @param id_aplicacion Id. de la aplicacion
     * @param id_cliente Id. de cliente
     * @return Licencia
     */
    public static APPEX_LICENCIA findLicencia(Long id_distribuidor, String id_aplicacion, Long id_cliente) {
        Map filters = new HashMap();
        filters.put("id_distribuidor", id_distribuidor);
        filters.put("id_aplicacion", id_aplicacion);
        filters.put("id_cliente", id_cliente);

        return findFirstByFilter(filters);
    }

    public static APPEX_LICENCIA findLicencia(Long id_distribuidor, Long id_cliente) {
        return findLicencia(id_distribuidor, Constants.APP_NAME, id_cliente);
    }

    public static APPEX_LICENCIA findLicencia(Long id_cliente) {
        return findLicencia(APPBS_PARAMETROS_MANAGER.getLongParameter("LICENSE_DIST"), Constants.APP_NAME, id_cliente);
    }

    /**
     * Verifica si la licencia es correcta
     * @param licencia Licencia a comprobar
     *
     * @return Codigo de error
     *              0 - Correcta
     *              1 - Error de licencia
     *              2 - Expiro el tiempo de prueba
     *              3 - Expiro la licencia
     *              4 - Licencia desactualizada
     * @throws Exception Error criptografico
     */
    public static int checkLicencia(APPEX_LICENCIA licencia) throws Exception {
        if (licencia != null && licencia.getActiva()) {
            Calendar today = Calendar.getInstance();
            Calendar check = Calendar.getInstance();
            today.setTime(new Date());
            // Verifica si esta actualizada
            check.setTime(licencia.getFecha_actualizacion());
            check.add(Calendar.DAY_OF_YEAR, Constants.MAX_UPDATE_DAYS);
            if (check.before(today))
                return LICENCE_ABSOLETE;
            if (!licencia.getTrial_terminado()) {
                // Verifica si esta en periodo de pruebas
                check.setTime(licencia.getFecha_inicio());
                check.add(Calendar.DAY_OF_YEAR, licencia.getTrial_tiempo().intValue());
                if (check.before(today))
                    return LICENCE_TRIAL_FINISH;
            } else {
                // Verifica si se termino la licencia
                check.setTime(licencia.getFecha_final());
                if (check.before(today))
                    return LICENCE_FINISH;
            }
            // Genera la llave publica
            RSA_KEYS keys = new RSA_KEYS(new BigInteger(licencia.getHash_modulo()), new BigInteger(licencia.getHash_exponente()));
            PublicKey kPublic =  keys.getPublicKey();
            // Descifra la frase
            BASE64Decoder base64 = new BASE64Decoder();
            byte[] cipherText = base64.decodeBuffer(licencia.getHash_frase());
            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.DECRYPT_MODE, kPublic);
            byte[] decryptedText = rsaCipher.doFinal(cipherText);
            String dText = new String(decryptedText);
            // Genera el Hash
            String hashText = licencia.getPlainLicencia() + ":" + dText + ":" + APPBS_PARAMETROS_MANAGER.getStrParameter("LICENSE_SERVER");
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            sha1.update(hashText.getBytes());
            String hash_code = Converter.byteArray2Hex(sha1.digest());
            return hash_code.equals(licencia.getHash_code()) ? LICENCE_OK : LICENCE_ERROR;
        }
        return LICENCE_ERROR;
    }

    /**
     * Verifica si la licencia es correcta
     * @param id_distribuidor Id. del distribuidor
     * @param id_aplicacion Id. de la aplicacion
     * @param id_cliente Id. de cliente
     *
     * @return Codigo de error
     *              0 - Correcta
     *              1 - Error de licencia
     *              2 - Expiro el tiempo de prueba
     *              3 - Expiro la licencia
     *              4 - Licencia desactualizada
     * @throws Exception Error criptografico
     */
    public static int checkLicencia(Long id_distribuidor, String id_aplicacion, Long id_cliente) throws Exception {
        return checkLicencia(findLicencia(id_distribuidor, id_aplicacion, id_cliente));
    }

    public static int checkLicencia(Long id_distribuidor, Long id_cliente) throws Exception {
        return checkLicencia(findLicencia(id_distribuidor, Constants.APP_NAME, id_cliente));
    }

    public static int checkLicencia(Long id_cliente) throws Exception {
        return checkLicencia(findLicencia(APPBS_PARAMETROS_MANAGER.getLongParameter("LICENSE_DIST"), Constants.APP_NAME, id_cliente));
    }

    /**
     * Se conecta al servidor de licencias y actualiza la informacion de la licencia
     *
     * @param id_aplicacion Id. de la aplicación
     * @param id_distribuidor Id. del distribuidor
     * @param id_cliente Id. del cliente
     * @return Verdadero si se actualizo la licencia
     */
    public static boolean updateLicense(String id_aplicacion, Long id_distribuidor, Long id_cliente) {
        log.debug("updateLicense("+id_aplicacion+", "+id_distribuidor+", "+id_cliente+")");
        try {
            String lic_server = APPBS_PARAMETROS_MANAGER.getStrParameter("LICENSE_SERVER");
            // Inicia la sesion con el servidor
            String sessionId = SessionWS.createSession(lic_server, true);
            License licenseAPI = new LicenseAPIService(new URL(lic_server + "licenseAPI?wsdl"), new QName("http://webservices.license.befasoft.com/", "LicenseAPIService")).getLicenseAPIPort();
            List<Licencia> licenses = licenseAPI.getLicense(sessionId, id_distribuidor, id_aplicacion, id_cliente);
            SessionWS.finishSession(lic_server, sessionId, true);
            Session session = HibernateUtil.currentSession();
            Transaction tx = session.beginTransaction();
            APPEX_LICENCIA appex_licencia = null;
            try {
                for (Licencia licencia : licenses) {
                    appex_licencia = findByKey(licencia.getIdLicencia());
                    if (appex_licencia == null) {
                        appex_licencia = new APPEX_LICENCIA();
                        appex_licencia.setId_licencia(licencia.getIdLicencia());
                    }
                    appex_licencia.updateLicencia(licencia);
                    save(session, appex_licencia);
                    // Actualiza los valores de la licencia
                    if (appex_licencia.getItems() != null) {
                        for (int i = 0; i < appex_licencia.getItems().size(); i++) {
                            APPEX_LICENCIA_ITEMS items = appex_licencia.getItems().get(i);
                            if (!items.isUpdated())
                                delete(session, items);
                            else
                                save(session, items);
                        }
                    }
                }
                // Borra las licencias extras
                if (appex_licencia != null) {
                    List<APPEX_LICENCIA> licencias = listLicencias(id_distribuidor, id_aplicacion, id_cliente);
                    for (APPEX_LICENCIA lic : licencias) {
                        if (lic.getId_licencia().longValue() != appex_licencia.getId_licencia()) {
                            APPEX_LICENCIA_MANAGER.delete(session, lic);
                        }
                    }
                }
                tx.commit();
            }
            catch (Throwable e) {
                BaseAction.rollbackTransaction(tx, e);
                return false;
            }
            return licenses.size() > 0;
        } catch (Exception e) {
            log.debug(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateLicense(Long id_distribuidor, Long id_cliente) {
        return updateLicense(Constants.APP_NAME, id_distribuidor, id_cliente);
    }

    public static boolean updateLicense(Long id_cliente) {
        return updateLicense(Constants.APP_NAME, APPBS_PARAMETROS_MANAGER.getLongParameter("LICENSE_DIST"), id_cliente);
    }

}
