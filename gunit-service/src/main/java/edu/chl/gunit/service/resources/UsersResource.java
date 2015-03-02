package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.service.api.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davida on 2.3.2015.
 */
@Path("/api/users")
@Produces("application/json")
public class UsersResource {
    private final ServiceFacade facade;

    public UsersResource(ServiceFacade facade) {
        this.facade = facade;
    }

    @GET
    @Timed
    public List<User> getUsers() {
        return facade.userService()
                .getList()
                .stream()
                .map(User::from)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/by-name")
    @Timed
    public User getUser(@QueryParam("name") String name) {
        if (!(name == null || "".equals(name))) {
            return User.from(facade.userService().getUser(name));
        }

        return null;
    }
}
