package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "TIMETABLE_TO_LECTION_RESULT")
@Data
public class TimeTableToLectionResult implements Serializable {
    @Id
    @Column(name = "TIMETABLE_ID", unique = true, nullable = false)
    private Long timetableId;
    @Column(name = "LECTION_RESULT_ID", nullable = false)
    private Integer lectionResultId;
}
