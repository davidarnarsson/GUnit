package edu.chl.gunit;

import etse.core.testorganizer.fixture.ClassSetupUsage;
import etse.core.testorganizer.fixture.htmlreport.AllReportGenerator;
import etse.core.testorganizer.fixture.htmlreport.AnalysisListerner;

import java.util.HashSet;

/**
 * Created by davida on 17/02/15.
 */
public class DummyReportGenerator extends AllReportGenerator {

    public DummyReportGenerator() {
        this(null,null);
    }

    public DummyReportGenerator(String folderReport, String templateLocation) {
        super(folderReport, templateLocation);
    }

    public DummyReportGenerator(String folderReport2, String templateLocation2, HashSet<AnalysisListerner> analysisListerners) {
        this(folderReport2, templateLocation2);
    }

    @Override
    public void newTestCaseReport(ClassSetupUsage classProfile, String testClassName) {

    }


}
