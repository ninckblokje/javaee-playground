package ninckblokje.playground.javaee.batch.helloworld;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

@Named
public class HelloWorldItemReader extends AbstractItemReader {

    private int counter;

    @Override
    public Object readItem() throws Exception {
        if (counter == 100) {
            return null;
        }

        return counter++;
    }
}
