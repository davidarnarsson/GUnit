package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by davida on 26.2.2015.
 */
public interface UserBadgeService extends Service<UserbadgesRecord> {
    public List<UserbadgesRecord> getUserBadges(int userId);

    public UserbadgesRecord awardBadge(int userId, int badgeId, Timestamp date, Integer ruleId, int sessionId);
}
