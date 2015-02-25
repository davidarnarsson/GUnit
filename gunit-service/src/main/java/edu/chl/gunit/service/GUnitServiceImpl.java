package edu.chl.gunit.service;


import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.gamification.TestDataRunner;
import edu.chl.gunit.core.gamification.TestRunRequest;

import javax.inject.Inject;
import javax.jws.WebService;

/**
 * Created by davida on 20.2.2015.
 */
@WebService(endpointInterface = "edu.chl.gunit.service.GUnitService")
public class GUnitServiceImpl implements GUnitService {

    @Override
    public String ping() {
        return "pong!";
    }

    @Override
    public int submitTestRun(TestRunRequest request) {
        Injector injector = Guice.createInjector(new Module());
        TestDataRunner runner = injector.getInstance(TestDataRunner.class);

        int sessionid = runner.initialize(request);

        Thread runThread = new Thread(runner);
        runThread.start();

        /**
         * 1. submit everything to the database
         * 2. create a unique message key
         * 3. collect all the new ids and create a message queue message
         * 4. return the message key for the user to query at a later date
         * */

        return sessionid;
    }
}
