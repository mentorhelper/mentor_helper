package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BOT_ENTER_HISTORY")
@Data
@SequenceGenerator(name = "BOT_ENTER_HISTORY_SEQ_GENERATOR", sequenceName = "BOT_ENTER_SEQ", allocationSize = 1)
public class BotEnterHistory implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "ATTEMPT_DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date attemptDateTime;
    @Column(name = "TELEGRAM_ID", unique = true, nullable = false)
    private Integer telegramId;
    @Column(name = "TELEGRAM_NICKNAME", length = 200, nullable = false)
    private String telegramNickname;
    @Column(name = "TELEGRAM_FIRSTNAME", length = 100, nullable = false)
    private String firstName;
    @Column(name = "TELEGRAM_LASTNAME", length = 100, nullable = false)
    private String lastName;
    @Column(name = "COMMAND", length = 100, nullable = false)
    private String command;
}
