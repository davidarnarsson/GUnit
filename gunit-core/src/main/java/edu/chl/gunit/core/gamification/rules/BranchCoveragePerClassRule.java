package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.JaCoCoResultService;

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
                .filter(x -> x.getComplexitycovered() > 0)
                .collect(Collectors.toList());


        List<JacocoresultRecord> lastResults = affectedClasses.stream()
                .map(x -> jaCoCoResultService.getLatestJaCoCoResult(x.getPackagename(), x.getClassname(), ctx.getSession().getUserid()))
                .collect(Collectors.toList());


        // for each affected class do
        //      get the last result
        //      if not result then set lastBranch to 0
        //      calculate percentage of coverage for both last and current
        //      scale the total possible score based on the total cyclomatic complexity
        //      calculate points for each based on percentage -- score can be negative

        RuleResult result = new RuleResult();
        result.setRule(self);
        List<ClassResult> results = new ArrayList<>();

        for (JacocoresultRecord affectedClass : affectedClasses) {
            JacocoresultRecord lastResult = lastResults.stream()
                    .filter(x -> x.getClassname().equals(affectedClass.getClassname()) && x.getPackagename().equals(affectedClass.getPackagename()))
                    .findFirst().orElse(null);

            double lastBranchCoverage = 0.0;

            if (lastResult != null) {
                assert lastResult.getSessionid() != affectedClass.getSessionid();

                lastBranchCoverage = (lastResult.getBranchcovered() / (double) (lastResult.getBranchcovered() + lastResult.getBranchmissed())) * 100;
            }

            double currentBranchCoverage = (affectedClass.getBranchcovered() / (double) (affectedClass.getBranchcovered() + affectedClass.getBranchmissed())) * 100;
            //(int)Math.floor(Math.min(5.0, (currentBranchCoverage * (5 / 75.0))))
            if (Math.abs(currentBranchCoverage - lastBranchCoverage) >= 10) {
                ClassResult r = new ClassResult();
                r.points = (int) (Math.max(-1, Math.min(1, currentBranchCoverage - lastBranchCoverage)) * Math.round(Math.min(5.0, (currentBranchCoverage * (5 / 50.0)))));
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
