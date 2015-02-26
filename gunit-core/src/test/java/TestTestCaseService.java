/*import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.SuitetestcaseRecord;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.services.TestCaseService;
import org.jooq.Record4;
import org.jooq.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by davida on 24.2.2015.

public class TestTestCaseService {
    static ServiceFacade facade;

    @BeforeClass
    public static void setup() {
        facade = Utils.getFacade();

        UserRecord user = facade.userService().getOrCreate("testuser");
        SessionRecord session = facade.sessionService().create(user.getId());

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
        TestsuiteresultRecord suite = facade.testSuiteService().createResult(result, session);

        facade.testCaseService().createTestCase(testCase,suite.getId());
        facade.testCaseService().createTestCase(tc2,suite.getId());

    }

    @Test
    public void testGetNewlyAddedTestCases() {
        Result<Record4<Integer, Integer, String, String>> results = facade.testCaseService().getTestCasesByAuthor();

        Record4<Integer,Integer,String,String> r = results.stream().findFirst().get();

        assertEquals("should be 1", 2, r.value2(), 0.1);
    }

    @Test
    public void testUpdate() {
        TestCaseService tcs = facade.testCaseService();

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
*/