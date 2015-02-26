package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;

import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.tables.records.RuleRecord;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by davida on 23.2.2015.
 */
public class RuleFactory {

    @Inject
    private edu.chl.gunit.core.services.RuleService ruleService;

    public List<Rule> getRules() {
        List<Rule> out = new ArrayList<>();

        List<RuleRecord> records = ruleService.getList();

        for (RuleRecord record : records) {
            try {
                RuleStrategy strat = (RuleStrategy) ServiceFacade.get().getInjector()
                        .getInstance(
                                getClass().getClassLoader().loadClass(record.getClassname())
                        );

                Rule u = new Rule(record, strat);
                out.add(u);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return out;
    }
}
