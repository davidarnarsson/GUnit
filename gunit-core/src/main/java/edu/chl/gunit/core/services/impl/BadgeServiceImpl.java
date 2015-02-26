package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.services.BadgeService;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static edu.chl.gunit.core.data.Tables.*;
import static org.jooq.impl.DSL.select;

/**
 * Created by davida on 26.2.2015.
 */
public class BadgeServiceImpl extends AbstractService<BadgeRecord> implements BadgeService {

    public BadgeServiceImpl() {
        super(BADGE);
    }

    @Override
    protected Field<Integer> idField() {
        return BADGE.ID;
    }

    @Override
    public List<BadgeRecord> getBadgesForUserSession(int sessionId) {
        return ctx().select(BADGE.fields()).from(BADGE).join(
                select(USERBADGES.BADGEID).from(USERBADGES).where(USERBADGES.SESSIONID.eq(sessionId))
        ).on(USERBADGES.BADGEID.eq(BADGE.ID)).fetch().into(BADGE);
    }
}
