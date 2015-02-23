package edu.chl.gunit.core.services;


import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;

import javax.inject.Inject;
import javax.inject.Named;

import static edu.chl.gunit.core.data.tables.Jacocoresult.JACOCORESULT;
import static org.jooq.impl.DSL.timestamp;

/**
 * Created by davida on 23.2.2015.
 */
@Named
public class JaCoCoResultService extends AbstractService<JacocoresultRecord> {

    public JaCoCoResultService() {
        super(JACOCORESULT);
    }

    @Inject
    private UserService userService;

    public JacocoresultRecord createFromResult(JaCoCoResult r, String username) {

        UserRecord user = userService.getOrCreate(username);

        JacocoresultRecord out = ctx().insertInto(JACOCORESULT)
                .set(JACOCORESULT.BRANCHCOVERED, r.getBranchCovered())
                .set(JACOCORESULT.BRANCHMISSED, r.getBranchMissed())
                .set(JACOCORESULT.CLASSNAME, r.getClassName())
                .set(JACOCORESULT.COMPLEXITYCOVERED, r.getComplexityCovered())
                .set(JACOCORESULT.COMPLEXITYMISSED, r.getComplexityMissed())
                .set(JACOCORESULT.DATE, timestamp(r.getDate()))
                .set(JACOCORESULT.GROUPNAME, r.getGroupName())
                .set(JACOCORESULT.INSTRUCTIONCOVERED, r.getInstructionCovered())
                .set(JACOCORESULT.INSTRUCTIONMISSED, r.getInstructionMissed())
                .set(JACOCORESULT.LINECOVERED, r.getLineCovered())
                .set(JACOCORESULT.LINEMISSED, r.getLineMissed())
                .set(JACOCORESULT.METHODCOVERED, r.getMethodCovered())
                .set(JACOCORESULT.METHODMISSED, r.getMethodMissed())
                .set(JACOCORESULT.PACKAGENAME, r.getPackageName())
                .set(JACOCORESULT.USERID, user.getId())
        .returning().fetchOne();

        return out;
    }

}
