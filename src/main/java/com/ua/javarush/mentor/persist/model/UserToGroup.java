package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_TO_GROUP")
@Data
public class UserToGroup implements Serializable {
    @Id
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long userId;
    @OneToOne
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", nullable = false)
    private Group groupId;
}
