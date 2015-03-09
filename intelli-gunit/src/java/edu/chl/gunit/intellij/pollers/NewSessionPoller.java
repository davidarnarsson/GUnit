package edu.chl.gunit.intellij.pollers;

import edu.chl.gunit.commons.api.ApiSession;
import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.intellij.Messages;
import edu.chl.gunit.service.client.Client;

/**
 * Created by davida on 09/03/15.
 */
public class NewSessionPoller implements MessagePoller<ApiSession> {
    private final String userName;
    Integer lastSession = null;

    public NewSessionPoller(String userName) {
        this.userName = userName;
    }


    @Override
    public ApiSession poll(Client c) throws Exception {
        ApiSession session = c.getLatestSession(userName);

        if (session != null) {
            if (lastSession == null) {
                lastSession = session.getSessionId();
                return null;
            }

            if (!session.getSessionId().equals(lastSession) && session.getSessionStatus() == SessionStatus.Processed.getStatusCode()) {
                lastSession = session.getSessionId();
                return session;
            }
        }

        return null;
    }

    @Override
    public int getInterval() {
        return 1;
    }

    @Override
    public boolean oneTime() {
        return false;
    }

    @Override
    public String getMessageName() {
        return Messages.NEWSESSION;
    }
}
