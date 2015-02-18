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
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.jboss.shrinkwrap.resolver.api.maven.ScopeType.*;
import static org.jboss.shrinkwrap.resolver.api.maven.ScopeType.RUNTIME;

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

    @Parameter(
            property = "java.class.path",
            defaultValue = "${java.class.path}")
    private File javaClassPath;

    @Parameter(
            property = "java.library.path",
            defaultValue = "${java.library.path}")
    private File javaLibraryPath;

    @Parameter(property = "localRepository", defaultValue = "${localRepository}")
    private File localRepository;

    @Parameter(property = "project.file", defaultValue = "${project.file}")
    private File pomFile;


    public void execute() throws MojoExecutionException {
        getLog().info(getFileString(outputDirectory, "No output directory!"));
        getLog().info(getFileString(testOutputDirectory, "No test output directory!"));
        getLog().info(getFileString(testSourceDirectory, "No test source directory!"));

        MavenProject project = (MavenProject) getPluginContext().get("project");

        PomEquippedResolveStage stage = Maven.resolver().loadPomFromFile(pomFile);
        File[] libs = stage.importDependencies()
                .resolve()
                .withTransitivity()
                .asFile();



        if (testOutputDirectory == null) {
            throw new MojoExecutionException("Unable to locate test output directory!");
        }

        ClazzLoader loader = new ClazzLoader(testOutputDirectory, libs);
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
