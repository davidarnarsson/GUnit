package edu.chl.gunit.core.data;

/**
 * Created by davida on 20.2.2015.
 */
public class User {
    public void setName(String name) {
        this.name = name;
    }

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;

    }

    public String getName() {
        return name;
    }

    private long id;
    private String name;


}
