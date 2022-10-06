package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TIMETABLE")
@Data
public class TimeTable implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "GROUP_ID", unique = true, nullable = false)
    private Integer groupId;
    @Column(name = "LECTION_ID", unique = true, nullable = false)
    private Integer lectionId;
    @Column(name = "START_DATETIME", nullable = false)
    private LocalDateTime startDateTime;
    @Column(name = "FINISH_DATETIME", nullable = false)
    private LocalDateTime finishDateTime;
}
