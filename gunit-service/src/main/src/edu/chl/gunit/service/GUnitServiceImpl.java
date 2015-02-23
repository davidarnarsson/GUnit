package edu.chl.gunit.service;


import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.Facade;
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
    public String submitTestRun(TestRunRequest request) {

        List<JaCoCoResult> jaCoCoResults = request.getCoverageResults();

        if (jaCoCoResults != null && jaCoCoResults.size() > 0) {
            for (JaCoCoResult r : jaCoCoResults) {
                facade.getJaCoCoResultService().createFromResult(r,request.getUser());
            }
        }

        List<TestSuiteResults> testSuiteResults = request.getTestResults();
        if (testSuiteResults != null && testSuiteResults.size() > 0) {
            for (TestSuiteResults result : testSuiteResults) {
                TestsuiteresultRecord rc = facade.getTestSuiteService().createResult(result, request.getUser());

                for (TestCase tc : result.getTestCases()) {
                    facade.getTestCaseService().createTestCase(tc, rc.getId());
                }
            }
        }

        /**
         * 1. submit everything to the database
         * 2. create a unique message key
         * 3. collect all the new ids and create a message queue message
         * 4. return the message key for the user to query at a later date
         * */

        return "OMG HELLO DER!";
    }
}
