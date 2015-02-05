import edu.chl.gunit.plugin.input.junit.JUnitResultException;
import edu.chl.gunit.plugin.input.junit.JUnitXMLReader;
import edu.chl.gunit.plugin.input.junit.TestSuiteResults;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by davida on 4.2.2015.
 */
public class TestJUnitXmlReader {

    @Test
    public void testReadSuccessfully() {
        final String path = "C:\\Code\\GUnit\\test-project\\target\\surefire-reports\\TEST-TestWallet.xml";

        JUnitXMLReader reader = new JUnitXMLReader();
        try {
            TestSuiteResults results = reader.read(path);
            Assert.assertEquals(results.getName(), "TestWallet");
        } catch (JUnitResultException e) {
            Assert.fail("Threw exception");
            e.printStackTrace();
        }
    }

    @Test(expected = JUnitResultException.class)
    public void testReadNonexistentFile() throws JUnitResultException {
        final String path = "C:\\Non\\existent\\file.xml";

        JUnitXMLReader reader = new JUnitXMLReader();
        TestSuiteResults results = reader.read(path);
    }



}
