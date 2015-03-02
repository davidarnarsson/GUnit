import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestRunRequest;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.commons.input.jacoco.JaCoCoCSVReader;
import edu.chl.gunit.commons.input.jacoco.JaCoCoResultException;
import edu.chl.gunit.commons.input.junit.JUnitResultException;
import edu.chl.gunit.commons.input.junit.JUnitXMLReader;

import edu.chl.gunit.service.client.Client;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by davida on 4.2.2015.
 */
@Mojo( name = "send-reports" )
public class GUnitMojo extends AbstractMojo {

    @Parameter(property = "developerName")
    protected String developerName;

    @Parameter(property = "jacocoReports")
    private File jacocoReportCsv;

    @Parameter(property = "testhoundReports")
    private File testHoundReportDirectory;

    @Parameter(property = "unitTestReports")
    private File unitTestReportsFolder;

    @Parameter(property = "gunitWsLocation")
    private String gunitWsLocation;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hi there, " + (developerName != null ? developerName : "Unnamed" + "!"));


        if (developerName == null || "".equals(developerName)) {
            throw new MojoExecutionException("Developer name not set!");
        }

        TestRunRequest req = new TestRunRequest();
        req.setUser(developerName);



        if (unitTestReportsFolder != null && unitTestReportsFolder.isDirectory()) {

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("TEST") && name.endsWith("xml");
                }
            };

            JUnitXMLReader reader = new JUnitXMLReader();
            for (File f : unitTestReportsFolder.listFiles(filter)) {
                try {
                    TestSuiteResults results = reader.read(f.getAbsolutePath());
                    req.getTestResults().add(results);
                } catch (JUnitResultException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            getLog().warn("Could not read unit test reports folder!");
        }

        if (jacocoReportCsv != null && jacocoReportCsv.isFile()) {
            JaCoCoCSVReader reader = new JaCoCoCSVReader();
            try {
                List<JaCoCoResult> coverageResultList = reader.read(jacocoReportCsv);

                for (JaCoCoResult result : coverageResultList) {
                    req.getCoverageResults().add(result);
                }
            } catch (JaCoCoResultException e) {
                e.printStackTrace();
            }
        } else {
            getLog().warn("Could not read coverage reports folder!");
        }

        Client client = new Client(gunitWsLocation);

        try {
            int uniqueId = client.submitTestRun(req);
            getLog().info("Sent test run info to GUnit server, got unique tracking ID: " + uniqueId);

        }catch (Exception e) {
            getLog().warn("Unable to submit test run to gamification server!");
            StringWriter writer = new StringWriter();
            PrintWriter w = new PrintWriter(writer);
            e.printStackTrace(w);
            getLog().warn("Stacktrace: \n" + writer.toString());
        }



        // sækja gögn

        // kalla í vefþjónustu með gögn + developername

        // 1. kalla í vefþjónustu með user + project info + date á síðustu gögnum
        // 2. ef meðtekið þá
        // 3.      birta gögn fyrir user
        // 5. annars
        // 6.   sofa í 1000ms og goto 1

    }
}
