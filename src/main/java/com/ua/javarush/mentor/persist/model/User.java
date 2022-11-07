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
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё]{2,100}", message = "First name must contain only letters and be between 2 and 100 characters long")
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё]{2,100}", message = "Last name must contain only letters and be between 2 and 100 characters long")
    private String lastName;

    @Column(name = "COUNTRY", length = 20, nullable = false)
    private String country;

    @Column(name = "REGISTERED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;

    @Column(name = "TELEGRAM_ID", unique = true)
    private Long telegramId;

    @Column(name = "TELEGRAM_NICKNAME", length = 200)
    private String telegramNickname;

    @Column(name = "SALARY_PER_HOUR")
    private Integer salaryPerHour;

    @Column(name = "SALARY_CURRENCY", length = 20)
    private String salaryCurrency;

    @Column(name = "SECRET_PHRASE", length = 50)
    private String secretPhrase;

    @Column(name = "EMAIL", length = 50, nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email")
    private String email;

    @Column(name = "EMAIL_VERIFIED")
    private boolean emailVerified;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "LOCKED")
    private boolean locked;

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
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Invalid username")
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "Invalid password")
    private String password;

    @OneToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role roleId;

    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RefreshSessions> refreshSessions = new HashSet<>();

    @Column(name = "DATE_OF_SENDING_RESET_PASSWORD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfSendingResetPassword;

    @Column(name = "RESET_PASSWORD_CODE", length = 100)
    private String resetPasswordCode;

    @Column(name = "DATE_OF_RESET_PASSWORD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfResetPassword;

    @Column(name = "COUNT_OF_RESET_PASSWORD")
    private Integer countOfResetPassword;

    @Column(name = "LAST_PASSWORD_CHANGE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange;

    @Column(name = "AVATAR_KEY")
    private String avatarKey;

    @Column(name = "WALLPAPER_KEY")
    private String wallpaperKey;

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
