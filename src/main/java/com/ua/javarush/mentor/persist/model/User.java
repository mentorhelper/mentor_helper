package com.ua.javarush.mentor.persist.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "`USER`")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "EMAIL", length = 50)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email")
    private String email;
    @Column(name = "EMAIL_VERIFIED")
    private boolean emailVerified;
    @Column(name = "EMAIL_VERIFICATION_TOKEN", length = 100)
    private String emailConfirmationToken;
    @Column(name = "DATE_OF_CONFIRMATION_EMAIL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfConfirmationEmail;
    @Column(name = "DATE_OF_SENDING_EMAIL_CONFIRMATION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfSendingEmailConfirmation;
    @NonNull
    @Column(name = "USERNAME", nullable = false, length = 32)
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @OneToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role roleId;
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RefreshSessions> refreshSessions = new HashSet<>();

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
