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
@Table(name = "LECTURE_RESULT")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LectureResult implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "LECTURE_ID", nullable = false)
    private Integer lectureId;
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;
    @Column(name = "HOURS_IN_LECTURE", nullable = false)
    private Integer hoursInLecture;
    @Column(name = "IS_USER_LEADING", nullable = false)
    private Boolean isUserLeading;
    @Column(name = "WAS_AT_LECTURE", nullable = false)
    private Boolean wasAtLecture;
    @Column(name = "OTHER_USER_WAS_ON_LECTURE", nullable = false)
    private Boolean otherUserWasOnLecture;
    @Column(name = "OTHER_USER_HOURS_ON_LECTURE", nullable = false)
    private Integer otherUserHoursOnLecture;
    @Column(name = "COMMENT")
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LectureResult that = (LectureResult) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
