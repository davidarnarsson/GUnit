package edu.chl.linkedlist; /**
 * Created by davida on 21.1.2015.
 */

import edu.chl.coverage.CoverageRunner;
import etse.core.classloader.BCELAnalysableClass;
import etse.core.classloader.ClazzLoader;
import etse.core.testorganizer.fixture.FixtureAnalyser;
import etse.core.testorganizer.fixture.TestSuiteResult;
import etse.core.testorganizer.fixture.htmlreport.AllReportGenerator;
import etse.core.testorganizer.main.experiments.ExperiementMain;
import org.jacoco.core.analysis.IClassCoverage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;

public class TestStuff extends ExperiementMain {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        /*HashSet<String> testClasses = new HashSet<String>();

        testClasses.add("edu.david.linkedlisttests.LinkedListTests");
        File dir = new File("/Users/davida/Code/linkedlist/out/test/LinkedList");
        File libDir = new File("/Users/davida/Code/linkedlist/lib");
        ClazzLoader loader = new ClazzLoader(dir,libDir);
        URL u = dir.toURI().toURL();

        URLClassLoader loder = new URLClassLoader(new URL[] { u, libDir.toURI().toURL() });

        BCELAnalysableClass c = loader.loadBCELAnalyzableClass("edu.david.linkedlisttests.LinkedListTests");

        FixtureAnalyser analyser = new FixtureAnalyser(loader);
        AllReportGenerator allReportGenerator = new AllReportGenerator("/Users/davida/Code/linkedlist/out", "/Users/davida/Code/TestHound/report/HTML version/template");
        TestSuiteResult result = analyseTestSuite(testClasses,analyser,allReportGenerator);
        */



        CoverageRunner runner = new CoverageRunner(System.out);

        try {
            IClassCoverage coverages = runner.execute(LinkedList.class.getName());

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
