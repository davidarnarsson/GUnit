import static org.junit.Assert.assertEquals;

import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.SuitetestcaseRecord;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.services.TestCaseService;
import edu.chl.gunit.core.services.impl.TestCaseServiceImpl;
import org.jooq.*;


import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by davida on 24.2.2015.
 */
public class TestTestCaseService {
    static UserRecord user;
    static SessionRecord session;
    static Module testModule;

    @BeforeClass
    public static void setup() {
        testModule = Utils.getTestModule();

        user = testModule.getUserService().getOrCreate("testuser");
        session = testModule.getSessionService().create(user.getId());

        TestSuiteResults result = new TestSuiteResults("Herp", 0.0, 1,0,0,0);
        TestCase testCase = new TestCase("Derp");
        testCase.setClassName("HerpDerp");
        testCase.setSucceeded(true);
        testCase.setTimeElapsed(0.0);
        result.addTestCase(testCase);
        TestCase tc2 = new TestCase("NotDerp");
        tc2.setClassName("HerpDerp");
        tc2.setSucceeded(true);
        tc2.setTimeElapsed(0.0);
        result.addTestCase(tc2);
        TestsuiteresultRecord suite = testModule.getTestSuiteService().createResult(result, session);

        testModule.getTestCaseService().createTestCase(testCase,suite.getId());
        testModule.getTestCaseService().createTestCase(tc2,suite.getId());

    }

    @Test
    public void testGetNewlyAddedTestCases() {
        Result<Record4<Integer, Integer, String, String>> results = testModule.getTestCaseService().getTestCasesByAuthor();

        Record4<Integer,Integer,String,String> r = results.stream().findFirst().get();

        assertEquals("should be 1", 1, r.value2(), 0.1);
    }

    @Test
    public void testUpdate() {
        TestCaseService tcs = testModule.getTestCaseService();

        SuitetestcaseRecord tc = tcs.get(1);

        assertEquals("name", "Derp", tc.getName());

        tc.setName("Derp2");

        tcs.update(tc);

        tc = tcs.get(1);

        assertEquals("name", "Derp2", tc.getName());

        tc = tcs.get(2);

        assertEquals("other entries' name should not be Derp2", "NotDerp", tc.getName());
    }
}
