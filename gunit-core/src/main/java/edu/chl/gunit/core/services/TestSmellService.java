package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.TestsmellRecord;

/**
 * Created by davida on 12.4.2015.
 */
public interface TestSmellService extends Service<TestsmellRecord> {
    void create(TestsmellRecord r);
}
