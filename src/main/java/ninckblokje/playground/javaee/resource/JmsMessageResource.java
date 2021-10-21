package ninckblokje.playground.javaee.resource;

import ninckblokje.playground.javaee.entity.BatchMessage;
import ninckblokje.playground.javaee.entity.JmsMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/jms/message")
public class JmsMessageResource {

    @PersistenceContext
    private EntityManager em;

    @GET
    public List<JmsMessage> getAll() {
        var query = em.createNamedQuery("JmsMessage.findAll", JmsMessage.class);
        return query.getResultList();
    }

    @GET
    @Path("/count")
    public long countAll() {
        var query = em.createNamedQuery("JmsMessage.countAll", Long.class);
        return query.getSingleResult();
    }
}
