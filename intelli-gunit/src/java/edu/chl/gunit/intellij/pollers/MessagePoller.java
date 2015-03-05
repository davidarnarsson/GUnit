package edu.chl.gunit.intellij.pollers;

import edu.chl.gunit.service.client.Client;

/**
* Created by davida on 4.3.2015.
*/
public interface MessagePoller<T> {
    T poll(Client c) throws Exception;
    int getInterval();
    boolean oneTime();
    String getMessageName();
}
