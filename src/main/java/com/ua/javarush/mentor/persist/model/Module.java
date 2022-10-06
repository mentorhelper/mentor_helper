package com.ua.javarush.mentor.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MODULE")
@Data
public class Module implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "MODULE_NUMBER", nullable = false)
    private int moduleNumber;
    @Column(name = "LECTION_COUNT", nullable = false)
    private int lectionCount;
    @Column(name = "NAME", length = 200, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", length = 2000, nullable = false)
    private String description;
    @Column(name = "LINK_TO_SITE", length = 500, nullable = false)
    private String linkToSite;
}

