package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.commons.api.ApiTestCase;
import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.tables.Suitetestcase;
import edu.chl.gunit.core.data.tables.records.SuitetestcaseRecord;
import org.jooq.*;

import static org.jooq.impl.DSL.*;
import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 23.2.2015.
 */
public class TestCaseServiceImpl extends AbstractService<SuitetestcaseRecord> implements edu.chl.gunit.core.services.TestCaseService {
    public TestCaseServiceImpl() {
        super(Suitetestcase.SUITETESTCASE);
    }

    @Override
    public SuitetestcaseRecord createTestCase(ApiTestCase tc, int suiteId) {
        try (DBContext ctx = ctx()) {
            return ctx.dsl.insertInto(Suitetestcase.SUITETESTCASE)
                    .set(SUITETESTCASE.CLASSNAME, tc.getClassName())
                    .set(SUITETESTCASE.ELAPSED, tc.getTimeElapsed())
                    .set(SUITETESTCASE.ERROR, tc.getError())
                    .set(SUITETESTCASE.NAME, tc.getName())
                    .set(SUITETESTCASE.SUCCEEDED, tc.getSucceeded())
                    .set(SUITETESTCASE.SUITEID, suiteId)
                    .returning()
                    .fetchOne();
        }
    }

    @Override
    public Result<Record4<Integer, Integer, String, String>> getTestCasesByAuthor() {
        /**
         *   select u.*, i.testId, i.name, i.className from testsuiteresult tsr
             inner join (
                select min(tc.id) as testId, tc.name, tc.className, tc.suiteId from suitetestcase tc
                 group by tc.name, tc.className) as i on i.suiteId = tsr.id
             inner join session s on s.sessionId = tsr.sessionId
             inner join user u on u.id = s.userId;

         *
         */
        try (DBContext ctx = ctx()) {
            return ctx.dsl.select(
                    field("u.id", Integer.class),
                    field("i.testId", Integer.class),
                    field("i.name", String.class),
                    field("i.className", String.class)
            )
                    .from(TESTSUITERESULT.as("tsr"))
                    .join(
                            select(min(field("tc.id")).as("testId"), field("tc.name"), field("tc.className"), field("tc.suiteId"))
                                    .from(SUITETESTCASE.as("tc"))
                                    .groupBy(field("tc.name"), field("tc.classname")).asTable("i")
                    ).on(field("i.suiteId").eq(field("tsr.id")))
                    .join(SESSION.as("s")).on(field("s.sessionId").eq(field("tsr.sessionId")))
                    .join(USER.as("u")).on(field("u.id").eq(field("s.userId"))).fetch();
        }
    }

    @Override
    protected Field<Integer> idField() {
        return SUITETESTCASE.ID;
    }
}
