package edu.chl.gunit.core.services;

import edu.chl.gunit.commons.api.ApiTestSuiteResults;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;

/**
 * Created by davida on 25.2.2015.
 */
public interface TestSuiteService extends Service<TestsuiteresultRecord> {
    TestsuiteresultRecord createResult(ApiTestSuiteResults result, SessionRecord session);
}
