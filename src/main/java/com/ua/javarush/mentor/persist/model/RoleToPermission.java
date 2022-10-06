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
@Table(name = "ROLE_TO_PERMISSION")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RoleToPermission implements Serializable {
    @Id
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;
    @Column(name = "PERMISSION", length = 200, nullable = false)
    private String permission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoleToPermission that = (RoleToPermission) o;
        return roleId != null && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
