import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;

import java.io.File;

/**
 * Created by davida on 18/02/15.
 */
public class TestMavenResolve {

    @Test
    public void testMaven() {
        MavenResolverSystem system = Maven.resolver();
        PomEquippedResolveStage stage = system.loadPomFromFile(new File("/Users/davida/Code/GUnit/test-project/pom.xml"));

        File[] libs = stage.importCompileAndRuntimeDependencies().resolve()
                .withTransitivity()
                .asFile();
    }
}
