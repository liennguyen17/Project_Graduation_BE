//package com.ltw.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.Hibernate;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Getter
//@Setter
//@Embeddable
//public class RolePermissionId implements Serializable {
//    private static final long serialVersionUID = -4017508650980367226L;
//    @Column(name = "role_id", nullable = false)
//    private Integer roleId;
//
//    @Column(name = "permission_id", nullable = false)
//    private Integer permissionId;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        RolePermissionId entity = (RolePermissionId) o;
//        return Objects.equals(this.permissionId, entity.permissionId) &&
//                Objects.equals(this.roleId, entity.roleId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(permissionId, roleId);
//    }
//
//}