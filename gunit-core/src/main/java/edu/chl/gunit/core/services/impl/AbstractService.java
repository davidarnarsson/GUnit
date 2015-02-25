package edu.chl.gunit.core.services.impl;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.DBProvider;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/**
 * Created by davida on 23.2.2015.
 */
public abstract class AbstractService<T extends Record> implements edu.chl.gunit.core.services.Service<T> {

    @Inject
    private DBProvider provider;

    private final TableImpl<T> tableInstance;

    protected abstract Field<Integer> idField();

    public AbstractService(TableImpl<T> tableInstance) {
        this.tableInstance = tableInstance;
    }

    @Override
    public T get(int id) {
        return ctx().fetchOne(tableInstance, idField().eq(id));
    }

    @Override
    public List<T> getList() {
        return getList(new Condition[]{});
    }

    @Override
    public List<T> getList(Condition... c) {
        return ctx().selectFrom(tableInstance)
                .where(c)
                .fetch();
    }

    @Override
    public void transaction(TransactionalRunnable tr) {
        ctx().transaction(tr);
    }

    @Override
    public int update(T record) {
        return ctx().update(tableInstance).set(record).where(idField().eq(record.getValue(idField()))).execute();
    }

    @Override
    public T create(Map<? extends Field<?>, ?> fields) {
        return ctx().insertInto(tableInstance).set(fields).returning().fetchOne();
    }

    @Override
    public int delete(int id) {
        return ctx().delete(tableInstance).where(idField().eq(id)).execute();
    }

    @Override
    public DSLContext ctx() {
        try {
            return provider.getContext();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
