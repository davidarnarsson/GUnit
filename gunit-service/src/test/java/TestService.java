import edu.chl.gunit.service.AppConfig;
import edu.chl.gunit.service.GUnitServiceApp;
import org.junit.Test;

/**
 * Created by davida on 23.2.2015.
 */
public class TestService {

    @Test
    public void testServiceStuff() {
        /*Injector injector = Guice.createInjector(new AbstractModule() {
             @Override
             protected void configure() {
                 bind(GUnitService.class).to(GUnitServiceImpl.class);
             }
        });

        GUnitService service = injector.getInstance(GUnitService.class);

        TestRunRequest request = new TestRunRequest();

        request.setUser("Herpderp");
        request.setTestResults(new ArrayList<TestSuiteResults>());
        request.getTestResults().add(new TestSuiteResults("Herp derp", 5, 2,3,0,1));
        //String out = service.submitTestRun(request);*/


    }
}
