
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import edu.chl.gunit.core.data.Statistics;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.rules.BranchCoveragePerClassRule;
import edu.chl.gunit.core.gamification.rules.RuleResult;
import edu.chl.gunit.core.services.JaCoCoResultService;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 7.4.2015.
 */
public class TestBranchCoveragePerClassRule {

    @Test
    public void testGetNoClasses() {
        JaCoCoResultService mockService = mock(JaCoCoResultService.class);

        when(mockService.getLatestJaCoCoResult("org.test", "Testclass", 1)).thenReturn(null);
        GamificationContext mockCtx = mock(GamificationContext.class);
        List<JacocoresultRecord> records = new ArrayList<>();
        records.add(new JacocoresultRecord(1, "Testclass", "org.test", null, 1, 0, 1, 0, 1, 0, 2, 1, 1, 0, 2));
        when(mockCtx.getJacocoresultRecords()).thenReturn(records);
        when(mockCtx.getSession()).thenReturn(new SessionRecord(2,1, Timestamp.from(Instant.now()),0.0,0.0,0.0,0,0,0,0));

        BranchCoveragePerClassRule rule = new BranchCoveragePerClassRule(mockService);

        RuleResult r = rule.calculate(mockCtx, null);

        assertTrue("Rule should not give points", r.getPointsAwarded() == 0);
    }

    @Test
    public void testGetPointsForClass() {
        JaCoCoResultService mockService = mock(JaCoCoResultService.class);

        when(mockService.getLatestJaCoCoResult("org.test", "Testclass", 1)).thenReturn(new JacocoresultRecord(1, "Testclass", "org.test", null, 1, 0, 100, 0, 1, 0, 2, 1, 1, 0, 1));
        GamificationContext mockCtx = mock(GamificationContext.class);
        List<JacocoresultRecord> records = new ArrayList<>();
        records.add(new JacocoresultRecord(1, "Testclass", "org.test", null, 1, 0, 80, 20, 1, 0, 2, 1, 1, 0, 2));
        when(mockCtx.getJacocoresultRecords()).thenReturn(records);
        when(mockCtx.getSession()).thenReturn(new SessionRecord(2,1, Timestamp.from(Instant.now()),0.0,0.0,0.0,0,0,0,0));

        BranchCoveragePerClassRule rule = new BranchCoveragePerClassRule(mockService);

        RuleResult r = rule.calculate(mockCtx, null);

        assertTrue("Rule should give 2 points", r.getPointsAwarded() == 2);
    }

    @Test
    public void testGetMaxPointsForClass() {
        JaCoCoResultService mockService = mock(JaCoCoResultService.class);

        when(mockService.getLatestJaCoCoResult("org.test", "Testclass", 1)).thenReturn(new JacocoresultRecord(1, "Testclass", "org.test", null, 1, 0, 100, 0, 1, 0, 2, 1, 1, 0, 1));
        GamificationContext mockCtx = mock(GamificationContext.class);
        List<JacocoresultRecord> records = new ArrayList<>();
        records.add(new JacocoresultRecord(1, "Testclass", "org.test", null, 1, 0, 40, 60, 1, 0, 2, 1, 1, 0, 2));
        when(mockCtx.getJacocoresultRecords()).thenReturn(records);
        when(mockCtx.getSession()).thenReturn(new SessionRecord(2,1, Timestamp.from(Instant.now()),0.0,0.0,0.0,0,0,0,0));

        BranchCoveragePerClassRule rule = new BranchCoveragePerClassRule(mockService);

        RuleResult r = rule.calculate(mockCtx, null);

        assertTrue("Rule should give 5 points", r.getPointsAwarded() == 5);
    }
}
