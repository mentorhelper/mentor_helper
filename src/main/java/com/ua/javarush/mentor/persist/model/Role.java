package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
@Data
public class Role implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "name", unique = true, nullable = false)
    private Integer name;
}
