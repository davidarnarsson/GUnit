package edu.chl.gunit.core.services;

import com.google.inject.Inject;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.Testsuiteresult;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import org.jooq.impl.TableImpl;
import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 23.2.2015.
 */
public class TestSuiteService extends AbstractService<TestsuiteresultRecord> {

    public TestSuiteService() {
        super(Testsuiteresult.TESTSUITERESULT);
    }

    public TestsuiteresultRecord createResult(TestSuiteResults result, SessionRecord session) {

        return ctx().insertInto(TESTSUITERESULT)
                .set(TESTSUITERESULT.ELAPSED, result.getTimeElapsed())
                .set(TESTSUITERESULT.ERRORS,result.getErrors())
                .set(TESTSUITERESULT.FAILURES, result.getFailures())
                .set(TESTSUITERESULT.NAME, result.getName())
                .set(TESTSUITERESULT.SKIPPED, result.getSkipped())
                .set(TESTSUITERESULT.TESTS, result.getTests())
                .set(TESTSUITERESULT.SESSIONID, session.getSessionid())
                .returning().fetchOne();
    }
}
