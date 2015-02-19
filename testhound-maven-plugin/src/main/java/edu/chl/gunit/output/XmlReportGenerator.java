package edu.chl.gunit.output;

import etse.core.testorganizer.fixture.ClassSetupUsage;
import etse.core.testorganizer.fixture.FieldIdentifier;
import etse.core.testorganizer.fixture.FieldLink;
import etse.core.testorganizer.fixture.SetupUsageProfile;
import etse.core.testorganizer.fixture.bcel.BCELFieldUsage;
import etse.core.testorganizer.fixture.problemcalculators.ClassSetupProblems;
import org.apache.maven.plugin.MojoExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by davida on 17/02/15.
 */
public class XmlReportGenerator  {
    public Document generate(List<ClassSetupUsage> result, Date date) throws MojoExecutionException {
        assert result != null;

        DocumentBuilder builder = null;
        try {
             builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new MojoExecutionException("Unable to generate testhound result XML");
        }

        Document xml = builder.newDocument();

        Element root = xml.createElement("TestHound");
        root.setAttribute("executionDate", date.toString());
        xml.appendChild(root);

        for (ClassSetupUsage u : result) {
            createClassSetupUsageElement(u, root, xml);
        }

        return xml;
    }

    protected void createClassSetupUsageElement(ClassSetupUsage usage, Element parent, Document doc) {
        Element clu = doc.createElement("ClassSetupUsage");

        clu.setAttribute("testcase", usage.getTestCaseName());

        createAnnotationList(usage.getClassAnnotations(), clu, doc);
        createFieldsList("DeadFields", usage.getDeadFields(), clu, doc);
        createFieldsList("DeadInheritedFields", usage.getDeadInheritedFields(), clu, doc);
        createFieldsList("InheritedFields", usage.getInheritedFields(),clu, doc);
        createFieldsList("SharedFixtureFields", usage.getSharedFixtureFields(), clu, doc);
        createField("LiveFields", usage.getLiveFields(), clu, doc);
        createField("LiveSetupFields", usage.getLiveSetupFields(), clu, doc);
        createField("NrMemberSetupFields", usage.getNrMemberSetupFields(), clu, doc);
        createField("NrStaticSetupFields", usage.getNrStaticSetupFields(), clu, doc);



        createProblems(usage, clu, doc);

        parent.appendChild(clu);
    }

    private void createProblems(ClassSetupUsage usage, Element parent, Document doc) {
        ClassSetupProblems problems = new ClassSetupProblems(usage);

        Element pElement = doc.createElement("Problems");

        createSetupUsageProfile("DetachedMethods", problems.getDetachedMethods(), pElement, doc);
        createSetupUsageProfile("InlineSetupMethods", problems.getInlineSetupMethods(), pElement, doc);
        createSetupUsageProfile("GeneralFixtureMethods", problems.getGeneralFixtureLiveFieldsMethods(), pElement, doc);

        parent.appendChild(pElement);
    }

    private void createSetupUsageProfile(String fieldName, List<SetupUsageProfile> profile, Element parent, Document doc) {
        Element e = doc.createElement(fieldName);
        for (SetupUsageProfile p : profile) {
            Element pl = doc.createElement("Method");

            pl.setTextContent(p.getMethod().getName());

            e.appendChild(pl);
        }
        parent.appendChild(e);
    }

    private void createField(String fieldName, Integer fieldContent, Element parent, Document doc) {
        Element e = doc.createElement(fieldName);
        e.setTextContent(fieldContent.toString());
        parent.appendChild(e);
    }
    private void createFieldsList(String fieldName, HashMap<FieldIdentifier, FieldLink> deadFields, Element clu, Document doc) {
        Element dfe = doc.createElement(fieldName);

        for (FieldIdentifier key : deadFields.keySet()) {
            FieldLink link = deadFields.get(key);
            Element df = doc.createElement("DeadField");

            df.setAttribute("fieldName", key.getFieldName());
            df.setAttribute("fieldType", key.getFieldType());

            Element usages = doc.createElement("ClassUsages");

            for (BCELFieldUsage usage : link.getFieldUsages()) {
                Element usageElement = doc.createElement("Usage");

                usageElement.setTextContent(usage.getClassOfFieldUsage().getReflectionClass().getCanonicalName());

                usages.appendChild(usageElement);
            }

            df.setAttribute("isConstructorUsage", new Boolean(link.isConstructorUsage()).toString());
            df.appendChild(usages);

            dfe.appendChild(df);
        }
        clu.appendChild(dfe);
    }

    private void createAnnotationList(List<Annotation> list, Element parent, Document doc) {
        Element r = doc.createElement("ClassAnnotations");
        parent.appendChild(r);

        for (Annotation l : list) {
            Element e = doc.createElement("Annotation");
            e.setTextContent(l.annotationType().getCanonicalName());
            r.appendChild(e);
        }
    }
}
