package be.spring.app.model;

import be.spring.app.data.PollStatusEnum;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 29/05/16.
 */
@Entity
@Table(name = "polls")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Poll <T>{
    private Long id;
    private Poll poll;
    private String question;
    private DateTime startDate;
    private DateTime endDate;
    private PollStatusEnum status = PollStatusEnum.WAITING;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @ManyToOne
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @NotNull
    @Column(name = "start_date")
    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    @NotNull
    @Column(name = "end_date")
    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @NotNull
    public PollStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PollStatusEnum status) {
        this.status = status;
    }

    @Transient
    public abstract T getResult();

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", poll=" + poll +
                ", question='" + question + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}
