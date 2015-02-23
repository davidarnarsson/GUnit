package edu.chl.gunit.service.client;

import edu.chl.gunit.service.GUnit;
import edu.chl.gunit.service.GUnitServiceImplService;
import edu.chl.gunit.service.TestRunRequest;

/**
 * Created by davida on 23.2.2015.
 */
public class Client {

    private GUnitServiceImplService service;

    private GUnit getService() {
        if (service == null) {
            service = new GUnitServiceImplService();
        }

        return service.getGUnitServiceImplPort();
    }

    public String submitTestRun(TestRunRequest request) {
        return getService().submitTestRun(request);
    }

    public String ping() {
        return getService().ping();
    }
}
