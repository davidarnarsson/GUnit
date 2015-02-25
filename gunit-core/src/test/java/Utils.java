import com.google.inject.Injector;
import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.data.DBProvider;

/**
 * Created by davida on 25.2.2015.
 */
public class Utils {

    public static Module getTestModule() {
        return new Module(new DBProvider("jdbc:mysql://localhost/gunit_test", "gunit_test", "startrek"));
    }
}
