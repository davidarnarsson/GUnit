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
public class Module {

    public edu.chl.gunit.core.services.JaCoCoResultService getJaCoCoResultService() {
        return getService(JaCoCoResultService.class);
    }

    public edu.chl.gunit.core.services.RuleService getRuleService() {
        return getService(RuleService.class);
    }

    public edu.chl.gunit.core.services.SessionService getSessionService() {
        return getService(SessionService.class);
    }

    public TestCaseService getTestCaseService() {
        return getService(TestCaseService.class);
    }

    public edu.chl.gunit.core.services.TestSuiteService getTestSuiteService() {
        return getService(TestSuiteService.class);
    }

    public edu.chl.gunit.core.services.UserService getUserService() {
        return getService(UserService.class);
    }

    private <T extends Service> T getService(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    public DBProvider getDBProvider() {
        return injector.getInstance(DBProvider.class);
    }

    private final Injector injector;

    public Module() {
        this(new DBProvider());
    }

    public Module(DBProvider provider) {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(DBProvider.class).toInstance(provider);

                bind(JaCoCoResultService.class).to(JaCoCoResultServiceImpl.class);
                bind(RuleService.class).to(RuleServiceImpl.class);
                bind(SessionService.class).to(SessionServiceImpl.class);
                bind(TestCaseService.class).to(TestCaseServiceImpl.class);
                bind(TestSuiteService.class).to(TestSuiteServiceImpl.class);
                bind(UserService.class).to(UserServiceImpl.class);
            }
        });
    }

    public Injector getInjector() {
        return injector;
    }
}
