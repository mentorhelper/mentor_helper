package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "GROUP")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SequenceGenerator(name = "GROUP_SEQ_GENERATOR", sequenceName = "GROUP_SEQ", allocationSize = 1)
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GROUP_SEQ_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "NAME", length = 100, nullable = false)
    private String nickname;
    @Column(name = "DATE_CREATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "DATE_STARTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStarted;
    @Column(name = "DATE_FINISHED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @Column(name = "SLACK_URL", length = 200, nullable = false)
    private String slackUrl;
    @Column(name = "STUDENTS_COUNT_ON_START", nullable = false)
    private Integer studentCountOnStart;
    @Column(name = "STUDENTS_COUNT_ON_END")
    private Integer studentsCountOnEnd;
    @Column(name = "HOURS_PER_LECTURE", nullable = false)
    private Integer hoursPerLecture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return id != null && Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
