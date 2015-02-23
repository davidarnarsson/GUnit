package edu.chl.gunit.service.data;

import java.util.Date;

/**
 * Created by davida on 20.2.2015.
 */
public class GamificationRequest {

    private final String user;

    private final Date lastPollDate;

    public GamificationRequest(String user, Date lastPollDate) {
        this.user = user;
        this.lastPollDate = lastPollDate;
    }
}
