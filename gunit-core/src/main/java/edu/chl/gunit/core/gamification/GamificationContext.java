package edu.chl.gunit.core.gamification;

import edu.chl.gunit.core.data.tables.records.UserRecord;

/**
 * Created by davida on 23.2.2015.
 */
public class GamificationContext {
    private UserRecord user;

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

}
