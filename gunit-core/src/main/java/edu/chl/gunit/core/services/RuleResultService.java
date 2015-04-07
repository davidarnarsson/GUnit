package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.RuleresultRecord;
import edu.chl.gunit.core.gamification.rules.RuleResult;

import java.util.List;

/**
 * Created by davida on 7.3.2015.
 */
public interface RuleResultService extends Service<RuleresultRecord> {

    List<RuleresultRecord> persistResults(int sessionId, List<RuleResult> results);
    public List<RuleresultRecord> getRuleResults(int userId, boolean all);
}
