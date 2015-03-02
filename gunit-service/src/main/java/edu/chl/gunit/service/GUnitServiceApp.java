package edu.chl.gunit.service;

import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.DBProvider;
import edu.chl.gunit.core.data.Statistics;
import edu.chl.gunit.service.health.DbHealthCheck;
import edu.chl.gunit.service.resources.AccessOriginFilter;
import edu.chl.gunit.service.resources.GamificationResource;
import edu.chl.gunit.service.resources.StatisticsResource;
import edu.chl.gunit.service.resources.UsersResource;
import io.dropwizard.Application;

import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by davida on 2.3.2015.
 */
public class GUnitServiceApp extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        new GUnitServiceApp().run(args);
    }

    @Override
    public String getName() {
        return "gunit-service";
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        DBProvider provider = new DBProvider(appConfig.getConnectionString(),appConfig.getUserName(), appConfig.getPassword());

        ServiceFacade facade = ServiceFacade.get(new Module(provider));

        environment.healthChecks().register("DatabaseCheck", new DbHealthCheck(appConfig));

        environment.jersey().register(AccessOriginFilter.class);
        environment.jersey().register(new GamificationResource(facade));
        environment.jersey().register(new UsersResource(facade));
        environment.jersey().register(new StatisticsResource(facade));
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/static","/site", "index.html", "static"));
        super.initialize(bootstrap);
    }
}
