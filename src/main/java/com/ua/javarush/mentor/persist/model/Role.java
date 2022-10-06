package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SequenceGenerator(name = "ROLE_SEQ_GENERATOR", sequenceName = "ROLE_SEQ", allocationSize = 1)
public class Role implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_SEQ_GENERATOR")
    private Long id;
    @Column(name = "NAME", length = 200, unique = true, nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
