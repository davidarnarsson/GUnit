package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.records.UserRecord;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.TableField;

import java.util.List;
import java.util.Optional;

/**
 * Created by davida on 25.2.2015.
 */
public interface UserService extends Service<UserRecord> {
    UserRecord getUser(String username);

    UserRecord createUser(String username);

    UserRecord getOrCreate(String userName);

    Result<UserRecord> getLeaderboard(Optional<TableField<UserRecord, ?>> field);
}
