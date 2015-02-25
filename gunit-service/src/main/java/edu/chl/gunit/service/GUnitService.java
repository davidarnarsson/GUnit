package edu.chl.gunit.service;

import edu.chl.gunit.core.gamification.TestRunRequest;

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



}
