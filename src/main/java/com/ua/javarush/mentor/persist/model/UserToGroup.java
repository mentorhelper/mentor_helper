package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_to_group")
@Data
public class UserToGroup {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User userId;
    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private Group groupId;
}
