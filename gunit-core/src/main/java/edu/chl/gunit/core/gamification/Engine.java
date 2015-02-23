package edu.chl.gunit.core.gamification;

import static org.jooq.impl.DSL.*;

import edu.chl.gunit.core.data.DBConnection;
import edu.chl.gunit.core.data.Gunit;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.Rule;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.gamification.rules.RuleStrategy;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.List;

import static org.jooq.impl.DSL.select;

/**
 * Created by davida on 23.2.2015.
 */
public class Engine {

    List<RuleStrategy> ruleStrategies;

    private final static Engine INSTANCE = new Engine();

    private Engine() {


    }

    public static Engine getInstance() {
        return INSTANCE;
    }

    public void calculatePoints() {

    }
}
