package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LECTION_RESULT")
@Data
public class LectionResult implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "LECTION_ID", nullable = false)
    private Integer lectionId;
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;
    @Column(name = "HOURS_IN_LECTION", nullable = false)
    private Integer hoursInLection;
    @Column(name = "IS_USER_LEADING", nullable = false)
    private Boolean isUserLeading;
    @Column(name = "WAS_AT_LECTION", nullable = false)
    private Boolean wasAtLection;
    @Column(name = "OTHER_USER_WAS_ON_LECTION", nullable = false)
    private Boolean otherUserWasOnLection;
    @Column(name = "OTHER_USER_HOURS_ON_lECTION", nullable = false)
    private Integer otherUserHoursOnLection;
    @Column(name = "COMMENT")
    private String comment;
}
