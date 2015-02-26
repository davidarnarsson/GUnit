package edu.chl.gunit.service;


import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.chl.gunit.core.Module;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.DBProvider;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;
import edu.chl.gunit.core.gamification.TestDataRunner;
import edu.chl.gunit.core.gamification.TestRunRequest;
import edu.chl.gunit.service.data.SessionStatistics;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 * Created by davida on 20.2.2015.
 */

@WebService(endpointInterface = "edu.chl.gunit.service.GUnitService")
public class GUnitServiceImpl implements GUnitService {

    @Override
    public String ping() {
        return "pong!";
    }

    @Resource
    private WebServiceContext context;

    private static ServiceFacade facade;

    private ServiceFacade getFacade() {
        if (facade == null) {
            ServletContext servletContext =
                    (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
            String connString = servletContext.getInitParameter("connstring");
            String username = servletContext.getInitParameter("username");
            String password = servletContext.getInitParameter("password");

            DBProvider provider = new DBProvider(connString,username,password);

            facade = ServiceFacade.get(new Module(provider));
        }

        return facade;
    }


    @Override
    public int submitTestRun(TestRunRequest request) {

        TestDataRunner runner = getFacade().getInjector().getInstance(TestDataRunner.class);

        int sessionid = runner.initialize(request);

        Thread runThread = new Thread(runner);
        runThread.start();

        /**
         * 1. submit everything to the database
         * 2. create a unique message key
         * 3. collect all the new ids and create a message queue message
         * 4. return the message key for the user to query at a later date
         * */

        return sessionid;
    }

    @Override
    public SessionStatistics getSessionStatistics(int sessionId) {
        SessionStatistics statistics = new SessionStatistics();

        SessionRecord session = getFacade().sessionService().get(sessionId);
        if (session != null) {
            statistics.setSession(session);

            UserRecord userRecord = getFacade().userService().get(session.getUserid());
            statistics.setUser(userRecord);

            statistics.setBadgesEarned(getFacade().badgeService().getBadgesForUserSession(sessionId));
            return statistics;
        }

        return null;
    }
}
