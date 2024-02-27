package com.ltw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "masterdata", schema = "db_graduation")
public class MasterData {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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