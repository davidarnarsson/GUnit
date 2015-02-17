package edu.chl.gunit;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import etse.core.classloader.ClazzLoader;
import etse.core.testorganizer.fixture.ClassSetupUsage;
import etse.core.testorganizer.fixture.FieldIdentifier;
import etse.core.testorganizer.fixture.TestSuiteResult;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Goal which runs the TestHound test smell detecting tool by
 * Greiler et al. on the unit testing project referenced by the
 * pom.xml
 */
@Mojo(name = "analyze",defaultPhase = LifecyclePhase.VERIFY,requiresReports = true)
public class TesthoundMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     */
    @Parameter(
            property = "project.build.directory",
            defaultValue = "${project.build.directory}")
    private File outputDirectory;

    /**
     * Location of the test output
     * */

    @Parameter(
            property = "project.build.testOutputDirectory",
            defaultValue = "${project.build.testOutputDirectory}")
    private File testOutputDirectory;

    /**
     * Location of the test source directory
     * */

    @Parameter(
            property = "project.build.testSourceDirectory",
            defaultValue = "${project.build.testSourceDirectory}")
    private File testSourceDirectory;


    public void execute() throws MojoExecutionException {
        getLog().info(getFileString(outputDirectory, "No output directory!"));
        getLog().info(getFileString(testOutputDirectory, "No test output directory!"));
        getLog().info(getFileString(testSourceDirectory, "No test source directory!"));

        if (testOutputDirectory == null) {
            throw new MojoExecutionException("Unable to locate test output directory!");
        }

        ClazzLoader loader = new ClazzLoader(testOutputDirectory);
        ClassFinder finder = new ClassFinder(loader);

        HashSet<String> classes = finder.getClassNames(testOutputDirectory);

        TestHoundRunner runner = new TestHoundRunner();
        File reportOut = new File(outputDirectory.getAbsolutePath() + "/testhound-reports");
        if (!reportOut.exists() && !reportOut.mkdir()) {
            throw new MojoExecutionException("Unable to make out directory " + reportOut.getAbsolutePath());
        }

        List<ClassSetupUsage> result = runner.run(classes, loader, reportOut);

        Log l = getLog();
        l.info("TestHound results: ");
        for(ClassSetupUsage u : result) {
            l.info(u.getTestCaseName());
            l.info("\tDead fields: ");

            Set<FieldIdentifier> keys = u.getDeadFields().keySet();
            for(FieldIdentifier k : keys) {
                l.info("\t\t" + k.getFieldName());
            }
        }
    }



    private String getFileString(File f, String def) {
        if (f == null) {
            return def;
        }

        return f.getAbsolutePath();
    }
}
