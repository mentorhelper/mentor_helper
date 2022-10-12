package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TIMETABLE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TimeTable implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "GROUP_ID", unique = true, nullable = false)
    private Integer groupId;
    @Column(name = "LECTURE_ID", unique = true, nullable = false)
    private Integer lectureId;
    @Column(name = "START_DATETIME", nullable = false)
    private LocalDateTime startDateTime;
    @Column(name = "FINISH_DATETIME", nullable = false)
    private LocalDateTime finishDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TimeTable timeTable = (TimeTable) o;
        return id != null && Objects.equals(id, timeTable.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
