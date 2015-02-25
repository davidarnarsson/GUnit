package edu.chl.gunit.service.client;

import edu.chl.gunit.service.GUnit;
import edu.chl.gunit.service.GUnitServiceImplService;
import edu.chl.gunit.service.TestRunRequest;

import javax.xml.ws.BindingProvider;
import java.net.URL;

/**
 * Created by davida on 23.2.2015.
 */
public class Client {


    private final URL wsLocation;

    public Client(URL wsLocation) {

        this.wsLocation = wsLocation;
    }

    private GUnitServiceImplService service;

    private GUnit getService() {
        if (service == null) {
            service = new GUnitServiceImplService();
        }

        GUnit port = service.getGUnitServiceImplPort();

        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsLocation.toString());

        return port;
    }

    public int submitTestRun(TestRunRequest request) {
        return getService().submitTestRun(request);
    }

    public String ping() {
        return getService().ping();
    }
}
