package edu.chl.gunit.core;

import com.google.inject.Inject;
import edu.chl.gunit.core.services.*;

/**
 * Created by davida on 23.2.2015.
 */
@com.google.inject.Singleton
public class Facade {

    @Inject
    private UserService userService;

    @Inject
    private JaCoCoResultService jaCoCoResultService;

    @Inject
    private TestSuiteService testSuiteService;

    @Inject
    private TestCaseService testCaseService;

    @Inject
    private SessionService sessionService;

    public UserService getUserService() { return userService; }

    public JaCoCoResultService getJaCoCoResultService() {
        return jaCoCoResultService;
    }

    public TestSuiteService getTestSuiteService() {
        return testSuiteService;
    }

    public TestCaseService getTestCaseService() {
        return testCaseService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }
}
