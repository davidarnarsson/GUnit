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
    private final TestRunRequest request;

    @Inject
    private Processor processor;

    @Inject


    public int initialize() {
        session = processor.createNewProcessSession(request.getUser());
        return session.getSessionid();
    }

    public interface OnCompleteListener {
        public void onComplete();
    }

    private final List<OnCompleteListener> listeners = new ArrayList<>();

    public void addCompleteListener(OnCompleteListener l) {
        listeners.add(l);
    }

    public TestDataRunner(TestRunRequest request) {
        this.request = request;
    }

    @Override
    public void run() {

        GamificationContext results = processor.process(session,request.getCoverageResults(), request.getTestResults());

        Engine engine = new Engine();

        List<RuleResult> ruleResults = engine.calculatePoints(results);

    }
}
