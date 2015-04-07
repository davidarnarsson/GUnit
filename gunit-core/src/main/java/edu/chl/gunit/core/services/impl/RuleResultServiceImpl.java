package edu.chl.gunit.core.services.impl;

import static edu.chl.gunit.core.data.Tables.*;
import static org.jooq.impl.DSL.field;

import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.tables.records.RuleresultRecord;
import edu.chl.gunit.core.gamification.rules.RuleResult;
import edu.chl.gunit.core.services.RuleResultService;
import org.jooq.*;

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
            return results.stream().map(x -> new RuleresultRecord(null, x.getPointsAwarded(), sessionId, x.getRule().getId(), x.getMessage(),x.getRegardingUserId(),x.getRegardingBadgeId()))
                    .map(x -> ctx.dsl.insertInto(RULERESULT).set(x).returning().fetchOne())
                    .collect(Collectors.toList());
        }
    }

    public List<RuleresultRecord> getRuleResults(int userId, boolean all) {
        try (DBContext ctx = ctx()) {

            return ctx.dsl.select(RULERESULT.as("r").fields()).from(RULERESULT.as("r"))
                    .join(SESSION.as("s")).on(field("s.sessionId").eq(field("r.sessionId")))
                    .where(
                            RULERESULT.as("r").MESSAGE.isNotNull().and(RULERESULT.as("r").MESSAGE.notEqual("")).and(SESSION.as("s").USERID.eq(userId))
                    ).orderBy(SESSION.as("s").DATE.desc()).limit(15).fetchInto(RuleresultRecord.class);
        }
    }
}
