package com.ua.javarush.mentor.persist.model;

import liquibase.datatype.core.NVarcharType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "LECTION")
@Data
public class Lection implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "MODULE_ID", length = 500, nullable = false)
    private String moduleId;
    @Column(name = "NAME", length = 500, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", length = 5000, nullable = false)
    private String description;
    @Column(name = "LINK_TO_PRESENTATION", length = 500, nullable = false)
    private String linkToPresentation;
    @Column(name = "LINK_TO_LESSON", length = 500, nullable = false)
    private String linkToLesson;
    @Column(name = "LOCALE", length = 20, nullable = false)
    private String locale;
}