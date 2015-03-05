package edu.chl.gunit.service.resources;

import edu.chl.gunit.core.services.Service;
import org.jooq.Record;

import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davida on 5.3.2015.
 */
public abstract class AbstractResource<I extends Record, R extends Service<I>, O> {

    private final ResourceMapper<I, O> mapper;

    protected AbstractResource(ResourceMapper<I, O> mapper) {
        this.mapper = mapper;
    }

    protected ResourceMapper<I,O> getMapper() {
        return mapper;
    }

    protected abstract Service<I> getService();

    @GET
    @Path("/{id}")
    public O get(@PathParam("id") int id) {
        return mapper.map(getService().get(id));
    }

    @GET
    @Path("/")
    public List<O> getList(@QueryParam("offset") int offset,@QueryParam("count") int count) {
        if (count == 0) count = 20;
        if (count > 100) count = 100;

        return getService().getList(offset, count, null)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

}
