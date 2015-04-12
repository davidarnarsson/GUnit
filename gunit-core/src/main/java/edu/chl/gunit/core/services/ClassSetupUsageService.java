package edu.chl.gunit.core.services;

import edu.chl.gunit.commons.api.SetupUsage;
import edu.chl.gunit.commons.api.TesthoundResult;
import edu.chl.gunit.core.data.tables.records.ClasssetupusageRecord;

/**
 * Created by davida on 12.4.2015.
 */
public interface ClassSetupUsageService extends Service<ClasssetupusageRecord> {

    ClasssetupusageRecord create(SetupUsage x, int sessionId);
}
