package edu.chl.gunit.service.resources;

import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiBadge;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.services.BadgeService;
import edu.chl.gunit.core.services.Service;
import edu.chl.gunit.service.api.Utils;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by davida on 7.4.2015.
 */
@Path("/api/badges")
@Produces("application/json")
public class BadgesResource extends AbstractResource<BadgeRecord, BadgeService, ApiBadge> {

    @Inject
    BadgeService badgeService;

    public BadgesResource() {
        super(Utils::from);
    }

    @Override
    protected Service<BadgeRecord> getService() {
        return badgeService;
    }
}
