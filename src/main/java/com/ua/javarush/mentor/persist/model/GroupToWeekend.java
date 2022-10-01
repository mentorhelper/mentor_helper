package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "group_to_weekend")
@Data
public class GroupToWeekend implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private Group groupId;
    @Column(name = "number_of_day", nullable = false)
    private Integer numberOfDay;
}
