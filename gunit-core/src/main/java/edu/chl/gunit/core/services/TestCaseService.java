package edu.chl.gunit.core.services;

import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.core.data.tables.Suitetestcase;
import edu.chl.gunit.core.data.tables.records.SuitetestcaseRecord;
import org.jooq.Record4;
import org.jooq.Table;

import java.util.List;
import static org.jooq.impl.DSL.*;
import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 23.2.2015.
 */
public class TestCaseService extends AbstractService<SuitetestcaseRecord> {
    public TestCaseService() {
        super(Suitetestcase.SUITETESTCASE);
    }

    public SuitetestcaseRecord createTestCase(TestCase tc, int suiteId) {
        return ctx().insertInto(Suitetestcase.SUITETESTCASE)
                .set(SUITETESTCASE.CLASSNAME, tc.getClassName())
                .set(SUITETESTCASE.ELAPSED,tc.getTimeElapsed())
                .set(SUITETESTCASE.ERROR, tc.getError())
                .set(SUITETESTCASE.NAME, tc.getName())
                .set(SUITETESTCASE.SUCCEEDED, tc.getSucceeded())
                .set(SUITETESTCASE.SUITEID, suiteId)
                .returning()
                .fetchOne();
    }

    public List<SuitetestcaseRecord> getNewlyAddedTestCases() {
        /**
         *

         *
         */
        ctx().execute

    }
}
