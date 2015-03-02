package edu.chl.gunit.service.health;

import com.codahale.metrics.health.HealthCheck;
import edu.chl.gunit.core.data.DBProvider;
import edu.chl.gunit.service.AppConfig;

import java.sql.Connection;

/**
 * Created by davida on 2.3.2015.
 */
public class DbHealthCheck extends HealthCheck {


    private final AppConfig config;

    public DbHealthCheck(AppConfig config) {
        this.config = config;
    }

    @Override
    protected Result check() throws Exception {
        DBProvider provider = new DBProvider(config.getConnectionString(), config.getUserName(), config.getPassword());
        Connection conn = provider.get();

        if (conn == null) {
            return Result.unhealthy("Unable to get a database connection!");
        }
        return Result.healthy();
    }
}
