package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.Session;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import static edu.chl.gunit.core.data.Tables.*;
import static org.jooq.impl.DSL.*;

/**
 * Created by davida on 24.2.2015.
 */
public class SessionService extends AbstractService<SessionRecord> {
    public SessionService() {
        super(SESSION);
    }

    public SessionRecord create(int userId) {
        return ctx().insertInto(SESSION)
                .set(SESSION.DATE, DSL.currentTimestamp())
                .set(SESSION.USERID, userId)
                .returning()
                .fetchOne();
    }
}
