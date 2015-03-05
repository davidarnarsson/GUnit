package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by davida on 2.3.2015.
 */
public class ApiBadge {

    private Integer id;
    private String name;
    private String image;

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty
    public String getImage() {
        return image;
    }
}
