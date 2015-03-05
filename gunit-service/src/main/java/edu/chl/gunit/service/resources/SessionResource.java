package edu.chl.gunit.service.resources;

import edu.chl.gunit.commons.api.ApiSession;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.services.Service;
import edu.chl.gunit.core.services.SessionService;
import edu.chl.gunit.service.api.Utils;

import javax.ws.rs.Path;

/**
 * Created by davida on 5.3.2015.
 */
@Path("/api/session")
public class SessionResource extends AbstractResource<SessionRecord, SessionService, ApiSession> {

    private final ServiceFacade facade;

    public SessionResource(ServiceFacade facade) {
        super(in -> Utils.from(in));
        this.facade = facade;
    }

    @Override
    protected Service<SessionRecord> getService() {
        return facade.sessionService();
    }
}
