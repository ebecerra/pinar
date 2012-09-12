package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.Constants;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.model.appdb.CLIENTES;
import com.vincomobile.model.appdb.CLIENTES_CONTACTOS;
import com.vincomobile.model.appdb.CLIENTES_DIRECCIONES;
import com.vincomobile.model.business.USER_DEMO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 14/04/2011
 */
public class CLIENTES_MANAGER extends BeanManager {

    public static String BEAN_NAME = "CLIENTES";

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

    public static List findByFilter(Map filters, String sort, String dir) {
        return findByFilter(BEAN_NAME, filters, -1, -1, sort, dir);
    }

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static CLIENTES findFirstByFilter(Map filters) {
        return  (CLIENTES) findFirstByFilter(BEAN_NAME, filters);
    }

    public static CLIENTES findFirstByFilter(Session session, Map filters) {
        return  (CLIENTES) findFirstByFilter(session, BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     * "id_cliente"
     */
    public static CLIENTES findByKey(Long id_cliente ) {
        Map filters = new HashMap();
        filters.put("id_cliente", id_cliente);

        return findFirstByFilter(filters);
    }

    public static CLIENTES findByKey(Map keyValues) {
        EntityBean bean = new CLIENTES();
        return (CLIENTES) findByKey(bean, keyValues);
    }

    public static CLIENTES findByRef_web(String ref_web) {
        Map filters = new HashMap();
        filters.put("ref_web", ref_web);

        return findFirstByFilter(filters);
    }

    /**
     * Obtiene el proximo numero de cliente
     * @return Numero de cliente
     */
    public static Long getNextId_cliente() {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT CLIENTES_WEB_SEQ.nextval FROM DUAL").setCacheable(false);
        return ((BigDecimal) query.uniqueResult()).longValue();
    }

    /**
     * Lista clientes de un vendedor
     * @param id_vendedor Id. del vendedor
     * @param id_cliente Id. del cliente
     * @param numero Numero del cliente
     * @param cif CIF del cliente
     * @param nombre Nombre del cliente
     * @param start Elemento en el que se comienza
     * @param limit Cantidad de elementos
     * @return Lista de clientes
     */
    public static List<CLIENTES> getClientesByVendedorWeb(Long id_vendedor, Long id_cliente, Long numero, String cif, String nombre, int start, int limit) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(getSQLClientesByVendedorWeb(id_vendedor, id_cliente, numero, cif, nombre, false)).setCacheable(false);
        if (start != -1 && limit != -1) {
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }
        return query.setResultTransformer(Transformers.aliasToBean(CLIENTES.class)).list();
    }

    public static int countClientesByVendedorWeb(Long id_vendedor, Long id_cliente, Long numero, String cif, String nombre) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(getSQLClientesByVendedorWeb(id_vendedor, id_cliente, numero, cif, nombre, true)).setCacheable(false);
        BigDecimal count = (BigDecimal) query.uniqueResult();
        return count == null ? 0 : count.intValue();
    }

    private static String getSQLClientesByVendedorWeb(Long id_vendedor, Long id_cliente, Long numero, String cif, String nombre, boolean count) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(count ? "COUNT(*)" : "*").append(" FROM CLIENTES C ");
        sql.append("WHERE (C.EXPORTABLE = 'N' OR C.VALIDO = 'S')");
        sql.append(" AND EXISTS (SELECT 1 FROM CLIENTES_DIRECCIONES CD WHERE C.ID_CLIENTE = CD.ID_CLIENTE AND CD.ID_VENDEDOR = ").append(id_vendedor).append(")");
        if (id_cliente != null && id_cliente > 0)
            sql.append(" AND C.ID_CLIENTE = ").append(id_cliente);
        if (numero != null && numero > 0)
            sql.append(" AND C.NUMERO = ").append(numero);
        if (!Converter.isEmpty(cif))
            sql.append(" AND UPPER(C.CIF) LIKE '%").append(Converter.getSQLString(cif.toUpperCase(), false)).append("%'");
        if (!Converter.isEmpty(nombre))
            sql.append(" AND UPPER(C.NOMBRE) LIKE '%").append(Converter.getSQLString(nombre.toUpperCase(), false)).append("%'");
        if (!count)
            sql.append(" ORDER BY NOMBRE");
        return sql.toString();
    }

    /**
     * Verifica si un cliente es nacional
     * @param id_cliente Id. del cliente
     * @return Verdadero si es nacional
     */
    public static boolean checkNacional(Long id_cliente) {
        Map filter = new HashMap();
        filter.put("tipo", "FACTURACION");
        filter.put("principal", "Y");
        filter.put("id_pais", "ES");
        filter.put("id_cliente", id_cliente);
        return CLIENTES_DIRECCIONES_MANAGER.findFirstByFilter(filter) != null;
    }

    /**
     * Lista clientes de un vendedor
     * @param id_vendedor Id. del vendedor
     * @param id_cliente Id. del cliente
     * @param numero Numero del cliente
     * @param cif CIF del cliente
     * @param nombre Nombre del cliente
     * @param start Elemento en el que se comienza
     * @param limit Cantidad de elementos
     * @param sort Campo por el que se ordena
     * @param dir Direccion de ordenacion
     * @return Lista de clientes
     */
    public static List<CLIENTES> getClientesByVendedor(Long id_vendedor, Long id_cliente, Long numero, String cif, String nombre, int start, int limit, String sort, String dir) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(getSQLClientesByVendedor(id_vendedor, id_cliente, numero, cif, nombre, sort, dir, false)).setCacheable(false);
        if (start != -1 && limit != -1) {
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }
        List<CLIENTES> result = query.setResultTransformer(Transformers.aliasToBean(CLIENTES.class)).list();
        for (int i = 0; i < result.size(); i++) {
            CLIENTES cliente = result.get(i);
            cliente.setNacional(checkNacional(cliente.getId_cliente()));
        }
        return result;
    }

    public static int countClientesByVendedor(Long id_vendedor, Long id_cliente, Long numero, String cif, String nombre) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(getSQLClientesByVendedor(id_vendedor, id_cliente, numero, cif, nombre, null, null, true)).setCacheable(false);
        BigDecimal count = (BigDecimal) query.uniqueResult();
        return count == null ? 0 : count.intValue();
    }

    private static String getSQLClientesByVendedor(Long id_vendedor, Long id_cliente, Long numero, String cif, String nombre, String sort, String dir, boolean count) {
        StringBuilder sql = new StringBuilder();
        if (count)
            sql.append("SELECT COUNT(*) FROM (");
        else
            sql.append("SELECT * FROM (");
        sql.append("SELECT * FROM CLIENTES WHERE ID_AGENTE = ").append(id_vendedor);
        sql.append(" UNION ");
        sql.append("SELECT * FROM CLIENTES C ");
        sql.append("WHERE EXISTS (SELECT 1 FROM CLIENTES_DIRECCIONES CD WHERE C.ID_CLIENTE = CD.ID_CLIENTE AND CD.ID_VENDEDOR = ").append(id_vendedor).append(")");
        if (id_cliente != null && id_cliente > 0)
            sql.append(" AND C.ID_CLIENTE = ").append(id_cliente);
        if (numero != null && numero > 0)
            sql.append(" AND C.NUMERO = ").append(numero);
        if (!Converter.isEmpty(cif))
            sql.append(" AND UPPER(C.CIF) LIKE '%").append(Converter.getSQLString(cif.toUpperCase(), false)).append("%'");
        if (!Converter.isEmpty(nombre))
            sql.append(" AND UPPER(C.NOMBRE) LIKE '%").append(Converter.getSQLString(nombre.toUpperCase(), false)).append("%'");
        if (count)
            sql.append(")");
        else {
        	if (sort != null) {
        		sql.append(") ORDER BY ").append(sort).append(" ").append(dir);
        	}
        	else {
                sql.append(")");
        	}
        }
        return sql.toString();
    }

    /**
     * Verifica si un cliente es correcto
     * @param id_cliente Id. del cliente a verificar
     * @return Si el cliente es valido
     */
    public static boolean getValidCliente(Long id_cliente) {
        CLIENTES cliente = findByKey(id_cliente);
        if (cliente != null) {
            // Verifica las direcciones
            boolean fact = false, envio = false;
            for (int i = 0; i < cliente.getDirecciones().size(); i++) {
                CLIENTES_DIRECCIONES dir = cliente.getDirecciones().get(i);
                if ("FACTURACION".equals(dir.getTipo()))
                    fact = true;
                if ("ENVIO".equals(dir.getTipo()))
                    envio = true;
            }
            // Verifica los contactos
            if (fact && envio) {
                Map flt = new HashMap();
                flt.put("id_cliente", cliente.getId_cliente());
                List<CLIENTES_CONTACTOS> contactos = CLIENTES_CONTACTOS_MANAGER.findByFilter(flt);
                if (contactos != null && contactos.size() > 0)
                    return true;
            }
        }
        return false;
    }

    /**
     * Actualiza la informacion de un cliente
     * @param cliente Datos del cliente
     * @param login_info Informacion de login
     * @return Verdadero si se actualizo, falso si hay problemas con la lista de precios
     */
    public static boolean setUserInfo(CLIENTES cliente, USER_DEMO login_info) {
        login_info.setId_cliente(cliente.getId_cliente());
        login_info.setEmail(cliente.getEmail());
        login_info.setEmail_comercial(cliente.getEmail_comercial());
        login_info.setId_condpago(cliente.getId_condpago() == null ? 0 : cliente.getId_condpago());
//        login_info.setPrecio_punto("MEX".equals(id_marca) ? cliente.getPrecio_punto_mex() : cliente.getPrecio_punto_min());
        login_info.setId_iva(cliente.getId_iva());
        login_info.setCliente_select(true);
        login_info.setCliente_nombre(cliente.getNombre());
        login_info.setMultiplicador(cliente.getMultiplicador());
        login_info.setNumero(cliente.getNumero());
        return true;
    }

    public static Long getMobileId_cliente(Session session, Long mobile_id_cliente, Long mobile_id) {
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT ID_CLIENTE FROM CLIENTES WHERE MOBILE_ID_CLIENTE = :mobile_id_cliente AND MOBILE_ID = :mobile_id")
                .setParameter("mobile_id_cliente", mobile_id_cliente)
                .setParameter("mobile_id", mobile_id)
                .setCacheable(false);
        BigDecimal result = (BigDecimal) query.uniqueResult();
        return result == null ? 0L : result.longValue();
    }

    public static Long getMobileId_cliente(Session session, List<APPBS_CAMPOS> fields) {
        APPBS_CAMPOS mobile_id_cliente = null;
        APPBS_CAMPOS mobile_id = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("MOBILE_ID_CLIENTE".equals(campo.getNombre()))
                mobile_id_cliente = campo;
            if ("MOBILE_ID".equals(campo.getNombre()))
                mobile_id = campo;
        }
        if (mobile_id_cliente != null && mobile_id != null)
            return getMobileId_cliente(session, Converter.getLong(mobile_id_cliente.getValue()), Converter.getLong(mobile_id.getValue()));
        else
            return 0L;
    }

    public static Long getNextMobileId_cliente(Long mobile_id, Long id_vendedor) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(
                "SELECT MAX(C.MOBILE_ID_CLIENTE) FROM CLIENTES C JOIN CLIENTES_DIRECCIONES CD ON C.ID_CLIENTE = CD.ID_CLIENTE " +
                "WHERE C.MOBILE_ID = :mobile_id AND CD.ID_VENDEDOR = :id_vendedor")
                .setParameter("mobile_id", mobile_id)
                .setParameter("id_vendedor", id_vendedor)
                .setCacheable(false);
        BigDecimal result = (BigDecimal) query.uniqueResult();
        return result == null || result.longValue() < com.vincomobile.Constants.FIRST_MOBILE_ID_CLIENTE ? com.vincomobile.Constants.FIRST_MOBILE_ID_CLIENTE : result.longValue() + 1;
    }

}
