package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ROLE_TO_PERMISSION")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SequenceGenerator(name = "ROLE_PERMISSION_SEQ_GENERATOR", sequenceName = "ROLE_PERMISSION_SEQ", allocationSize = 1)
public class RoleToPermission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_PERMISSION_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;
    @Column(name = "PERMISSION", length = 200, nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionType permission;

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
