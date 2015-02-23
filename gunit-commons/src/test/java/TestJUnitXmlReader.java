import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.commons.input.junit.JUnitResultException;
import edu.chl.gunit.commons.input.junit.JUnitXMLReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by davida on 4.2.2015.
 */
public class TestJUnitXmlReader {

    @Test
    public void testReadSuccessfully() {
        final String path = getClass().getClassLoader().getResource("TEST-TestWallet.xml").getFile();
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
