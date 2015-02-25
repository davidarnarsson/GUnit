package edu.chl.gunit.core.gamification;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.Processor;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.rules.RuleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 23.2.2015.
 */
public class TestDataRunner implements Runnable {

    private SessionRecord session;
    private TestRunRequest request;

    @Inject
    private Processor processor;

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

    @Override
    public void run() {
        assert this.request != null;

        GamificationContext results = processor.process(session,request.getCoverageResults(), request.getTestResults());

        Engine engine = new Engine();

        List<RuleResult> ruleResults = engine.calculatePoints(results);

        this.request = null;

    }
}
