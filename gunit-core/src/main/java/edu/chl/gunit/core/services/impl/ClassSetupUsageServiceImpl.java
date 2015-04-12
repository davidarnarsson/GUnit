package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.commons.api.SetupUsage;
import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.ClasssetupusageRecord;
import edu.chl.gunit.core.services.ClassSetupUsageService;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 12.4.2015.
 */
public class ClassSetupUsageServiceImpl extends AbstractService<ClasssetupusageRecord> implements ClassSetupUsageService {
    public ClassSetupUsageServiceImpl() {
        super(Tables.CLASSSETUPUSAGE);
    }

    @Override
    protected Field<Integer> idField() {
        return Tables.CLASSSETUPUSAGE.ID;
    }

    @Override
    public ClasssetupusageRecord create(SetupUsage x, int sessionId) {
        try (DBContext dsl = ctx()) {
            return dsl.dsl.insertInto(Tables.CLASSSETUPUSAGE)
                    .set(CLASSSETUPUSAGE.CLASSNAME, x.getClassName())
                    .set(CLASSSETUPUSAGE.DEADSETUPFIELDS, x.getDeadFields().size())
                    .set(CLASSSETUPUSAGE.TESTSMELLS, x.getDeadFields().size() + x.getInlineSetupMethods().size() + x.getGeneralFixtureMethods().size() + x.getDetachedMethods().size())
                    .set(CLASSSETUPUSAGE.SESSIONID, sessionId)
                    .returning()
                    .fetchOne();
        }
    }
}
