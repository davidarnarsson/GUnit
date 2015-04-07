package edu.chl.gunit;

import etse.core.classloader.ClazzLoader;
import etse.core.testorganizer.fixture.ClassSetupUsage;
import etse.core.testorganizer.fixture.FixtureAnalyser;
import etse.core.testorganizer.fixture.htmlreport.AllReportGenerator;
import etse.core.testorganizer.main.experiments.ExperiementMain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by davida on 17/02/15.
 */
public class TestHoundRunner extends ExperiementMain {

    public ArrayList<ClassSetupUsage> run(HashSet<String> classes, ClazzLoader loader, File outDir, File templateLocation) {

        return analyseTests(classes, new FixtureAnalyser(loader), new AllReportGenerator(outDir.getAbsolutePath(), templateLocation.getAbsolutePath()));
    }
}
