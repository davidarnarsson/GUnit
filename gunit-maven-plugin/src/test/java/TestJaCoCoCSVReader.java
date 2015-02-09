import edu.chl.gunit.plugin.input.jacoco.JaCoCoCSVReader;
import edu.chl.gunit.plugin.input.jacoco.JaCoCoResult;
import edu.chl.gunit.plugin.input.jacoco.JaCoCoResultException;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by davida on 06/02/15.
 */
public class TestJaCoCoCSVReader {

    @Test
    public void testParseJaCoCoDoc() throws JaCoCoResultException {
        JaCoCoCSVReader reader = new JaCoCoCSVReader();
        List<JaCoCoResult> l = reader.read(new File(getClass().getClassLoader().getResource("jacoco.csv").getFile()));

        JaCoCoResult result = l.get(0);
        // test-project,edu.chl.coverage,CoverageRunner,231,0,8,0,44,0,10,0,5,0
        assertEquals("test-project", result.getGroupName());
        assertEquals("edu.chl.coverage", result.getPackageName());
        assertEquals("CoverageRunner", result.getClassName());
        assertEquals(231, result.getInstructionMissed());
        assertEquals(0, result.getInstructionCovered());
        assertEquals(8, result.getBranchMissed());
        assertEquals(0, result.getBranchCovered());
        assertEquals(44, result.getLineMissed());
        assertEquals(0, result.getLineCovered());
        assertEquals("complexityMissed",10, result.getComplexityMissed());
        assertEquals(0, result.getComplexityCovered());
        assertEquals(5, result.getMethodMissed());
        assertEquals(0, result.getMethodCovered());

    }
}
