package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
    @Column(name = "country", length = 20, nullable = false)
    private String country;
    @Column(name = "registered_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;
    @Column(name = "telegram_id", unique = true, nullable = false)
    private Integer telegramId;
    @Column(name = "telegram_nickname", length = 200, nullable = false)
    private String telegramNickname;
    @Column(name = "salary_per_hour")
    private Integer salaryPerHour;
    @Column(name = "salary_currency", length = 20, nullable = false)
    private String salaryCurrency;
    @Column(name = "secret_phrase", length = 50, nullable = false)
    private String secretPhrase;
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role roleId;
}
