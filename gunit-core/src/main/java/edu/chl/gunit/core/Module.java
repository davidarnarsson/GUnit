package edu.chl.gunit.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.chl.gunit.core.data.DBProvider;
import edu.chl.gunit.core.services.*;
import edu.chl.gunit.core.services.impl.*;
import edu.chl.gunit.core.services.impl.JaCoCoResultServiceImpl;
import edu.chl.gunit.core.services.impl.RuleServiceImpl;
import edu.chl.gunit.core.services.impl.SessionServiceImpl;
import edu.chl.gunit.core.services.impl.TestSuiteServiceImpl;
import edu.chl.gunit.core.services.impl.UserServiceImpl;

/**
 * Created by davida on 23.2.2015.
 */
@com.google.inject.Singleton
public class Module extends AbstractModule {

    private final DBProvider provider;

    public Module() {
        this(new DBProvider());
    }

    @Override
    protected void configure() {
        bind(DBProvider.class).toInstance(provider);
        bind(JaCoCoResultService.class).to(JaCoCoResultServiceImpl.class);
        bind(RuleService.class).to(RuleServiceImpl.class);
        bind(SessionService.class).to(SessionServiceImpl.class);
        bind(TestCaseService.class).to(TestCaseServiceImpl.class);
        bind(TestSuiteService.class).to(TestSuiteServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserBadgeService.class).to(UserBadgeServiceImpl.class);
        bind(BadgeService.class).to(BadgeServiceImpl.class);
        bind(RuleResultService.class).to(RuleResultServiceImpl.class);
        bind(ClassSetupUsageService.class).to(ClassSetupUsageServiceImpl.class);
        bind(TestSmellService.class).to(TestSmellServiceImpl.class);
    }

    public Module(DBProvider provider) {
        this.provider = provider;
    }
}
