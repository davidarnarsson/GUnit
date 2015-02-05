import com.sun.org.apache.xml.internal.utils.XMLReaderManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.xml.sax.XMLReader;

import java.io.File;
import java.net.URL;

/**
 * Created by davida on 4.2.2015.
 */
@Mojo( name = "sayhi" )
public class GUnitMojo extends AbstractMojo {

    private URL jacocoReportXml;

    private File testReportDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Hi there!");
    }
}
