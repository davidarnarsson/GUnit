package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.tables.Userbadges;
import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;
import edu.chl.gunit.core.services.UserBadgeService;
import org.jooq.Field;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 26.2.2015.
 */
public class UserBadgeServiceImpl extends AbstractService<UserbadgesRecord> implements UserBadgeService {
    public UserBadgeServiceImpl() {
        super(USERBADGES);
    }

    @Override
    protected Field<Integer> idField() {
        return USERBADGES.USERID;
    }

    public List<UserbadgesRecord> getUserBadges(int userId) {
        return ctx().selectFrom(USERBADGES)
                .where(USERBADGES.USERID.eq(userId)).fetch()
                .stream().collect(Collectors.toList());
    }

    @Override
    public UserbadgesRecord awardBadge(int userId, int badgeId, Timestamp date, Integer ruleId, int sessionId) {
        UserbadgesRecord record = new UserbadgesRecord();

        record.setBadgeid(badgeId);
        record.setEarneddate(date);
        record.setFromruleid(ruleId);
        record.setUserid(userId);
        record.setSessionid(sessionId);


        return ctx().insertInto(USERBADGES).set(record).returning().fetchOne();
    }
}
