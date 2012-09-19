package com.befasoft.common.tools;

import org.hibernate.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCAccess {

    /**
     * Ejecuta una Query
     *
     * @param sql Query
     * @return ResultSet con los resultado
     * @throws SQLException Error en la ejecucion de la Query
     */
    public static ResultSet execQuery(String sql) throws SQLException {
        Session session = HibernateUtil.currentSession();
        Connection connection = session.connection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public static ResultSet execQuery(StringBuilder sql) throws SQLException {
        return execQuery(sql.toString());
    }

    /**
     * Obtiene un valor entero de una Query
     *
     * @param sql Query
     * @return Valor entero
     * @throws SQLException Error en la ejecucion de la Query
     */
    public static long getLongValue(String sql) throws SQLException {
        long result = 0;
        ResultSet rs = execQuery(sql);
        if (rs.next())
            result = rs.getLong(1);
        rs.close();
        return result;
    }

    public static long getLongValue(StringBuilder sql) throws SQLException {
        return getLongValue(sql.toString());
    }
}
