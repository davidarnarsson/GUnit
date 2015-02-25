package edu.chl.gunit.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.chl.gunit.core.data.tables.Jacocoresult;
import edu.chl.gunit.core.data.tables.Session;
import edu.chl.gunit.core.data.tables.Testsuiteresult;
import edu.chl.gunit.core.services.*;

/**
 * Created by davida on 25.2.2015.
 */
public class ServiceFacade {

    public JaCoCoResultService jaCoCoResultService() {
        return injector.getInstance(JaCoCoResultService.class);
    }

    public RuleService ruleService() {
        return injector.getInstance(RuleService.class);
    }

    public SessionService sessionService() {
        return injector.getInstance(SessionService.class);
    }

    public TestCaseService testCaseService() {
        return injector.getInstance(TestCaseService.class);
    }

    public TestSuiteService testSuiteService() {
        return injector.getInstance(TestSuiteService.class);
    }

    public UserService userService() {
        return injector.getInstance(UserService.class);
    }


    private final Injector injector;

    private ServiceFacade(Injector injector) {
        this.injector = injector;
    }

    public static ServiceFacade get() {
        return get(new Module());
    }

    public static ServiceFacade get(Module module) {
        return new ServiceFacade(Guice.createInjector(module));
    }
}
