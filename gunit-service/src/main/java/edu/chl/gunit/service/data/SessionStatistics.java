package edu.chl.gunit.service.data;

import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;

import java.util.List;

/**
 * Created by davida on 26.2.2015.
 */
public class SessionStatistics {

    private SessionRecord session;

    private UserRecord user;

    public List<BadgeRecord> getBadgesEarned() {
        return badgesEarned;
    }

    public void setBadgesEarned(List<BadgeRecord> badgesEarned) {
        this.badgesEarned = badgesEarned;
    }

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

    public SessionRecord getSession() {
        return session;
    }

    public void setSession(SessionRecord session) {
        this.session = session;
    }

    private List<BadgeRecord> badgesEarned;
}
