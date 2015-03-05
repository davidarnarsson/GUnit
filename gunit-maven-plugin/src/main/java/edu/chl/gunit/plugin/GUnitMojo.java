package edu.chl.gunit.plugin;

import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.commons.api.TestRunRequest;
import edu.chl.gunit.commons.api.ApiTestSuiteResults;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by davida on 4.2.2015.
 */
@Mojo( name = "send-reports" )
public class GUnitMojo extends AbstractMojo {

    @Parameter(property = "developerName")
    protected String developerName;

    @Parameter(property = "jacocoReports")
    private File jacocoReportCsvFolder;

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

            List<File> junitTestFiles = new ArrayList<File>();

            crawlDirectory(unitTestReportsFolder, filter, junitTestFiles);
            JUnitXMLReader reader = new JUnitXMLReader();
            for (File f : junitTestFiles) {
                try {
                    ApiTestSuiteResults results = reader.read(f.getAbsolutePath());
                    req.getTestResults().add(results);
                } catch (JUnitResultException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            getLog().warn("Could not read unit test reports folder!");
        }

        if (jacocoReportCsvFolder != null && jacocoReportCsvFolder.isDirectory()) {
            JaCoCoCSVReader reader = new JaCoCoCSVReader();

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.equalsIgnoreCase("jacoco.csv");
                }
            };

            List<File> jacocoFiles = new ArrayList<>();

            crawlDirectory(jacocoReportCsvFolder, filter, jacocoFiles);

            for (File jacocoFile : jacocoFiles) {
                try {
                    List<ApiJaCoCoResult> coverageResultList = reader.read(jacocoFile);

                    for (ApiJaCoCoResult result : coverageResultList) {
                        req.getCoverageResults().add(result);
                    }
                } catch (JaCoCoResultException e) {
                    e.printStackTrace();
                }
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

    private void crawlDirectory(File directory, FilenameFilter filter, List<File> filesFound) {
        crawlDirectory(directory,filter, filesFound,0, 5);
    }

    private void crawlDirectory(File directory, FilenameFilter filter, List<File> filesFound, int level, int max) {
        if (level >= max) return;

        filesFound.addAll(Arrays.asList(directory.listFiles(filter)));

        File[] subDirectories = directory.listFiles(pathname -> {
            return pathname.isDirectory();
        });

        for (File subDirectory : subDirectories) {
            crawlDirectory(subDirectory, filter, filesFound,level + 1, max);
        }
    }
}
