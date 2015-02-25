package edu.chl.gunit.core.services;

import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;

/**
 * Created by davida on 25.2.2015.
 */
public interface JaCoCoResultService extends Service<JacocoresultRecord> {
    JacocoresultRecord createFromResult(JaCoCoResult r, SessionRecord s);
}
