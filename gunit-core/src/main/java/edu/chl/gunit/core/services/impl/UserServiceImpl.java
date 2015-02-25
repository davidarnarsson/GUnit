package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.tables.User;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import org.jooq.Field;
import org.jooq.TableField;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davida on 23.2.2015.
 */

public class UserServiceImpl extends AbstractService<UserRecord> implements edu.chl.gunit.core.services.UserService {
    public UserServiceImpl() {
        super(User.USER);
    }

    @Override
    public UserRecord getUser(String username) {
        return ctx().selectFrom(User.USER)
                .where(User.USER.NAME.eq(username))
                .fetchOne();
    }


    @Override
    public UserRecord createUser(String username) {
        Map<TableField<UserRecord, String>, String> map = new HashMap<>();
        map.put(User.USER.NAME, username);
        return create(map);
    }

    @Override
    public UserRecord getOrCreate(String userName) {
        UserRecord r = getUser(userName);
        if (r == null) {
            r = createUser(userName);
        }
        return r;
    }

    @Override
    protected Field<Integer> idField() {
        return User.USER.ID;
    }
}
