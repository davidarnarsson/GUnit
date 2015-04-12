package edu.chl.gunit.commons.api;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 12.4.2015.
 */
public class TesthoundResult {
    private List<SetupUsage> classSetupUsages;

    public TesthoundResult() {
        classSetupUsages = new ArrayList<SetupUsage>();
    }

    public List<SetupUsage> getClassSetupUsages() {
        return classSetupUsages;
    }

    public void setClassSetupUsages(List<SetupUsage> classSetupUsages) {
        this.classSetupUsages = classSetupUsages;
    }
}
