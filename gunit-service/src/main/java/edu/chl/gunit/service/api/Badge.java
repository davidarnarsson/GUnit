package edu.chl.gunit.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;

/**
 * Created by davida on 2.3.2015.
 */
public class Badge {

    private Integer id;
    private String name;
    private String image;

    public static Badge from(BadgeRecord record) {
        if (record == null) return null;

        Badge b = new Badge();

        b.setId(record.getId());
        b.setImage(record.getImage());
        b.setName(record.getName());
        return b;
    }

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
