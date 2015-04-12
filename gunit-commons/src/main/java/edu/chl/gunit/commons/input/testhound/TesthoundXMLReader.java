package edu.chl.gunit.commons.input.testhound;

import edu.chl.gunit.commons.api.DeadFieldsContainer;
import edu.chl.gunit.commons.api.Profile;
import edu.chl.gunit.commons.api.SetupUsage;
import edu.chl.gunit.commons.api.TesthoundResult;
import edu.chl.gunit.commons.xml.XMLReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by davida on 12.4.2015.
 */
public class TesthoundXMLReader extends XMLReader {
    static XPath xpath;
    static {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        xpath = xPathfactory.newXPath();
    }

    public TesthoundResult readTesthoundData(File f) throws XPathExpressionException {
        Document doc = null;
        try {
             doc = super.read(f);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }

        if (doc == null) return null;

        TesthoundResult r = new TesthoundResult();

        parseSetupUsages(r, doc);

        return r;
    }

    private void parseSetupUsages(TesthoundResult r, Document doc) throws XPathExpressionException {
        NodeList expr = (NodeList) xpath.evaluate("/TestHound/ClassSetupUsage", doc, XPathConstants.NODESET);

        for (int i = 0; i < expr.getLength(); ++i){
            Node n  = expr.item(i);

            SetupUsage usage = new SetupUsage();

            usage.setClassName((String)xpath.evaluate("@testcase",n, XPathConstants.STRING));
            parseDeadFields(usage, n);

            parseProfiles(usage, n);
            
            parseProblems(usage, n);
            
            r.getClassSetupUsages().add(usage);
        }
    }

    private void parseProblems(SetupUsage usage, Node n) throws XPathExpressionException {
        NodeList detachedMethods = (NodeList) xpath.evaluate("Problems/DetachedMethods/Method", n, XPathConstants.NODESET);

        for (int i = 0; i < detachedMethods.getLength(); ++i) {
            usage.getDetachedMethods().add(detachedMethods.item(i).getTextContent());
        }

        NodeList inlineSetups = (NodeList) xpath.evaluate("Problems/InlineSetupMethods/Method", n, XPathConstants.NODESET);

        for (int i = 0; i < inlineSetups.getLength(); ++i) {
            usage.getInlineSetupMethods().add(inlineSetups.item(i).getTextContent());
        }

        NodeList generalFixtureMethods = (NodeList) xpath.evaluate("Problems/GeneralFixtureMethods/Method", n, XPathConstants.NODESET);

        for (int i = 0; i < generalFixtureMethods.getLength(); ++i) {
            usage.getGeneralFixtureMethods().add(generalFixtureMethods.item(i).getTextContent());
        }
     }

    private void parseDeadFields(DeadFieldsContainer ctr, Node n) throws XPathExpressionException {
        NodeList nl = (NodeList) xpath.evaluate("DeadFields/DeadField", n, XPathConstants.NODESET);

        for (int i = 0; i  < nl.getLength(); ++i) {
            Node df = nl.item(i);

            ctr.getDeadFields().add((String) xpath.evaluate("@fieldName", df, XPathConstants.STRING));
        }
    }

    private void parseProfiles(SetupUsage usage, Node n) throws XPathExpressionException {
        NodeList nl = (NodeList) xpath.evaluate("SetupUsageProfiles/Profile", n, XPathConstants.NODESET);

        for (int i = 0; i < nl.getLength(); ++i) {
            Node p = nl.item(i);

            usage.getProfiles().add(parseProfile(p));
        }
    }

    private Profile parseProfile(Node p) throws XPathExpressionException {

        Profile profile = new Profile();
        profile.setCanonicalName((String) xpath.evaluate("@canonical", p, XPathConstants.STRING));
        profile.setClassName((String) xpath.evaluate("@className", p, XPathConstants.STRING));
        profile.setName((String) xpath.evaluate("@name", p, XPathConstants.STRING));
        
        return profile;
    }
}
