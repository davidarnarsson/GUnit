package edu.chl.gunit.core.services;

import edu.chl.gunit.core.data.DBContext;
import org.jooq.*;

import java.util.List;
import java.util.Map;

/**
 * Created by davida on 25.2.2015.
 */
public interface Service<T extends Record> {
    T get(int id);

    List<T> getList();

    List<T> getList(Condition... c);
    List<T> getList(int offset , int count, Condition... c);

    void transaction(TransactionalRunnable tr);

    int update(T record);

    T create(Map<? extends Field<?>, ?> fields);

    int delete(int id);

    DBContext ctx();

    int count(Condition eq);
}
