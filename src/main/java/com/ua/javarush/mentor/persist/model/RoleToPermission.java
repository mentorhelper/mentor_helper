package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROLE_TO_PERMISSION")
@Data
public class RoleToPermission implements Serializable {
    @Id
    @Column(name = "ROLE_ID", nullable = false)
    private Integer roleId;
    @Column(name = "PERMISSION", length = 200, nullable = false)
    private String permission;
}
