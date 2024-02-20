package com.ltw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "masterdata", schema = "projectgraduation")
public class Masterdatum {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "code")
    private String code;

    @Lob
    @Column(name = "name")
    private String name;

}