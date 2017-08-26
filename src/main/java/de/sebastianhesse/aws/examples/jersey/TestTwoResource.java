package de.sebastianhesse.aws.examples.jersey;

import de.sebastianhesse.aws.examples.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * A simple Jersey resource using Spring.
 */
@Path("/two")
@Service
public class TestTwoResource {

    @Autowired DefaultService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response simpleGet() {
        return Response.ok("Resource Number Two: " + service.getFoo()).build();
    }
}
