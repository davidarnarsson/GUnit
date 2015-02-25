package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.UserRecord;

/**
 * Created by davida on 25.2.2015.
 */
public interface UserService extends Service<UserRecord> {
    UserRecord getUser(String username);

    UserRecord createUser(String username);

    UserRecord getOrCreate(String userName);
}
