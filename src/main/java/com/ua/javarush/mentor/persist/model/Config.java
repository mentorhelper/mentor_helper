package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CONFIG")
@SequenceGenerator(name = "CONFIG_SEQ", sequenceName = "config_seq", allocationSize = 1)
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIG_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "VALUE", length = 100, nullable = false)
    private String value;
}
