package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.Testsuiteresult;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;
import org.jooq.impl.TableImpl;

/**
 * Created by davida on 23.2.2015.
 */
public class TestSuiteService extends AbstractService<TestsuiteresultRecord> {
    public TestSuiteService() {
        super(Testsuiteresult.TESTSUITERESULT);
    }
}
