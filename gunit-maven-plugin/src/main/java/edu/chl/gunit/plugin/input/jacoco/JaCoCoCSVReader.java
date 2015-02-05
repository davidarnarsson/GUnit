package edu.chl.gunit.plugin.input.jacoco;

import edu.chl.gunit.plugin.csv.CSVReader;
import edu.chl.gunit.plugin.input.junit.JUnitResultException;
import edu.chl.gunit.plugin.xml.XMLReader;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by davida on 5.2.2015.
 */
public class JaCoCoCSVReader {
    public void read(String path) throws JaCoCoResultException {

        CSVReader reader = new CSVReader();
    }
}
