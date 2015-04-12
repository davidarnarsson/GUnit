package edu.chl.gunit.core.services.impl;


import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import org.jooq.Field;
import org.jooq.Result;

import javax.inject.Inject;
import javax.inject.Named;

import static edu.chl.gunit.core.data.tables.Jacocoresult.JACOCORESULT;
import static org.jooq.impl.DSL.field;
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
    public JacocoresultRecord createFromResult(ApiJaCoCoResult r, SessionRecord s) {
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
    public JacocoresultRecord getLatestJaCoCoResult(String packageName, String className) {
        try (DBContext ctx = ctx()) {

            Result<JacocoresultRecord> result = ctx.dsl.select(JACOCORESULT.as("j").fields()).from(JACOCORESULT.as("j"))
                    .join(Tables.SESSION.as("s")).on(
                            field("s.sessionId").eq(field("j.sessionId"))
                                    .and(field("s.sessionStatus").eq(SessionStatus.Processed.getStatusCode())))
                    .where(field("j.className").eq(className).and(field("j.packageName").eq(packageName)))
                    .orderBy(field("s.date").desc())
                    .limit(1)
                    .fetchInto(JACOCORESULT);

            return result.stream().findFirst().orElse(null);
        }
    }

    @Override
    protected Field<Integer> idField() {
        return JACOCORESULT.ID;
    }
}
