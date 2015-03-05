package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by davida on 26.2.2015.
 */
public class SessionStatistics {

    private ApiSession session;

    private ApiUser user;

    @JsonProperty
    public List<ApiBadge> getBadgesEarned() {
        return badgesEarned;
    }

    public void setBadgesEarned(List<ApiBadge> badgesEarned) {
        this.badgesEarned = badgesEarned;
    }

    public SessionStatistics() {
    }

    @JsonProperty
    public ApiUser getUser() {
        return user;
    }

    public void setUser(ApiUser user) {
        this.user = user;
    }

    @JsonProperty
    public ApiSession getSession() {
        return session;
    }

    public void setSession(ApiSession session) {
        this.session = session;
    }

    private List<ApiBadge> badgesEarned;
}
