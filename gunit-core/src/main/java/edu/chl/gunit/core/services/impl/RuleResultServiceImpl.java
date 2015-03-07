package edu.chl.gunit.core.services.impl;

import static edu.chl.gunit.core.data.Tables.*;

import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.tables.records.RuleresultRecord;
import edu.chl.gunit.core.gamification.rules.RuleResult;
import edu.chl.gunit.core.services.RuleResultService;
import org.jooq.Field;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davida on 7.3.2015.
 */
public class RuleResultServiceImpl extends AbstractService<RuleresultRecord> implements RuleResultService {
    public RuleResultServiceImpl() {
        super(RULERESULT);
    }

    @Override
    protected Field<Integer> idField() {
        return RULERESULT.ID;
    }

    @Override
    public List<RuleresultRecord> persistResults(int sessionId, List<RuleResult> results) {
        try (DBContext ctx = ctx()) {
            return results.stream().map(x -> new RuleresultRecord(null, x.getPointsAwarded(), sessionId, x.getRule().getId(), x.getMessage()))
                    .map(x -> ctx.dsl.insertInto(RULERESULT).set(x).returning().fetchOne())
                    .collect(Collectors.toList());
        }

    }
}
