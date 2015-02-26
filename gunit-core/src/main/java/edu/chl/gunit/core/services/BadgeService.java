package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.BadgeRecord;

import java.util.List;

/**
 * Created by davida on 26.2.2015.
 */
public interface BadgeService extends Service<BadgeRecord> {

    public List<BadgeRecord> getBadgesForUserSession(int sessionId);
}
