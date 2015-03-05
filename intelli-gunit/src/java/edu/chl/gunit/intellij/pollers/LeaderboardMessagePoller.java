package edu.chl.gunit.intellij.pollers;

import edu.chl.gunit.commons.api.ApiUser;

import edu.chl.gunit.intellij.Messages;
import edu.chl.gunit.service.client.Client;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by davida on 4.3.2015.
 */
public class LeaderboardMessagePoller implements MessagePoller<List<LinkedHashMap<String,String>>> {
    @Override
    public List<LinkedHashMap<String,String>> poll(Client c) throws Exception {
        return c.getLeaderboard();
    }

    @Override
    public int getInterval() {
        return 10;
    }

    @Override
    public boolean oneTime() {
        return false;
    }

    @Override
    public String getMessageName() {
        return Messages.LEADERBOARD;
    }
}
