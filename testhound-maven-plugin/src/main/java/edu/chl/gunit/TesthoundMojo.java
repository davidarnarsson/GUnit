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

import edu.chl.gunit.output.XmlReportGenerator;
import edu.chl.gunit.output.XmlSerializer;
import etse.core.classloader.ClazzLoader;
import etse.core.testorganizer.fixture.ClassSetupUsage;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.impl.ArtifactResolver;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.w3c.dom.Document;

import javax.inject.Inject;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


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


    @Inject
    protected RepositorySystem repositorySystem;

    /**
     * Used to look up Artifacts in the remote repository.
     *
     */
    @Inject
    protected ArtifactResolver artifactResolver;

    /**
     * List of Remote Repositories used by the resolver
     *
     * @parameter expression="${project.remoteArtifactRepositories}"
     * @readonly
     * @required
     */
    @Parameter(property = "project.remoteArtifactRepositories")
    protected List remoteRepositories;

    /**
     * Location of the local repository.
     *
     * @parameter expression="${localRepository}"
     * @readonly
     * @required
     */
    @Parameter(property = "localRepository")
    protected ArtifactRepository localRepository;


    public void execute() throws MojoExecutionException {

        MavenProject project = (MavenProject)getPluginContext().get("project");
        RepositorySystemSession session = newSession(project);

        // create a lib file list and add the project code into it
        List<File> libs = new ArrayList<>();

        String filename = String.format("%s%s%s.%s", project.getBuild().getDirectory(), File.separator, project.getBuild().getFinalName(), project.getArtifact().getType());

        libs.add(new File(filename));

        // resolve project deps
        for (Artifact a : project.getDependencyArtifacts()) {
            org.eclipse.aether.artifact.Artifact aetherArtifact = fromMavenArtifact(a);
            try {
                ArtifactRequest request = new ArtifactRequest(aetherArtifact, fromMavenRepos(remoteRepositories), null);
                ArtifactResult result = artifactResolver.resolveArtifact(session,request);

                libs.add(result.getArtifact().getFile());
            } catch (ArtifactResolutionException e) {
                throw new MojoExecutionException("Unable to resolve dependency " + aetherArtifact.toString());
            }
        }


        if (testOutputDirectory == null) {
            throw new MojoExecutionException("Unable to locate test output directory!");
        }

        // create a project class loader
        ClazzLoader loader = new ClazzLoader(testOutputDirectory, libs.toArray(new File[libs.size()]));
        ClassFinder finder = new ClassFinder(loader);

        // get the test class names to scan
        HashSet<String> classes = finder.getClassNames(testOutputDirectory);


        TestHoundRunner runner = new TestHoundRunner();
        File reportOut = new File(outputDirectory.getAbsolutePath() + "/testhound-reports");
        if (!reportOut.exists() && !reportOut.mkdir()) {
            throw new MojoExecutionException("Unable to make out directory " + reportOut.getAbsolutePath());
        }

        // run testhound
        List<ClassSetupUsage> result = runner.run(classes, loader, reportOut);


        // generate result xml
        XmlReportGenerator generator = new XmlReportGenerator();
        Document doc = generator.generate(result, new Date());

        XmlSerializer serializer = new XmlSerializer();

        try {
            FileWriter writer = new FileWriter(new File(reportOut.getAbsolutePath() + "/report.xml"));
            serializer.serialize(doc, writer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException("Unable to create report.xml file.");
        }
    }

    private List<RemoteRepository> fromMavenRepos(List remoteRepositories) {
        List<RemoteRepository> out = new ArrayList<>();
        for(Object repo : remoteRepositories) {
            MavenArtifactRepository r = (MavenArtifactRepository) repo;
            out.add(new RemoteRepository.Builder(r.getId(),null,r.getUrl()).build());
        }
        return out;
    }

    private org.eclipse.aether.artifact.Artifact fromMavenArtifact(Artifact a) {
        return new DefaultArtifact(
                a.getGroupId(),
                a.getArtifactId(),
                a.getClassifier(),
                a.getType(),
                a.getVersion());
    }


    private String getFileString(File f, String def) {
        if (f == null) {
            return def;
        }

        return f.getAbsolutePath();
    }

    private  RepositorySystemSession newSession(MavenProject project)
    {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

        LocalRepository localRepo = new LocalRepository(localRepository.getBasedir());
        session.setLocalRepositoryManager(repositorySystem.newLocalRepositoryManager(session,localRepo));

        return session;
    }
}
