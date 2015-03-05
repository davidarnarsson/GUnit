package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.commons.api.ApiTestSuiteResults;
import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.tables.Testsuiteresult;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;
import org.jooq.Field;

import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 23.2.2015.
 */
public class TestSuiteServiceImpl extends AbstractService<TestsuiteresultRecord> implements edu.chl.gunit.core.services.TestSuiteService {

    public TestSuiteServiceImpl() {
        super(Testsuiteresult.TESTSUITERESULT);
    }

    @Override
    public TestsuiteresultRecord createResult(ApiTestSuiteResults result, SessionRecord session) {
        try (DBContext ctx = ctx()) {
            return ctx.dsl.insertInto(TESTSUITERESULT)
                    .set(TESTSUITERESULT.ELAPSED, result.getTimeElapsed())
                    .set(TESTSUITERESULT.ERRORS, result.getErrors())
                    .set(TESTSUITERESULT.FAILURES, result.getFailures())
                    .set(TESTSUITERESULT.NAME, result.getName())
                    .set(TESTSUITERESULT.SKIPPED, result.getSkipped())
                    .set(TESTSUITERESULT.TESTS, result.getTests())
                    .set(TESTSUITERESULT.SESSIONID, session.getSessionid())
                    .returning().fetchOne();
        }
    }

    @Override
    protected Field<Integer> idField() {
        return TESTSUITERESULT.ID;
    }
}
