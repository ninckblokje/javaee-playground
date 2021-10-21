package ninckblokje.playground.javaee.batch.helloworld;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class HelloWorldItemProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        var counter = (Integer) item;
        return String.format("Hello world %d", counter);
    }
}
