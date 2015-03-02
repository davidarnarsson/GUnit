package edu.chl.gunit.core.services.impl;

import edu.chl.gunit.core.data.DBContext;
import edu.chl.gunit.core.data.tables.User;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.TableField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by davida on 23.2.2015.
 */

public class UserServiceImpl extends AbstractService<UserRecord> implements edu.chl.gunit.core.services.UserService {
    public UserServiceImpl() {
        super(User.USER);
    }

    @Override
    public UserRecord getUser(String username) {
        try (DBContext ctx = ctx()) {
            return ctx.dsl.selectFrom(User.USER)
                    .where(User.USER.NAME.eq(username))
                    .fetchOne();
        }
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
    public Result<UserRecord> getLeaderboard(Optional<TableField<UserRecord,?>> field) {
        try (DBContext ctx = ctx()) {
            return ctx.dsl.selectFrom(User.USER).orderBy(field.orElse(User.USER.POINTS).desc()).fetch();
        }
    }

    @Override
    protected Field<Integer> idField() {
        return User.USER.ID;
    }
}
