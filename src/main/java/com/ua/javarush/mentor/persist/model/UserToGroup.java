package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_to_group")
@Data
public class UserToGroup implements Serializable {
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;
    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private Group groupId;
}
