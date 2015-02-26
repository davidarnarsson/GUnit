package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.SessionRecord;

/**
 * Created by davida on 25.2.2015.
 */
public interface SessionService extends Service<SessionRecord> {
    SessionRecord create(int userId);
    void setProcessed(SessionRecord record);
    void setFailed(SessionRecord record);
}
