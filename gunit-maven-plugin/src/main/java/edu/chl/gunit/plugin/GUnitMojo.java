import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;

import edu.chl.gunit.commons.input.junit.JUnitResultException;
import edu.chl.gunit.commons.input.junit.JUnitXMLReader;
import edu.chl.gunit.service.TestRunRequest;
import edu.chl.gunit.service.client.Client;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FilenameFilter;

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
                    req.getTestResults().add(convert(results));
                } catch (JUnitResultException e) {
                    e.printStackTrace();
                }
            }

        }

        Client client = new Client();
        String uniqueId = client.submitTestRun(req);
        getLog().info("Sent test run info to GUnit server, got unique tracking ID: " + uniqueId);

        // sækja gögn

        // kalla í vefþjónustu með gögn + developername

        // 1. kalla í vefþjónustu með user + project info + date á síðustu gögnum
        // 2. ef meðtekið þá
        // 3.      birta gögn fyrir user
        // 5. annars
        // 6.   sofa í 1000ms og goto 1

    }

    private edu.chl.gunit.service.TestSuiteResults convert(TestSuiteResults r) {
        edu.chl.gunit.service.TestSuiteResults n = new edu.chl.gunit.service.TestSuiteResults();
        n.setErrors(r.getErrors());
        n.setFailures(r.getFailures());
        n.setName(r.getName());
        n.setSkipped(r.getSkipped());
        n.setTests(r.getTests());
        n.setTimeElapsed(r.getTimeElapsed());

        for (TestCase c : r.getTestCases()) {
            edu.chl.gunit.service.TestCase nc = new edu.chl.gunit.service.TestCase();

            nc.setClassName(c.getClassName());
            nc.setErrorMessage(c.getErrorMessage());
            nc.setErrorType(c.getErrorType());
            nc.setStackTrace(c.getStackTrace());
            nc.setSucceeded(c.getSucceeded());
            nc.setTimeElapsed(c.getTimeElapsed());

            n.getTestCases().add(nc);
        }

        return n;
    }
}
