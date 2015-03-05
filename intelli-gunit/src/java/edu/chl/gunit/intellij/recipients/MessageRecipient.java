package edu.chl.gunit.intellij.recipients;

/**
* Created by davida on 4.3.2015.
*/
public interface MessageRecipient<T> {
    void receive(T msg);
    String forMessageName();
    boolean oneTime();
}
