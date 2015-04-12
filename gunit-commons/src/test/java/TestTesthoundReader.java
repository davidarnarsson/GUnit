import edu.chl.gunit.commons.api.TesthoundResult;
import edu.chl.gunit.commons.input.testhound.TesthoundXMLReader;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;

/**
 * Created by davida on 12.4.2015.
 */
public class TestTesthoundReader {

    @Test
    public void testReadClassSetupUsage() throws XPathExpressionException {

        TesthoundXMLReader reader = new TesthoundXMLReader();
        TesthoundResult r = reader.readTesthoundData(new File(getClass().getResource("report.xml").getFile()));

        Assert.assertEquals("result contains two class usages",2, r.getClassSetupUsages().size());

        Assert.assertEquals("result should contain detached method problems ", 9, r.getClassSetupUsages().get(0).getDetachedMethods().size());
        Assert.assertNotNull("result classname should not be null", r.getClassSetupUsages().get(0).getClassName());
    }
}
