package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.data.DBConnection;

import edu.chl.gunit.core.data.tables.records.RuleRecord;
import org.jooq.DSLContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by davida on 23.2.2015.
 */
public class RuleFactory {



    public List<Rule> getRules() {
        List<Rule> out = new ArrayList<>();

        try {
            DSLContext create = DBConnection.getContext();

            List<RuleRecord> records = create.fetch(edu.chl.gunit.core.data.tables.Rule.RULE);

            for (RuleRecord record : records) {
                try {
                    RuleStrategy strat = (RuleStrategy) getClass().getClassLoader().loadClass(record.getClassname()).newInstance();
                    Rule u = new Rule(record, strat);
                    out.add(u);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException ignored) {

        }

        return out;
    }
}
