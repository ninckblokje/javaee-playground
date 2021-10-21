package ninckblokje.playground.javaee.listener;

import ninckblokje.playground.javaee.entity.JmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.atomic.AtomicInteger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = "HelloWorldQueue"
        ),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"
        )
})
public class HelloWorldMessageListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(HelloWorldMessageListener.class);

    private static final AtomicInteger counter = new AtomicInteger(0);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        try {
            if (message.getJMSRedelivered()) {
                logger.error("Redelivery");
            }

            var text = ((TextMessage) message).getText();
            var count = counter.addAndGet(1);
            logger.info("Received message {}: {}", count, text);

            em.persist(new JmsMessage(text));

            if (count % 20 == 0) {
                logger.error("JMS message {}!", count);
                throw new RuntimeException("Crashing JMS!");
            } else {
                logger.info("Not crashing JMS message {}", count);
            }
        } catch (JMSException ex) {
            logger.error("Exception", ex);
            throw new RuntimeException(ex);
        }
    }
}
