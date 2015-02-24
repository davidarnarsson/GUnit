package edu.chl.gunit.service;


import com.google.inject.Guice;
import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.Facade;
import edu.chl.gunit.core.data.Processor;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;
import edu.chl.gunit.service.data.TestRunRequest;

import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

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
    private Facade facade;

    @Override
    public int submitTestRun(TestRunRequest request) {

        Processor processor = Guice.createInjector().getInstance(Processor.class);
        int sessionId = processor.process(request.getUser(), request.getCoverageResults(), request.getTestResults());

        /**
         * 1. submit everything to the database
         * 2. create a unique message key
         * 3. collect all the new ids and create a message queue message
         * 4. return the message key for the user to query at a later date
         * */

        return sessionId;
    }
}
