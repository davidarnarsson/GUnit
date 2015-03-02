package edu.chl.gunit.core.services.impl;


import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import org.jooq.Field;

import javax.inject.Inject;
import javax.inject.Named;

import static edu.chl.gunit.core.data.tables.Jacocoresult.JACOCORESULT;
import static org.jooq.impl.DSL.timestamp;

/**
 * Created by davida on 23.2.2015.
 */
@Named
public class JaCoCoResultServiceImpl extends AbstractService<JacocoresultRecord> implements edu.chl.gunit.core.services.JaCoCoResultService {

    public JaCoCoResultServiceImpl() {
        super(JACOCORESULT);
    }

    @Inject
    private edu.chl.gunit.core.services.UserService userService;

    @Override
    public JacocoresultRecord createFromResult(JaCoCoResult r, SessionRecord s) {
        try (DBContext ctx = ctx()) {

            return ctx.dsl.insertInto(JACOCORESULT)
                    .set(JACOCORESULT.BRANCHCOVERED, r.getBranchCovered())
                    .set(JACOCORESULT.BRANCHMISSED, r.getBranchMissed())
                    .set(JACOCORESULT.CLASSNAME, r.getClassName())
                    .set(JACOCORESULT.COMPLEXITYCOVERED, r.getComplexityCovered())
                    .set(JACOCORESULT.COMPLEXITYMISSED, r.getComplexityMissed())
                    .set(JACOCORESULT.GROUPNAME, r.getGroupName())
                    .set(JACOCORESULT.INSTRUCTIONCOVERED, r.getInstructionCovered())
                    .set(JACOCORESULT.INSTRUCTIONMISSED, r.getInstructionMissed())
                    .set(JACOCORESULT.LINECOVERED, r.getLineCovered())
                    .set(JACOCORESULT.LINEMISSED, r.getLineMissed())
                    .set(JACOCORESULT.METHODCOVERED, r.getMethodCovered())
                    .set(JACOCORESULT.METHODMISSED, r.getMethodMissed())
                    .set(JACOCORESULT.PACKAGENAME, r.getPackageName())
                    .set(JACOCORESULT.SESSIONID, s.getSessionid())
                    .returning().fetchOne();
        }

    }

    @Override
    protected Field<Integer> idField() {
        return JACOCORESULT.ID;
    }
}
