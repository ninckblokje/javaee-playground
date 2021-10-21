package ninckblokje.playground.javaee.batch.helloworld;

import ninckblokje.playground.javaee.service.SendHelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class HelloWorldItemWriter extends AbstractItemWriter {

    private final Logger logger = LoggerFactory.getLogger(HelloWorldItemWriter.class);

    private int counter;

    @Inject
    private JobContext jobContext;
    @Inject
    private SendHelloWorldService service;

    @Override
    public void writeItems(List<Object> list) throws Exception {
        counter++;
        list.stream().forEach(o -> service.sendMessage(o.toString() + ", chunk " + counter));

        if (crash()) {
            logger.error("Crashing chuck {}!", counter);
            throw new RuntimeException("Crashing batch!");
        } else {
            logger.info("Not crashing chunk {}", counter);
        }
    }

    boolean crash() {
        var crashCount = getCrashCount();
        if (crashCount == -1) {
            return false;
        }

        return counter % crashCount == 0;
    }

    int getCrashCount() {
        return Integer.valueOf(BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId()).getProperty("crashCount"));
    }
}
