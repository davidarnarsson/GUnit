import edu.chl.gunit.plugin.csv.CSV;
import edu.chl.gunit.plugin.csv.CSVReader;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resources;
import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by davida on 5.2.2015.
 */
public class TestCSVReader {

    @Test
    public void testParseHeaders() {
        final String data = "Header1,Header2, Header3,Header 4";

        CSVReader reader = new CSVReader();

        CSV csv = reader.read(data, true);

        assertEquals("csv should have 4 columns", 4, csv.getColumnCount());
        assertEquals("csv should have Header1 as column 0", "Header1", csv.header(0));
        assertEquals("csv should have \"Header 4\" as column 3", "Header 4", csv.header(3));
    }

    @Test
    public void testParseCsv() {
        final String data = "Header1,Header2,Header3,Header4" +System.lineSeparator()+  "Data 1,Data2,3,4";
        CSVReader reader = new CSVReader();

        CSV csv = reader.read(data, true);

        assertEquals("CSV has headers", true, csv.hasHeader());
        assertEquals("CSV has 4 columns", 4, csv.getColumnCount());
        assertEquals("First row has \"Data 1\" as first cell in first row", "Data 1", csv.row(0).get(0));
    }

    @Test
    public void testParseJaCoCoCSV() throws IOException {
        File f = new File(getClass().getClassLoader().getResource("jacoco.csv").getFile());

        if (f.exists()) {
            String data = FileUtils.fileRead(f);

            CSVReader reader = new CSVReader();

            CSV csv = reader.read(data, true);

            Assert.assertTrue(true);
        } else {
            Assert.fail("Can't find test JaCoCo CSV file.");
        }
    }
}

