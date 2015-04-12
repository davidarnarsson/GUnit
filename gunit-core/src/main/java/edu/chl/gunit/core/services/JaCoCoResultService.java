package edu.chl.gunit.core.services;

import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;

/**
 * Created by davida on 25.2.2015.
 */
public interface JaCoCoResultService extends Service<JacocoresultRecord> {
    JacocoresultRecord createFromResult(ApiJaCoCoResult r, SessionRecord s);

    JacocoresultRecord getLatestJaCoCoResult(String packageName, String className);
}
