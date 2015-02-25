package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.tables.records.RuleRecord;
import org.jooq.Field;

import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 25.2.2015.
 */
public class RuleServiceImpl extends AbstractService<RuleRecord> implements edu.chl.gunit.core.services.RuleService {
    public RuleServiceImpl() {
        super(RULE);
    }

    @Override
    protected Field<Integer> idField() {
        return RULE.ID;
    }
}
