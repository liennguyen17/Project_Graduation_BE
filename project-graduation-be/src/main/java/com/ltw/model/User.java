package com.ltw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
@Entity
@Table(name = "user", schema = "db_graduation")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(max = 100)
    private String name;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "password")
    @Size(max = 100)
    private String password;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "subject")
    private String subject;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "role")
    private String role;

//        @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role roleId;

}