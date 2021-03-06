package edu.chl.gunit.core.data;

import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by davida on 2.3.2015.
 */
public class DBContext implements AutoCloseable{
    public DSLContext dsl;
    public Connection conn;

    public DBContext(Connection connection, Settings settings) {
        this.conn = connection;
        this.dsl = DSL.using(this.conn, settings);

    }

    public void close() {
        try {
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
