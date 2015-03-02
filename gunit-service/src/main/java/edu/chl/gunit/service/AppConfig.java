package edu.chl.gunit.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by davida on 2.3.2015.
 */
public class AppConfig extends io.dropwizard.Configuration {

    @NotEmpty
    private String connectionString;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    public String getAssetsDir() {
        return assetsDir;
    }

    public void setAssetsDir(String assetsDir) {
        this.assetsDir = assetsDir;
    }

    @NotEmpty
    private String assetsDir;

    @JsonProperty
    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
