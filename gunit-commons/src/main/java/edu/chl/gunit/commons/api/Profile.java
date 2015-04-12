package edu.chl.gunit.commons.api;

/**
 * Created by davida on 12.4.2015.
 */
public class Profile {
    private String canonicalName;
    private String name;
    private String className;

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
