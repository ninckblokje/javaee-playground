package ninckblokje.playground.javaee.entity;

import javax.persistence.*;

@Entity
@Table(name = "JMS_MESSAGE")
public class JmsMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    public JmsMessage() {}

    public JmsMessage(String message) {
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
