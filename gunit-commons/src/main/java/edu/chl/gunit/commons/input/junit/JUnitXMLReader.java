package edu.chl.gunit.commons.input.junit;

import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.commons.xml.XMLReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses a JUnit XML report into a data structure.
 * Created by davida on 4.2.2015.
 */
public class JUnitXMLReader extends XMLReader {

    public TestSuiteResults read(String path) throws JUnitResultException {
        File f = new File(path);

        Document d = null;
        try {
            d = super.read(f);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            Logger.getLogger("JUnitXmlReader").log(Level.ALL, e.getMessage());

            JUnitResultException ex = new JUnitResultException("Unable to parse the JUnit XML files, are they in the right place?");
            ex.addSuppressed(e);
            throw ex;
        }

        Element testSuiteElement = (Element) d.getElementsByTagName("testsuite").item(0);

        // gather test suite information
        String name = testSuiteElement.getAttribute("name");
        double time  = Double.parseDouble(testSuiteElement.getAttribute("time"));
        int tests = Integer.parseInt(testSuiteElement.getAttribute("tests"));
        int errors = Integer.parseInt(testSuiteElement.getAttribute("errors"));
        int skipped = Integer.parseInt(testSuiteElement.getAttribute("skipped"));
        int failures = Integer.parseInt(testSuiteElement.getAttribute("failures"));
        TestSuiteResults suiteResults = new TestSuiteResults(name, time, tests, errors, skipped, failures);

        // gather properties
        NodeList propertyList = d.getElementsByTagName("property");
        for(int i = 0 ; i < propertyList.getLength(); ++i) {
            Node node = propertyList.item(i);
            String key = node.getAttributes().getNamedItem("name").getTextContent();
            String value = node.getAttributes().getNamedItem("value").getTextContent();

            suiteResults.addProperty(key, value);
        }

        // Gather test case results
        NodeList caseList = d.getElementsByTagName("testcase");
        for(int i = 0; i< caseList.getLength();++i) {
            Node node = caseList.item(i);

            String caseName = node.getAttributes().getNamedItem("name").getTextContent();
            String className = node.getAttributes().getNamedItem("classname").getTextContent();
            double caseTime = Double.parseDouble(node.getAttributes().getNamedItem("time").getTextContent());

            TestCase testCase = new TestCase(caseName);
            testCase.setClassName(className);
            testCase.setTimeElapsed(caseTime);

            Element e = (Element) node;
            NodeList failure = e.getElementsByTagName("failure");
            testCase.setSucceeded(failure.getLength() == 0);
            if (failure.getLength() > 0) {
                Element fNode = (Element) failure.item(0);
                String message = fNode.getAttribute("message");
                String type = fNode.getAttribute("type");
                String stackTrace = fNode.getTextContent();

                testCase.setErrorMessage(message);
                testCase.setErrorType(type);
                testCase.setStackTrace(stackTrace);
            }

            suiteResults.addTestCase(testCase);
        }

        return suiteResults;
    }


}
