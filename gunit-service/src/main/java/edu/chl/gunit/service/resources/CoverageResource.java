package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.core.data.tables.Jacocoresult;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.services.JaCoCoResultService;
import edu.chl.gunit.core.services.Service;
import edu.chl.gunit.service.api.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davida on 7.3.2015.
 */
@Produces("application/json")
@Path("/api/coverage")
public class CoverageResource extends AbstractResource<JacocoresultRecord, JaCoCoResultService, ApiJaCoCoResult> {

    @Inject
    JaCoCoResultService service;

    protected CoverageResource() {
        super(in -> Utils.from(in));
    }

    @Override
    protected Service<JacocoresultRecord> getService() {
        return service;
    }

    @Timed
    @GET
    @Path("/session/{id}")
    public List<ApiJaCoCoResult> getSessionResults(@PathParam("id") int sessionId) {
        return service.getList(Jacocoresult.JACOCORESULT.SESSIONID.eq(sessionId)).stream()
                .map(getMapper()::map)
                .collect(Collectors.toList());
    }
}
