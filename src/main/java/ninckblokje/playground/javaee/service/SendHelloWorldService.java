package ninckblokje.playground.javaee.service;

import ninckblokje.playground.javaee.entity.BatchMessage;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
@RequestScoped
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class SendHelloWorldService {

    @Inject
    private JMSContext jmsContext;
    @Resource(name = "HelloWorldQueue")
    private Queue queue;

    @PersistenceContext
    private EntityManager em;

    public void sendMessage() {
        sendMessage("Hello world called");
    }

    public void sendMessage(String msg) {
        em.persist(new BatchMessage(msg));
        jmsContext.createProducer().send(queue, msg);
    }
}
