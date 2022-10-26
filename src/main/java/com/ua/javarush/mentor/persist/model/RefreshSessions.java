package com.ua.javarush.mentor.persist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ua.javarush.mentor.enums.DeviceType;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "REFRESH_SESSION")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "SQ_REFRESH_SESSION_ID_GENERATOR", sequenceName = "REFRESH_SESSION_SEQ", allocationSize = 1)
public class RefreshSessions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REFRESH_SESSION_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NonNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NonNull
    @Column(name = "TOKEN", nullable = false)
    private UUID refreshToken;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "DEVICE_TYPE", nullable = false)
    private DeviceType deviceType;

    @NonNull
    @Column(name = "FINGERPRINT", nullable = false)
    private String fingerPrint;

    @NonNull
    @Column(name = "DATE_OF_EXPIRATION", nullable = false)
    private Instant expiredDate;

    @NonNull
    @Column(name = "DATE_OF_CREATION", nullable = false)
    private Instant createdDate;
}
