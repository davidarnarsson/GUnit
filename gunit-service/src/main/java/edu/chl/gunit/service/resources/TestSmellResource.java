package edu.chl.gunit.service.resources;

import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiClassSetupUsage;
import edu.chl.gunit.commons.api.ApiTestSmell;
import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.services.ClassSetupUsageService;
import edu.chl.gunit.core.services.SessionService;
import edu.chl.gunit.core.services.TestSmellService;
import edu.chl.gunit.service.api.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davida on 13.4.2015.
 */

@Path("/api/smells")
@Produces("application/json")
public class TestSmellResource {

    @Inject
    SessionService sessionService;

    @Inject
    ClassSetupUsageService classSetupUsageService;

    @Inject
    TestSmellService testSmellService;

    @GET
    @Path("{id}")
    public List<ApiClassSetupUsage> getLatestUsage(@PathParam("id") int userId) {
        SessionRecord session = sessionService.getLatestSession(userId, SessionStatus.Processed);

        if (session == null) {
            return new ArrayList<>();
        }

        List<ApiClassSetupUsage> usages = classSetupUsageService.getSetupUsagesForSession(session.getSessionid())
                .stream().map(Utils::from)
                .collect(Collectors.toList());
        List<ApiTestSmell> testSmells = testSmellService.getTestSmellsForSession(session.getSessionid())
                .stream()
                .map(Utils::from)
                .collect(Collectors.toList());


        testSmells.forEach(x -> {
            ApiClassSetupUsage u = usages.stream().filter(y ->y.getId() == x.getClassSetupUsageId()).findFirst().orElse(null);
            if (u != null) {
                u.getTestSmells().add(x);
            }
        });

        return usages;
    }
}
