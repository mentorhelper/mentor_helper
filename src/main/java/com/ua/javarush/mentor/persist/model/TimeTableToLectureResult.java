package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TIMETABLE_TO_LECTURE_RESULT")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TimeTableToLectureResult implements Serializable {
    @Id
    @Column(name = "TIMETABLE_ID", unique = true, nullable = false)
    private Long timetableId;
    @Column(name = "LECTURE_RESULT_ID", nullable = false)
    private Integer lectureResultId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TimeTableToLectureResult that = (TimeTableToLectureResult) o;
        return timetableId != null && Objects.equals(timetableId, that.timetableId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
