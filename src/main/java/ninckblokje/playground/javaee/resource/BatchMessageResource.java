package ninckblokje.playground.javaee.resource;

import ninckblokje.playground.javaee.entity.BatchMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/batch/message")
public class BatchMessageResource {

    @PersistenceContext
    private EntityManager em;

    @GET
    public List<BatchMessage> getAll() {
        var query = em.createNamedQuery("BatchMessage.findAll", BatchMessage.class);
        return query.getResultList();
    }

    @GET
    @Path("/count")
    public long countAll() {
        var query = em.createNamedQuery("BatchMessage.countAll", Long.class);
        return query.getSingleResult();
    }
}
