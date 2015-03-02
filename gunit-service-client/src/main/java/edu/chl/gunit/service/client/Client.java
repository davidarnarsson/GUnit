package edu.chl.gunit.service.client;


import edu.chl.gunit.commons.TestRunRequest;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by davida on 23.2.2015.
 */
public class Client {
    private String webServiceLocation;
    private WebTarget target;

    public Client(String webServiceLocation) {
        this.webServiceLocation = webServiceLocation;
        this.target = ClientBuilder.newBuilder()
                .withConfig(new ClientConfig())
                .build()
                .register(JacksonFeature.class)
                .target(webServiceLocation);

    }

    public int submitTestRun(TestRunRequest req) {
        Integer sessionId = target
                .path("/statistics")
                .request(MediaType.APPLICATION_JSON)
                .<Integer>post(Entity.entity(req, MediaType.APPLICATION_JSON), Integer.class);
        return sessionId;
    }
}
