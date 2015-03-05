package edu.chl.gunit.core.services;

import edu.chl.gunit.commons.api.ApiTestCase;
import edu.chl.gunit.core.data.tables.records.SuitetestcaseRecord;
import org.jooq.Record4;
import org.jooq.Result;

/**
 * Created by davida on 25.2.2015.
 */
public interface TestCaseService extends Service<SuitetestcaseRecord> {
    SuitetestcaseRecord createTestCase(ApiTestCase tc, int suiteId);

    Result<Record4<Integer, Integer, String, String>> getTestCasesByAuthor();
}
