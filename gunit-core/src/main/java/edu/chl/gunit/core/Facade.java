package edu.chl.gunit.core;

import com.google.inject.Inject;
import edu.chl.gunit.core.services.JaCoCoResultService;
import edu.chl.gunit.core.services.TestSuiteService;
import edu.chl.gunit.core.services.UserService;

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

    public UserService getUserService() {
        return userService;
    }

    public JaCoCoResultService getJaCoCoResultService() {
        return jaCoCoResultService;
    }
}
