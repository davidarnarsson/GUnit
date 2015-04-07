package edu.chl.gunit.service;

import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.DBProvider;
import edu.chl.gunit.core.data.Statistics;
import edu.chl.gunit.core.services.SessionService;
import edu.chl.gunit.service.health.DbHealthCheck;
import edu.chl.gunit.service.resources.*;
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
        environment.jersey().register(facade.getInjector().getInstance(BadgesResource.class));
        environment.jersey().register(facade.getInjector().getInstance(RulesResource.class));
        environment.jersey().register(facade.getInjector().getInstance(GamificationResource.class));
        environment.jersey().register(facade.getInjector().getInstance(UsersResource.class));
        environment.jersey().register(new StatisticsResource(facade));
        environment.jersey().register(facade.getInjector().getInstance(SessionResource.class));
        environment.jersey().register(facade.getInjector().getInstance(CoverageResource.class));
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/static/fonts","/fonts", null, "fonts"));
        bootstrap.addBundle(new AssetsBundle("/static/css","/css", null, "css"));
        bootstrap.addBundle(new AssetsBundle("/static/js","/js", null, "js"));
        bootstrap.addBundle(new AssetsBundle("/static/images","/images", null, "images"));
        bootstrap.addBundle(new AssetsBundle("/static","/site", "index.html", "static"));
        super.initialize(bootstrap);
    }
}
