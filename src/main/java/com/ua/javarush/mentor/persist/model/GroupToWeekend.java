package com.ua.javarush.mentor.persist.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "GROUP_TO_WEEKEND")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GroupToWeekend implements Serializable {
    @Id
    @Column(name = "GROUP_ID", nullable = false)
    private Long groupId;
    @Column(name = "NUMBER_OF_DAY", nullable = false)
    private Integer numberOfDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupToWeekend that = (GroupToWeekend) o;
        return groupId != null && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
