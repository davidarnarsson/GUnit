import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.gamification.rules.SurpassingUserRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by davida on 7.4.2015.
 */
public class TestSurpassingUserRule {

    @Test
    public void testCreateNameStringMultipleUsers() {
        SurpassingUserRule r = new SurpassingUserRule();
        List<UserRecord> users = new ArrayList<>();
        users.add(new UserRecord(0, "Luke", 0, null, 0,0.0,0.0));
        users.add(new UserRecord(0, "John", 0, null, 0,0.0,0.0));
        users.add(new UserRecord(0, "Bob", 0, null, 0,0.0,0.0));
        String d = r.getUsernameString(users);

        assertEquals("String should be 'Luke, John og Bob'", "Luke, John og Bob", d);
    }

    @Test
    public void testCreateNameStringTwoUsers() {
        SurpassingUserRule r = new SurpassingUserRule();
        List<UserRecord> users = new ArrayList<>();
        users.add(new UserRecord(0, "John", 0, null, 0,0.0,0.0));
        users.add(new UserRecord(0, "Bob", 0, null, 0,0.0,0.0));
        String d = r.getUsernameString(users);

        assertEquals("String should be 'John og Bob'", "John og Bob", d);
    }

    @Test
    public void testCreateNameStringSingleUser() {
        SurpassingUserRule r = new SurpassingUserRule();
        List<UserRecord> users = new ArrayList<>();
        users.add(new UserRecord(0, "John", 0, null, 0,0.0,0.0));

        String d = r.getUsernameString(users);

        assertEquals("String should be 'John'", "John", d);
    }
}
