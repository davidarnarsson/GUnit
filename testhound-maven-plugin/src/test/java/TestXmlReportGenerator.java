import edu.chl.gunit.output.XmlReportGenerator;
import edu.chl.gunit.output.XmlSerializer;
import etse.core.testorganizer.fixture.ClassSetupUsage;
import etse.core.testorganizer.fixture.SetupUsageProfile;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by davida on 19/02/15.
 */
public class TestXmlReportGenerator {

    @Test
    public void testEmptyClassUsage() throws Exception {
        List<ClassSetupUsage> usage = new ArrayList<>();

        XmlReportGenerator generator = new XmlReportGenerator();
        Document doc = generator.generate(usage, new Date());

        XmlSerializer serializer = new XmlSerializer();
        StringWriter writer = new StringWriter();

        serializer.serialize(doc, writer);

        System.out.println(writer.toString());
    }
}
