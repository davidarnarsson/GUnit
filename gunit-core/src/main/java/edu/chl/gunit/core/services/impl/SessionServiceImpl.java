package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.tables.records.SessionRecord;
import org.jooq.Field;
import org.jooq.impl.DSL;

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
        return ctx().insertInto(SESSION)
                .set(SESSION.DATE, DSL.currentTimestamp())
                .set(SESSION.USERID, userId)
                .returning()
                .fetchOne();
    }


    @Override
    protected Field<Integer> idField() {
        return SESSION.SESSIONID;
    }
}
