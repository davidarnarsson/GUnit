package edu.chl.gunit.commons.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 12.4.2015.
 */
public class SetupUsage implements DeadFieldsContainer {

    List<String> deadFields;
    private List<Profile> profiles;
    private List<String> detachedMethods;
    private List<String> inlineSetupMethods;
    private List<String> generalFixtureMethods;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    String className;

    public SetupUsage() {
        this.deadFields = new ArrayList<String>();
        this.profiles = new ArrayList<Profile>();
        this.detachedMethods = new ArrayList<String>();
        this.inlineSetupMethods = new ArrayList<String>();
        this.generalFixtureMethods = new ArrayList<String>();
    }

    @Override
    public List<String> getDeadFields() {
        return deadFields;
    }

    public void setDeadFields(List<String> deadFields) {
        this.deadFields = deadFields;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<String> getDetachedMethods() {
        return detachedMethods;
    }

    public void setDetachedMethods(List<String> detachedMethods) {
        this.detachedMethods = detachedMethods;
    }


    public List<String> getInlineSetupMethods() {
        return inlineSetupMethods;
    }

    public void setInlineSetupMethods(List<String> inlineSetupMethods) {
        this.inlineSetupMethods = inlineSetupMethods;
    }

    public List<String> getGeneralFixtureMethods() {
        return generalFixtureMethods;
    }

    public void setGeneralFixtureMethods(List<String> generalFixtureMethods) {
        this.generalFixtureMethods = generalFixtureMethods;
    }
}
