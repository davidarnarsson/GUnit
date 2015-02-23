package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.tables.User;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import org.jooq.TableField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by davida on 23.2.2015.
 */

public class UserService extends AbstractService<UserRecord> {

    public UserService() {
        super(User.USER);
    }

    public UserRecord getUser(String username) {
        return ctx().selectFrom(User.USER)
                .where(User.USER.NAME.eq(username))
                .fetchOne();
    }


    public UserRecord createUser(String username) {
        Map<TableField<UserRecord, String>, String> map = new HashMap<>();
        map.put(User.USER.NAME, username);
        return create(map);
    }

    public UserRecord getOrCreate(String userName) {
        UserRecord r = getUser(userName);
        if (r == null) {
            r = createUser(userName);
        }
        return r;
    }
}
