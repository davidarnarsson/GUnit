/**
 * Created by davida on 7.3.2015.
 */

import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.commons.api.ApiSession;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.service.api.Utils;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestUtilsClass {

    @Test
    public void testFromJaCoCo() {
        JacocoresultRecord r = new JacocoresultRecord(1, "2", "3", "4",5,6,7,8,9,10,11,12,13,14,15);

        ApiJaCoCoResult api = Utils.from(r);
        assertEquals("branchcovered",api.getBranchCovered(),r.getBranchcovered().intValue());
        assertEquals(api.getBranchMissed(),r.getBranchmissed().intValue());
        assertEquals(api.getClassName(), r.getClassname());
        assertEquals(api.getComplexityCovered(), r.getComplexitycovered().intValue());
        assertEquals(api.getComplexityMissed(), r.getComplexitymissed().intValue());
        assertEquals(api.getGroupName(), r.getGroupname());
        assertEquals(api.getInstructionCovered(), r.getInstructioncovered().intValue());
        assertEquals(api.getInstructionMissed(), r.getInstructionmissed().intValue());
        assertEquals(api.getLineCovered(), r.getLinecovered().intValue());
        assertEquals(api.getLineMissed(), r.getLinemissed().intValue());
        assertEquals(api.getMethodCovered(), r.getMethodcovered().intValue());
        assertEquals(api.getMethodMissed(), r.getMethodmissed().intValue());
        assertEquals(api.getPackageName(), r.getPackagename());
    }


    @Test
    public void testFromSession() {
        SessionRecord r = new SessionRecord(1,2,new Timestamp(3),4.0,5.0,6.0,7,8,9,10);

        ApiSession a = Utils.from(r);
        assertEquals(a.getBadgesEarned(), r.getBadgesearned());
        assertEquals(a.getBranchCoverage(),r.getBranchcoverage());
        assertEquals(a.getDate(),r.getDate());
        assertEquals(a.getInstructionCoverage(),r.getInstructioncoverage());
        assertEquals(a.getLineCoverage(),r.getLinecoverage());
        assertEquals(a.getNewTests(),r.getNewtests());
        assertEquals(a.getPointsCollected(),r.getPointscollected());
        assertEquals(a.getSessionId(),r.getSessionid());
        assertEquals(a.getSessionStatus().getStatusCode(), r.getSessionstatus().intValue());
        assertEquals(a.getUserId(),r.getUserid());
    }
}
