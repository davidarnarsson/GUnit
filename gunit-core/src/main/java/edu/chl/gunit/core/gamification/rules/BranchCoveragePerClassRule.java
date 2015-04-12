package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.JaCoCoResultService;
import org.jooq.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davida on 7.4.2015.
 */
public class BranchCoveragePerClassRule implements PreRuleStrategy {

    @Inject
    JaCoCoResultService jaCoCoResultService;

    public BranchCoveragePerClassRule() {
    }

    public BranchCoveragePerClassRule(JaCoCoResultService service) {
        jaCoCoResultService = service;
    }

    class ClassResult {
        int points;
        String message;
    }

    @Override
    public RuleResult calculate(GamificationContext ctx, RuleRecord self) {
        List<JacocoresultRecord> affectedClasses = ctx.getJacocoresultRecords().stream()
                .filter(x -> x.getBranchcovered() > 0)
                .collect(Collectors.toList());


        List<JacocoresultRecord> lastResults = affectedClasses.stream()
                .map(x -> jaCoCoResultService.getLatestJaCoCoResult(x.getPackagename(), x.getClassname()))
                .filter(x -> x != null)
                .collect(Collectors.toList());


        // for each affected class do
        //      get the last result
        //      if not result then set lastBranch to 0
        //      calculate percentage of coverage for both last and current
        //      scale the total possible score based on the total branch complexity
        //      calculate points for each based on percentage -- score can be negative

        RuleResult result = new RuleResult();
        result.setRule(self);
        List<ClassResult> results = new ArrayList<>();

        for (JacocoresultRecord affectedClass : affectedClasses) {
            JacocoresultRecord lastResult = lastResults.stream()
                    .filter(x -> StringUtils.equals(x.getClassname(), affectedClass.getClassname()) && StringUtils.equals(x.getPackagename(), affectedClass.getPackagename()))
                    .findFirst().orElse(null);

            double lastBranchCoverage = 0.0;

            if (lastResult != null) {
                assert lastResult.getSessionid() != affectedClass.getSessionid();

                lastBranchCoverage = (lastResult.getBranchcovered() / (double) (lastResult.getBranchcovered() + lastResult.getBranchmissed())) * 100;
            }
            // median branch  complexity is 40
            int maxPoints = Math.max(1, (int)Math.round(Math.min(5, (affectedClass.getBranchcovered() + affectedClass.getBranchmissed()) / 40.0 * 5)));

            double currentBranchCoverage = (affectedClass.getBranchcovered() / (double) (affectedClass.getBranchcovered() + affectedClass.getBranchmissed())) * 100;
            //(int)Math.floor(Math.min(5.0, (currentBranchCoverage * (5 / 75.0))))
            if (Math.abs(currentBranchCoverage - lastBranchCoverage) >= 10) {
                ClassResult r = new ClassResult();
                r.points = (int) (Math.max(-1, Math.min(1, currentBranchCoverage - lastBranchCoverage)) * Math.round(Math.min(maxPoints, (currentBranchCoverage * (maxPoints / 50.0)))));
                r.message = String.format("%d stig fyrir að ná branch coverage klasans %s.%s úr %.0f%% %s í %.0f%%\n",
                        r.points,
                        affectedClass.getPackagename(),
                        affectedClass.getClassname(),
                        lastBranchCoverage,
                        (currentBranchCoverage >= lastBranchCoverage ? "upp" : "niður"),
                        currentBranchCoverage);
                results.add(r);
            }
        }

        if (results.size() > 0) {
            result.setMessage(results.stream().reduce("Vel gert! Þú fékkst: \n",(x, y) -> x + y.message, (a,b) -> a + b));
            result.setPointsAwarded(results.stream().mapToInt(r -> r.points).sum());
        }

        return result;
    }
}
