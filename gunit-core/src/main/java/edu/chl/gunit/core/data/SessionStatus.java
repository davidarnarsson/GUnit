package edu.chl.gunit.core.data;

/**
 * Created by davida on 26.2.2015.
 */
public enum SessionStatus {
    New(0),
    Processed(1),
    Failed(2);

    public int getStatusCode() {
        return statusCode;
    }

    private final int statusCode;

    SessionStatus(int i) {
        this.statusCode = i;
    }

    public static SessionStatus from(int code) {
        switch(code) {
            case 0 :return New;
            case 1: return Processed;
            case 2: return Failed;
            default: return New;
        }
    }

}
