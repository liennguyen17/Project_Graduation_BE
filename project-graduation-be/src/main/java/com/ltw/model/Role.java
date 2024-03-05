package com.ltw.model;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.util.Collection;
import java.util.Set;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role", schema = "db_graduation")
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;
//    @OneToMany(mappedBy = "roleId")
//    private Set<User> user;

//    @OneToMany(mappedBy = "roleId")
//    private Set<User> users;
//    //
//    @ManyToMany
//    @JoinTable(
//            name = "role_permission",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "role_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "permission_id", referencedColumnName = "permission_id"))
//    private Collection<Permission> permissions;

}