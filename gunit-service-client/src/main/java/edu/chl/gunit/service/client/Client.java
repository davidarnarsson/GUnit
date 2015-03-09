package edu.chl.gunit.service.client;


import edu.chl.gunit.commons.api.ApiSession;
import edu.chl.gunit.commons.api.ApiUser;
import edu.chl.gunit.commons.api.ApiUserBadge;
import edu.chl.gunit.commons.api.TestRunRequest;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    public List<LinkedHashMap<String,String>> getLeaderboard() {
        return target.path("/game/leaderboard")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<LinkedHashMap<String,String>>>(new ArrayList<LinkedHashMap<String,String>>().getClass()));
    }

    public List<ApiUserBadge> getBadgesForUser(int userid) {
        return target.path("/users/badge/" + userid)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ApiUserBadge>>(new ArrayList<ApiUserBadge>().getClass()));
    }

    public ApiUser getUser(int userId) {
        return target.path("/users/" + userId)
                .request(MediaType.APPLICATION_JSON)
                .get(ApiUser.class);
    }

    public ApiSession getLatestSession(String userName) {
        return target.path("/session/latest")
                .queryParam("username", new String[]{userName})
                .request(MediaType.APPLICATION_JSON)
                .get(ApiSession.class);
    }
}
