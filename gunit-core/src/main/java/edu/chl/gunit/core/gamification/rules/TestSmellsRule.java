package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;
import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.ClassSetupUsageService;
import edu.chl.gunit.core.services.SessionService;
import edu.chl.gunit.core.services.TestSmellService;

/**
 * Created by davida on 12.4.2015.
 */
public class TestSmellsRule implements PreRuleStrategy {

    @Inject
    TestSmellService testSmellService;

    @Inject
    ClassSetupUsageService classSetupUsageService;

    @Inject
    SessionService sessionService;

    @Override
    public RuleResult calculate(GamificationContext ctx, RuleRecord self) {

        SessionRecord lastSession = sessionService.getLatestSession(ctx.getSession().getUserid(), SessionStatus.Processed);

        RuleResult result = new RuleResult();
        result.setRule(self);

        if (ctx.getStatistics().getTestSmells() < lastSession.getTotaltestsmells()) {
            int pointsAwarded = lastSession.getTotaltestsmells() - ctx.getStatistics().getTestSmells();
            result.setMessage(String.format("Þú fjarlægðir %d prófkóðaþef!", lastSession.getTotaltestsmells() - ctx.getStatistics().getTestSmells()));
            result.setPointsAwarded(pointsAwarded);
        }

        return result;
    }
}
