package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.TestsmellRecord;

import java.util.List;

/**
 * Created by davida on 12.4.2015.
 */
public interface TestSmellService extends Service<TestsmellRecord> {
    void create(TestsmellRecord r);

    List<TestsmellRecord> getTestSmellsForSession(int sessionId);
}
