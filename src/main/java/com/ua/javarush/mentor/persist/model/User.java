package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "`USER`")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", allocationSize = 1)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;
    @Column(name = "COUNTRY", length = 20, nullable = false)
    private String country;
    @Column(name = "REGISTERED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;
    @Column(name = "TELEGRAM_ID", unique = true)
    private Long telegramId;
    @Column(name = "TELEGRAM_NICKNAME", length = 200, nullable = false)
    private String telegramNickname;
    @Column(name = "SALARY_PER_HOUR")
    private Integer salaryPerHour;
    @Column(name = "SALARY_CURRENCY", length = 20, nullable = false)
    private String salaryCurrency;
    @Column(name = "SECRET_PHRASE", length = 50, nullable = false)
    private String secretPhrase;
    @OneToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
