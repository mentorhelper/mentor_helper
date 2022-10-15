package com.ua.javarush.mentor.persist.model;

import com.ua.javarush.mentor.enums.NotificationProvider;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NOTIFICATIONS")
@SequenceGenerator(name = "NOTIFICATION_SEQ_GENERATOR", sequenceName = "NOTIFICATIONS_SEQ", allocationSize = 1)
public class Notification {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTIFICATION_SEQ_GENERATOR")
    private Long id;

    @Column(name = "DATA")
    private String data;

    @Column(name = "DATE_OF_SENDING")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "NOTIFICATION_PROVIDER")
    @Enumerated(EnumType.STRING)
    private NotificationProvider notificationProvider;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notification that = (Notification) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
