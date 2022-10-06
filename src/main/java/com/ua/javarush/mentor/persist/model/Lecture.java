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
@Table(name = "LECTURE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Lecture implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "MODULE_ID", length = 500, nullable = false)
    private String moduleId;
    @Column(name = "NAME", length = 500, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", length = 5000, nullable = false)
    private String description;
    @Column(name = "LINK_TO_PRESENTATION", length = 500, nullable = false)
    private String linkToPresentation;
    @Column(name = "LINK_TO_LESSON", length = 500, nullable = false)
    private String linkToLesson;
    @Column(name = "LOCALE", length = 20, nullable = false)
    private String locale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Lecture lecture = (Lecture) o;
        return id != null && Objects.equals(id, lecture.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}