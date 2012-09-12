package com.vincomobile.model.manager;

import com.befasoft.common.model.EntityBean;
import com.befasoft.common.model.appbs.APPBS_CAMPOS;
import com.befasoft.common.model.manager.BeanManager;
import com.befasoft.common.tools.Converter;
import com.befasoft.common.tools.HibernateUtil;
import com.vincomobile.Constants;
import com.vincomobile.model.appdb.PEDIDOS;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Devtools.
 * Date: 11/04/2011
 */
public class PEDIDOS_MANAGER extends BeanManager {

    public static String BEAN_NAME ="PEDIDOS";

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

    /**
     * Lista un elemento que cumplan con el criterio de filtrado
     */
    public static PEDIDOS findFirstByFilter(Map filters) {
        return  (PEDIDOS) findFirstByFilter(BEAN_NAME, filters);
    }

    /**
     * Busca por el campo llave
     */
    public static PEDIDOS findByKey(Long pedido_num) {
        Map filters = new HashMap();
        filters.put("pedido_num", pedido_num);

        return findFirstByFilter(filters);
    }

    public static PEDIDOS findByKey(Session session, Long pedido_num) {
        Map filters = new HashMap();
        filters.put("pedido_num", pedido_num);

        return (PEDIDOS) findFirstByFilter(session, BEAN_NAME, filters);
    }

    public static PEDIDOS findByKey(Map keyValues) {
        EntityBean bean = new PEDIDOS();
        return (PEDIDOS) findByKey(bean, keyValues);
    }

    public static int findCountByFilter(Map filters){
        return findCountByFilter(BEAN_NAME, filters);
    }

    public static BigDecimal getNextPedidoNum() {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT PEDIDOS_SEQ.nextval FROM DUAL").setCacheable(false);
        return (BigDecimal) query.uniqueResult();        
    }

    public static Long getNextMobilePedidoNum(Long mobile_id, Long id_vendedor) {
        Session session = HibernateUtil.currentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT MAX(MOBILE_PEDIDO_NUM) FROM PEDIDOS WHERE MOBILE_ID = :mobile_id AND ID_VENDEDOR = :id_vendedor")
                .setParameter("mobile_id", mobile_id)
                .setParameter("id_vendedor", id_vendedor)
                .setCacheable(false);
        BigDecimal result = (BigDecimal) query.uniqueResult();
        return result == null || result.longValue() < Constants.FIRST_MOBILE_PED_NUM ? Constants.FIRST_MOBILE_PED_NUM : result.longValue() + 1;
    }

    public static Long getMobilePedidoNum(Session session, Long mobile_pedido_num, Long mobile_id, Long id_vendedor) {
        SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT PEDIDO_NUM FROM PEDIDOS WHERE MOBILE_PEDIDO_NUM = :mobile_pedido_num AND MOBILE_ID = :mobile_id AND ID_VENDEDOR = :id_vendedor")
                .setParameter("mobile_pedido_num", mobile_pedido_num)
                .setParameter("mobile_id", mobile_id)
                .setParameter("id_vendedor", id_vendedor)
                .setCacheable(false);
        BigDecimal result = (BigDecimal) query.uniqueResult();
        return result == null ? 0L : result.longValue();
    }

    public static Long getMobilePedidoNum(Session session, List<APPBS_CAMPOS> fields) {
        APPBS_CAMPOS pedido_num = null;
        APPBS_CAMPOS mobile_pedido_num = null;
        APPBS_CAMPOS mobile_id = null;
        APPBS_CAMPOS id_vendedor = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("PEDIDO_NUM".equals(campo.getNombre()))
                pedido_num = campo;
            if ("MOBILE_PEDIDO_NUM".equals(campo.getNombre()))
                mobile_pedido_num = campo;
            if ("MOBILE_ID".equals(campo.getNombre()))
                mobile_id = campo;
            if ("ID_VENDEDOR".equals(campo.getNombre()))
                id_vendedor = campo;
        }
        if (mobile_pedido_num != null && mobile_id != null && pedido_num != null && id_vendedor != null)
            return PEDIDOS_MANAGER.getMobilePedidoNum(session, Converter.getLong(mobile_pedido_num.getValue()), Converter.getLong(mobile_id.getValue()), Converter.getLong(id_vendedor.getValue()));
        else
            return 0L;
    }

    public static Long getMobileId_cliente(Session session, List<APPBS_CAMPOS> fields) {
        APPBS_CAMPOS mobile_id = null;
        APPBS_CAMPOS id_cliente = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("MOBILE_ID".equals(campo.getNombre()))
                mobile_id = campo;
            if ("ID_CLIENTE".equals(campo.getNombre()))
                id_cliente = campo;
        }
        if (id_cliente != null && mobile_id != null) {
            SQLQuery query = (SQLQuery) session.createSQLQuery("SELECT ID_CLIENTE FROM CLIENTES WHERE MOBILE_ID_CLIENTE = :mobile_id_cliente AND MOBILE_ID = :mobile_id")
                    .setParameter("mobile_id_cliente", Converter.getLong(id_cliente.getValue()))
                    .setParameter("mobile_id", Converter.getLong(mobile_id.getValue()))
                    .setCacheable(false);
            BigDecimal result = (BigDecimal) query.uniqueResult();
            return result == null ? 0L : result.longValue();
        } else
            return 0L;
    }

    public static Long getMobileId_direccion(Session session, List<APPBS_CAMPOS> fields, String field) {
        APPBS_CAMPOS mobile_id = null;
        APPBS_CAMPOS id_direccion = null;
        for (APPBS_CAMPOS campo : fields) {
            if ("MOBILE_ID".equals(campo.getNombre()))
                mobile_id = campo;
            if (field.equals(campo.getNombre()))
                id_direccion = campo;
        }
        if (mobile_id != null && id_direccion != null) {
            SQLQuery query = (SQLQuery) session.createSQLQuery(
                    "SELECT ID_DIRECCION FROM CLIENTES_DIRECCIONES WHERE MOBILE_ID = :mobile_id AND MOBILE_ID_DIRECCION = :mobile_id_direccion")
                    .setParameter("mobile_id", Converter.getLong(mobile_id.getValue()))
                    .setParameter("mobile_id_direccion", Converter.getLong(id_direccion.getValue()))
                    .setCacheable(false);
            BigDecimal result = (BigDecimal) query.uniqueResult();
            return result == null ? 0L : result.longValue();
        } else
            return 0L;
    }
}
