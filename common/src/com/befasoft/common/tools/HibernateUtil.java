package com.befasoft.common.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.math.BigInteger;
import java.sql.*;
import java.util.Date;

public class HibernateUtil {

    private static Log log = LogFactory.getLog(HibernateUtil.class);

    public static String last_error = "";

    private static int sessionCount = 0;

    protected static SessionFactory sessionFactory;
    private static Configuration configuration;

    private static final byte[] lock = new byte[0];

    static {
        try {
            configuration = new Configuration().configure();
            // Create the SessionFactory
            sessionFactory = configuration.buildSessionFactory();
        }
        catch (Throwable ex) {
            ex.printStackTrace();
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static ThreadLocal<Session> sessionPool = new ThreadLocal<Session>();

    public static Session currentSession() {
        Session result = sessionPool.get();
        // Open a new Session, if this Thread has none yet
        if (result == null || !result.isOpen() || !result.isConnected()) {
            result = sessionFactory.openSession();
            sessionCount++;
            //System.out.println("+++ OPEN HIBERNATE SESSION +++ "+sessionCount+" sessions");
            sessionPool.set(result);
        }
        return result;
    }

    public static Session openSession() {
        sessionCount++;
        //System.out.println("+++ OPEN HIBERNATE SESSION +++ "+sessionCount+" sessions");
        return sessionFactory.openSession();
    }


    public static void flush() {
        currentSession().flush();
    }

    public static Session getCurrentSession() {
        Session result = sessionPool.get();
        return result;
    }

    public static Session setCurrentSession(Session newSession) {
        Session oldSession = sessionPool.get();
        // Save a new Session, if this Thread has none yet
        sessionPool.set(newSession);
        return oldSession;
    }


    public static void closeSession() {
        Session s = sessionPool.get();
        if (s != null) {
            sessionCount--;
            //System.out.println("+++ CLOSE HIBERNATE SESSION +++ "+sessionCount+" sessions");
            s.close();
            sessionPool.set(null);
        }
    }

    public static void commit() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        tx.commit();
    }

    public static void rollback() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        tx.rollback();
    }

    /**
     * Rebuild the SessionFactory with the static Configuration.
     */
    public static void rebuildSessionFactory(Configuration cfg) {

        synchronized (lock) {
            try {
                sessionFactory = cfg.buildSessionFactory();
                sessionPool = new ThreadLocal<Session>();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("rebuild SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }

    }

    public static int executeInsert(String insertStm) {
        Session session = HibernateUtil.currentSession();
        int result = 0;
        try {
            result = executeInsert(session, insertStm);
            session.connection().commit();

        }
        catch (Exception e) {
            System.out.println("error ejecutando inser " + insertStm);
            e.printStackTrace();
        }

        return result;

    }

    public static int executeInsert(Session session, String insertStm) throws SQLException {
        Connection connection = session.connection();
        PreparedStatement ps = connection.prepareStatement(insertStm);
        int idCount = ps.executeUpdate();
        ps.close();
        return idCount;
    }

    public static int executeInsert2(Session session, String insertStm) {
        Connection connection = session.connection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertStm);
            int idCount = ps.executeUpdate();
            ps.close();
            return idCount;
        }
        catch (SQLException e) {
            log.error(e);
            last_error = e.getMessage();
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (SQLException e1) {
                    log.error(e);
                }
            }
            return 0;
        }
    }

    public static boolean executeInsert3(Session session, String insertStm) {
        Connection connection = session.connection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertStm);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e) {
            log.error(e);
            last_error = e.getMessage();
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (SQLException e1) {
                    log.error(e);
                }
            }
            return false;
        }
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static String getLast_error() {
        return last_error;
    }

    public static Date getDatabaseDatetime() {
        Session session = HibernateUtil.currentSession();
        String sql;
        if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE)
            sql = "SELECT TO_CHAR(SYSDATE, 'DD/MM/YYYY HH:MI:SS') FROM DUAL";
        else if (Constants.DB_TYPE == Constants.DB_TYPE_MSSQL)
            sql = "SELECT GETDATE()";
        else
            sql = "SELECT SYSDATE()";
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        if (Constants.DB_TYPE == Constants.DB_TYPE_ORACLE)
            return Converter.getDate((String) query.uniqueResult(), "dd/MM/yyyy HH:mm:ss");
        else
            return (Date) query.uniqueResult();
    }

    /**
     * Obtiene un valor real de la DB
     * @param session Sesion de DB
     * @param sql SQL para obtener el valor
     * @return Valor
     */
    public static double getSQLDoubleValue(Session session, String sql) {
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        Double result = (Double) query.uniqueResult();
        return result == null ? 0.0 : result;
    }

    /**
     * Obtiene un valor real de la DB
     * @param session Sesion de DB
     * @param sql SQL para obtener el valor
     * @return Valor
     */
    public static int getSQLIntValue(Session session, String sql) {
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        Object result = query.uniqueResult();
        return result == null ? 0 : result instanceof BigInteger ? ((BigInteger) result).intValue() : ((Integer) result);
    }

    /**
     * Obtiene una cadena de la DB
     * @param session Sesion de DB
     * @param sql SQL para obtener el valor
     * @return Valor
     */
    public static String getSQLStringValue(Session session, String sql) {
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        return (String) query.uniqueResult();
    }

    /**
     * Obtiene una fecha de la DB
     * @param session Sesion de DB
     * @param sql SQL para obtener el valor
     * @return Valor
     */
    public static Date getSQLDateValue(Session session, String sql) {
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setCacheable(false);
        return (Date) query.uniqueResult();
    }

}
