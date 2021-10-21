package ninckblokje.playground.javaee.entity;

import javax.persistence.*;

@Entity
@Table(name = "BATCH_MESSAGE")
public class BatchMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    public BatchMessage() {}

    public BatchMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
