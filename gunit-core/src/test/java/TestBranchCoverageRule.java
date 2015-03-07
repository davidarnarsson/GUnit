import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.Statistics;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.rules.BranchCoverageRule;
import edu.chl.gunit.core.gamification.rules.InstructionCoverageRule;
import edu.chl.gunit.core.gamification.rules.RuleResult;
import edu.chl.gunit.core.services.SessionService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by davida on 7.3.2015.
 */
public class TestBranchCoverageRule {
    @Test
    public void testCalculatePositivePoints() {

        SessionService mockService = mock(SessionService.class);
        Statistics statistics = new Statistics();
        statistics.setBranchCoverage(50.0);

        when(mockService.getLatestSession(1, SessionStatus.Processed)).thenReturn(new SessionRecord(1,1,null,40.0,0.0,0.0,0,0,0,0));

        BranchCoverageRule rule = new BranchCoverageRule(mockService);

        GamificationContext ctx = new GamificationContext(statistics,new SessionRecord(1,1,null,0.0,0.0,0.0,0,0,0,0),null,null,null);

        RuleResult result = rule.calculate(ctx, new RuleRecord());

        assertEquals("points to be 8", 8, result.getPointsAwarded());
    }

    @Test
    public void testCalculateNegativePoints() {

        SessionService mockService = mock(SessionService.class);
        Statistics statistics = new Statistics();
        statistics.setBranchCoverage(10.1);

        when(mockService.getLatestSession(1, SessionStatus.Processed)).thenReturn(new SessionRecord(1,1,null,30.0,0.0,0.0,0,0,0,0));

        BranchCoverageRule rule = new BranchCoverageRule(mockService);

        GamificationContext ctx = new GamificationContext(statistics,new SessionRecord(1,1,null,0.0,0.0,0.0,0,0,0,0),null,null,null);

        RuleResult result = rule.calculate(ctx, new RuleRecord());

        assertEquals("points to be -4", -4, result.getPointsAwarded());
    }


    @Test
    public void testCalculatePointsNullPreviousSession() {

        SessionService mockService = mock(SessionService.class);
        when(mockService.getLatestSession(1, SessionStatus.Processed)).thenReturn(null);

        Statistics statistics = new Statistics();
        statistics.setBranchCoverage(20.1);

        BranchCoverageRule rule = new BranchCoverageRule(mockService);

        GamificationContext ctx = new GamificationContext(statistics,new SessionRecord(1,1,null,20.0,0.0,20.1,0,0,0,0),null,null,null);

        RuleResult result = rule.calculate(ctx, new RuleRecord());

        assertEquals("points to be 2", 2, result.getPointsAwarded());
    }
}
