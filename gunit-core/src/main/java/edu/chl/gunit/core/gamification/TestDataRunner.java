package edu.chl.gunit.core.gamification;

import com.google.inject.Inject;
import edu.chl.gunit.commons.api.TestRunRequest;
import edu.chl.gunit.core.data.Processor;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.rules.RuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by davida on 23.2.2015.
 */
public class TestDataRunner implements Runnable {

    private SessionRecord session;
    private TestRunRequest request;

    @Inject
    private Processor processor;

    @Inject
    private Engine engine;

    public int initialize(TestRunRequest request) {
        assert this.request == null;

        this.request = request;
        session = processor.createNewProcessSession(this.request.getUser());
        return session.getSessionid();
    }

    public interface OnCompleteListener {
        public void onComplete();
    }

    private final List<OnCompleteListener> listeners = new ArrayList<>();

    public void addCompleteListener(OnCompleteListener l) {
        listeners.add(l);
    }

    public TestDataRunner() {
    }

    public TestDataRunner(Processor processor, Engine engine) {
        this.processor = processor;
        this.engine = engine;
    }

    @Override
    public void run() {
        assert this.request != null;

        try {
            GamificationContext results = processor.process(session, request.getCoverageResults(), request.getTestResults());

            List<RuleResult> ruleResults = engine.calculatePoints(results);

            processor.updateUserStatistics(results.getStatistics(), ruleResults, session);

            processor.markSessionAsProcessed(session);

        } catch (Exception e) {
            if (session != null) {
                processor.markSessionAsFailed(session);
            }

            e.printStackTrace();
            Logger.getLogger("TestDataRunner").log(Level.SEVERE, "Error when processing test results", e);
        }
        finally {
            this.request = null;
            this.session = null;
        }

    }
}
