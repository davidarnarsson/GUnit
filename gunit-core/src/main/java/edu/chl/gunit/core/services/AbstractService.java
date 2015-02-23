package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.DBConnection;
import edu.chl.gunit.core.data.tables.Jacocoresult;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by davida on 23.2.2015.
 */
abstract class AbstractService<T extends Record> {


    private final TableImpl<T> tableInstance;

    public AbstractService(TableImpl<T> tableInstance) {
        this.tableInstance = tableInstance;
    }

    public T get(int id) {
        return ctx().selectFrom(tableInstance)
                .where(tableInstance.getIdentity().getField().ascii().eq(id))
                .fetchOne();
    }

    public List<T> getList() {
        return getList(null);
    }

    public List<T> getList(Condition... c) {
        return ctx().selectFrom(tableInstance)
                .where(c)
                .fetch();
    }

    public T create(Map<? extends Field<?>, ?> fields) {
        return ctx().insertInto(tableInstance).set(fields).returning().fetchOne();
    }

    public int delete(int id) {
        return ctx().delete(tableInstance).where(tableInstance.getIdentity().getField().ascii().equal(id)).execute();
    }

    public DSLContext ctx() {
        try {
            return DBConnection.getContext();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
