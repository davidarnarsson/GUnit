package edu.chl.gunit.service.resources;

import org.jooq.Record;

/**
 * Created by davida on 5.3.2015.
 */
public interface ResourceMapper<I extends Record, O> {
    O map(I in);
}
