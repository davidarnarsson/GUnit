package edu.chl.gunit.core.gamification;

import edu.chl.gunit.core.data.Statistics;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.SuitetestcaseRecord;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;

import java.util.List;

/**
 * Created by davida on 25.2.2015.
 */
public class GamificationContext {

    private final Statistics statistics;
    private final SessionRecord session;
    private final List<JacocoresultRecord> jacocoresultRecords;
    private final List<TestsuiteresultRecord> testsuiteresultRecords;

    public List<SuitetestcaseRecord> getSuitetestcaseRecords() {
        return suitetestcaseRecords;
    }

    public List<TestsuiteresultRecord> getTestsuiteresultRecords() {
        return testsuiteresultRecords;
    }

    public List<JacocoresultRecord> getJacocoresultRecords() {
        return jacocoresultRecords;
    }

    public SessionRecord getSession() {
        return session;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    private final List<SuitetestcaseRecord> suitetestcaseRecords;

    public GamificationContext(Statistics statistics, SessionRecord session, List<JacocoresultRecord> jacocoresultRecords, List<TestsuiteresultRecord> testsuiteresultRecords, List<SuitetestcaseRecord> suitetestcaseRecords) {

        this.statistics = statistics;
        this.session = session;
        this.jacocoresultRecords = jacocoresultRecords;
        this.testsuiteresultRecords = testsuiteresultRecords;
        this.suitetestcaseRecords = suitetestcaseRecords;
    }
}
