package edu.chl.gunit.service;


import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.data.Processor;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
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

    @Inject
    private Module module;

    @Override
    public int submitTestRun(TestRunRequest request) {
        TestDataRunner runner = new TestDataRunner(request);
        int sessionid = runner.initialize();

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
