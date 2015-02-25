package edu.chl.gunit.core.data;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by davida on 23.2.2015.
 */
public class DBProvider {
    private final String connString;
    private final String username;
    private final String password;
    private Connection connection;

    public DBProvider(String connString, String user, String password) {
        this.connString = connString;
        this.username = user;
        this.password = password;
    }

    public DBProvider() {
        connString  = "jdbc:mysql://localhost/gunit";
        username = "gunit_user";;
        password = "startrek";
    }

    public DSLContext get(Connection conn) {
        return DSL.using(conn, SQLDialect.MYSQL);
    }
    public Connection get(Driver d, String conn, String user, String pw) throws SQLException {
        DriverManager.registerDriver(d);
        return DriverManager.getConnection(conn, user,pw);
    }

    public Connection get(String conn, String user, String pw) throws SQLException {
        return get(new com.mysql.jdbc.Driver(), conn, user, pw);
    }

    public Connection get() throws SQLException {
        return get(connString, username,password);
    }

    public DSLContext getContext() throws SQLException {
        Settings settings = new Settings();
        settings.setRenderSchema(false);
        return DSL.using(get(),settings);
    }

}
