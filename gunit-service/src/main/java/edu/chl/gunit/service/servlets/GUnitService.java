package edu.chl.gunit.service.servlets;

import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.GamificationResult;
import edu.chl.gunit.core.gamification.TestRunRequest;
import edu.chl.gunit.service.data.SessionStatistics;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by davida on 20.2.2015.
 */
@WebService(name="GUnit")
public interface GUnitService {
    @WebMethod
    String ping();

    /**
     * Submits a test run to the server to analyze
     * @return a unique test run session identifier
     * */
    @WebMethod
    int  submitTestRun(TestRunRequest request);

    @WebMethod
    SessionStatistics getSessionStatistics(int sessionId);

}
