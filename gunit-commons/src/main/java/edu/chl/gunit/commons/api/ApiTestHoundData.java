package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by davida on 9.4.2015.
 */
public class ApiTestHoundData {
    private String data;

    @JsonProperty
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
