import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.DBProvider;

/**
 * Created by davida on 25.2.2015.
 */
public class Utils {

    public static Module getTestModule() {
        return new Module(new DBProvider("jdbc:mysql://localhost/gunit_test", "gunit_test", "startrek"));
    }

    public static Injector getTestInjector() {
        return Guice.createInjector(getTestModule());
    }

    public static ServiceFacade getFacade() {
        return ServiceFacade.get(getTestModule());
    }
}
