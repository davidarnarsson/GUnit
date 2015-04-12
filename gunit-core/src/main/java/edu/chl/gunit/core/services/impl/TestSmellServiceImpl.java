package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.TestsmellRecord;
import edu.chl.gunit.core.services.TestSmellService;
import org.jooq.Field;
import org.jooq.impl.TableImpl;
import static edu.chl.gunit.core.data.Tables.*;
/**
 * Created by davida on 12.4.2015.
 */
public class TestSmellServiceImpl extends AbstractService<TestsmellRecord> implements TestSmellService {
    public TestSmellServiceImpl() {
        super(Tables.TESTSMELL);
    }

    @Override
    protected Field<Integer> idField() {
        return Tables.TESTSMELL.ID;
    }

    @Override
    public void create(TestsmellRecord r) {
        try (DBContext ctx = ctx()) {
            ctx.dsl.insertInto(Tables.TESTSMELL)
                    .set(TESTSMELL.CLASSNAME, r.getClassname())
                    .set(TESTSMELL.CLASSSETUPUSAGEID, r.getClasssetupusageid())
                    .set(TESTSMELL.TESTCASENAME, r.getTestcasename())
                    .set(TESTSMELL.TYPE, r.getType())
                    .returning().fetchOne();
        }
    }
}
