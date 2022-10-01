package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "group")
@Data
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "name", length = 100, nullable = false)
    private String nickname;
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "date_started")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStarted;
    @Column(name = "date_finished")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @Column(name = "slack_url", length = 200, nullable = false)
    private String slackUrl;
    @Column(name = "students_count_on_start", nullable = false)
    private Integer studentCountOnStart;
    @Column(name = "students_count_on_end")
    private Integer studentsCountOnEnd;
    @Column(name = "hours_per_lecture", nullable = false)
    private Integer hoursPerLecture;
}
