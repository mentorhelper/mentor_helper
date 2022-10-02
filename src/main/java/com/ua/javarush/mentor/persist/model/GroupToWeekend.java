package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GROUP_TO_WEEKEND")
@Data
public class GroupToWeekend implements Serializable {
    @Id
    @Column(name = "GROUP_ID", nullable = false)
    private Long groupId;
    @Column(name = "NUMBER_OF_DAY", nullable = false)
    private Integer numberOfDay;
}
