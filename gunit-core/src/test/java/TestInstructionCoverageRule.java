
import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.Statistics;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.rules.InstructionCoverageRule;
import edu.chl.gunit.core.gamification.rules.RuleResult;
import edu.chl.gunit.core.services.SessionService;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by davida on 7.3.2015.
 */


public class TestInstructionCoverageRule {

    @Test
    public void testCalculatePositivePoints() {

        SessionService mockService = mock(SessionService.class);
        Statistics statistics = new Statistics();
        statistics.setInstructionCoverage(0.20);

        when(mockService.getLatestSession(1, SessionStatus.Processed)).thenReturn(new SessionRecord(1,1,null,0.0,0.0,0.30,0,0,0,0));

        InstructionCoverageRule rule = new InstructionCoverageRule(mockService);

        GamificationContext ctx = new GamificationContext(statistics,new SessionRecord(1,1,null,0.0,0.0,0.0,0,0,0,0),null,null,null);

        RuleResult result = rule.calculate(ctx, new RuleRecord());

        assertEquals("points to be -3", -3, result.getPointsAwarded());
    }

    @Test
    public void testCalculateNegativePoints() {

        SessionService mockService = mock(SessionService.class);
        Statistics statistics = new Statistics();
        statistics.setInstructionCoverage(0.301);

        when(mockService.getLatestSession(1, SessionStatus.Processed)).thenReturn(new SessionRecord(1,1,null,0.0,0.0,0.29,0,0,0,0));

        InstructionCoverageRule rule = new InstructionCoverageRule(mockService);

        GamificationContext ctx = new GamificationContext(statistics,new SessionRecord(1,1,null,0.0,0.0,0.0,0,0,0,0),null,null,null);

        RuleResult result = rule.calculate(ctx, new RuleRecord());

        assertEquals("points to be 3", 3, result.getPointsAwarded());
    }


    @Test
    public void testCalculatePointsNullPreviousSession() {

        SessionService mockService = mock(SessionService.class);
        when(mockService.getLatestSession(1, SessionStatus.Processed)).thenReturn(null);

        Statistics statistics = new Statistics();
        statistics.setInstructionCoverage(0.201);

        InstructionCoverageRule rule = new InstructionCoverageRule(mockService);

        GamificationContext ctx = new GamificationContext(statistics,new SessionRecord(1,1,null,0.0,0.0,0.201,0,0,0,0),null,null,null);

        RuleResult result = rule.calculate(ctx, new RuleRecord());

        assertEquals("points to be 2", 2, result.getPointsAwarded());
    }
}
