package de.sebastianhesse.aws.examples;

import de.sebastianhesse.aws.examples.jersey.TestOneResource;
import de.sebastianhesse.aws.examples.jersey.TestTwoResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * A Jersey config (registered as a Spring bean) which puts the Spring context into the properties.
 */
@Component
public class JerseyResourceConfig extends ResourceConfig {

    @Autowired TestOneResource oneResource;
    @Autowired TestTwoResource twoResource;


    @PostConstruct
    public void init() {
        // register spring supported resources
        register(oneResource);
        register(twoResource);
    }
}
