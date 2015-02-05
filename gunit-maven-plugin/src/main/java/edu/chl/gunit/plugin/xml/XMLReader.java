package edu.chl.gunit.plugin.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Created by davida on 4.2.2015.
 */
public class XMLReader {

    public Document read(File f) throws IOException, SAXException, ParserConfigurationException {
        FileInputStream stream = new FileInputStream(f);

        return read(stream);
    }

    public Document read(InputStream f) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(f);
    }
}
