package br.ufrgs.inf.pet.dinoapi.entity.faq;

import br.ufrgs.inf.pet.dinoapi.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "faq_user")
public class FaqUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SEQUENCE_NAME = "faq_user_seq";

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "faq_id", referencedColumnName = "id")
    private Faq faq;

    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public FaqUser() {}

    public FaqUser(Faq faq, User user) {
        this.faq = faq;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Faq getFaq() {
        return faq;
    }

    public void setFaq(Faq faq) {
        this.faq = faq;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}