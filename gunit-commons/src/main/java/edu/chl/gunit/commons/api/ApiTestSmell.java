package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by davida on 13.4.2015.
 */
public class ApiTestSmell {

    private int id;
    private int classSetupUsageId;
    private String name;
    private String type;

    @JsonProperty
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty
    public int getClassSetupUsageId() {
        return classSetupUsageId;
    }

    public void setClassSetupUsageId(int classSetupUsageId) {
        this.classSetupUsageId = classSetupUsageId;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
