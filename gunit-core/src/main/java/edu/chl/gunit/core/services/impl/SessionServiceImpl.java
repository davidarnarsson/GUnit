package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import org.jooq.Field;
import org.jooq.impl.DSL;

import static edu.chl.gunit.commons.api.SessionStatus.Failed;
import static edu.chl.gunit.commons.api.SessionStatus.New;
import static edu.chl.gunit.commons.api.SessionStatus.Processed;
import static edu.chl.gunit.core.data.Tables.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.selectFrom;

/**
 * Created by davida on 24.2.2015.
 */
public class SessionServiceImpl extends AbstractService<SessionRecord> implements edu.chl.gunit.core.services.SessionService {
    public SessionServiceImpl() {
        super(SESSION);
    }

    @Override
    public SessionRecord create(int userId) {
        try (DBContext ctx = ctx()) {
            return ctx.dsl.insertInto(SESSION)
                    .set(SESSION.SESSIONSTATUS, New.getStatusCode())
                    .set(SESSION.DATE, DSL.currentTimestamp())
                    .set(SESSION.USERID, userId)
                    .returning()
                    .fetchOne();
        }
    }

    private void setStatus(SessionRecord record, SessionStatus status) {
        try (DBContext ctx = ctx()) {
            ctx.dsl.update(SESSION)
                    .set(SESSION.SESSIONSTATUS, status.getStatusCode())
                    .where(SESSION.SESSIONID.eq(record.getSessionid()))
                    .execute();
        }

    }

    @Override
    public void setProcessed(SessionRecord record) {
        setStatus(record, Processed);
    }

    @Override
    public void setFailed(SessionRecord record) {
        setStatus(record, Failed);
    }

    @Override
    public SessionRecord getLatestSession(Integer userid,SessionStatus status) {
        try (DBContext ctx = ctx()) {
            return ctx.dsl.selectFrom(SESSION)
                            .where(SESSION.USERID.eq(userid)
                                    .and(SESSION.SESSIONSTATUS.eq(status.getStatusCode()))
                            ).orderBy(SESSION.DATE.desc())
                    .limit(1)
                    .fetchOne();
        }
    }

    @Override
    public SessionRecord getLatestSession(String userName) {
        try (DBContext ctx = ctx()) {
            return ctx.dsl.selectFrom(SESSION)
                    .where(SESSION.SESSIONID.eq(select(SESSION.SESSIONID).from(SESSION)
                            .join(USER).on(USER.ID.eq(SESSION.USERID).and(USER.NAME.eq(userName)))
                            .orderBy(SESSION.SESSIONID.desc()).limit(1)))
                    .fetchAny();
        }
    }


    @Override
    protected Field<Integer> idField() {
        return SESSION.SESSIONID;
    }
}
