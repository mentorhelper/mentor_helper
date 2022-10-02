package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROLE")
@Data
@SequenceGenerator(name = "ROLE_SEQ_GENERATOR", sequenceName = "ROLE_SEQ", allocationSize = 1)
public class Role implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_SEQ_GENERATOR")
    private Long id;
    @Column(name = "NAME", length = 200, unique = true, nullable = false)
    private String name;
}
