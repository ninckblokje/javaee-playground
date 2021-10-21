package ninckblokje.playground.javaee.resource;

import ninckblokje.playground.javaee.service.SendHelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.runtime.BatchRuntime;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Properties;

@Path("/hello-world")
public class HelloResource {

    private final Logger logger = LoggerFactory.getLogger(HelloResource.class);

    @Inject
    private SendHelloWorldService service;

    @GET
    @Produces("text/plain")
    public String hello(@DefaultValue("-1") @QueryParam("crashCount") Integer crashCount) {
        var props = new Properties();
        props.setProperty("crashCount", crashCount.toString());

        logger.info("Starting hello-world job with crash count {}", crashCount);
        BatchRuntime.getJobOperator().start("hello-world", props);
        return "Hello, World!";
    }
}