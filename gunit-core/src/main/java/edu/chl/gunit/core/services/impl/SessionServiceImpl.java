package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import org.jooq.Field;
import org.jooq.impl.DSL;

import static edu.chl.gunit.commons.api.SessionStatus.Failed;
import static edu.chl.gunit.commons.api.SessionStatus.New;
import static edu.chl.gunit.commons.api.SessionStatus.Processed;
import static edu.chl.gunit.core.data.Tables.*;

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
    protected Field<Integer> idField() {
        return SESSION.SESSIONID;
    }
}
