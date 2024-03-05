//package com.ltw.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "role_permission", schema = "db_graduation")
//public class RolePermission {
//    @EmbeddedId
//    private RolePermissionId id;
//
//    @MapsId("roleId")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "role_id", nullable = false)
//    private Role role;
//
//    @MapsId("permissionId")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "permission_id", nullable = false)
//    private Permission permission;
//
//}