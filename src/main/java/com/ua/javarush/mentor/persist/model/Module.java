package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "MODULE")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "MODULE_SEQ_GENERATOR", sequenceName = "MODULE_SEQ", allocationSize = 1)
public class Module implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MODULE_SEQ_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "MODULE_NUMBER", nullable = false)
    private int moduleNumber;
    @Column(name = "LECTURE_COUNT", nullable = false)
    private int lectureCount;
    @Column(name = "NAME", unique = true ,length = 200, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", length = 2000, nullable = false)
    private String description;
    @Column(name = "LINK_TO_SITE", length = 500, nullable = false)
    private String linkToSite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Module module = (Module) o;
        return id != null && Objects.equals(id, module.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

