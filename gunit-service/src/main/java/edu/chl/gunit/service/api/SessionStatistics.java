package edu.chl.gunit.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;

import java.util.List;

/**
 * Created by davida on 26.2.2015.
 */
public class SessionStatistics {

    private Session session;

    private User user;

    @JsonProperty
    public List<Badge> getBadgesEarned() {
        return badgesEarned;
    }

    public void setBadgesEarned(List<Badge> badgesEarned) {
        this.badgesEarned = badgesEarned;
    }

    public SessionStatistics() {
    }

    @JsonProperty
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private List<Badge> badgesEarned;
}
