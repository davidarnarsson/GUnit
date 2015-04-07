package edu.chl.gunit.service.resources;

import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiRule;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.services.RuleService;
import edu.chl.gunit.core.services.Service;
import edu.chl.gunit.service.api.Utils;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by davida on 7.4.2015.
 */
@Path("/api/rules")
@Produces("application/json")
public class RulesResource extends AbstractResource<RuleRecord, RuleService, ApiRule> {

    @Inject
    RuleService ruleService;

    public RulesResource() {
        super(Utils::from);
    }

    @Override
    protected Service<RuleRecord> getService() {
        return ruleService;
    }
}
