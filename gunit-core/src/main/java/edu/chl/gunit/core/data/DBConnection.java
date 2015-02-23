package edu.chl.gunit.core.data;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by davida on 23.2.2015.
 */
public class DBConnection {
    private final static String connString = "jdbc:mysql://localhost/gunit";
    private final static String username = "gunit_user";
    private final static String password = "startrek";



    public static Connection get() throws SQLException {
        return DriverManager.getConnection(connString, username,password);
    }

    public static DSLContext getContext() throws SQLException {
        return DSL.using(DBConnection.get());
    }

}
