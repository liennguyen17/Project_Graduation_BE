package com.ltw.model;

import com.ltw.domain.validator.UserValidator;
import com.ltw.importFileExcel.ExcelData;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

    @Column(name = "role")
    private String role;

    @Column(name = "user_code",unique = true)
    @Size(max = 50)
    private String userCode;

    @Column(name = "class_name")
    @Size(max = 50)
    private String className;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

//        @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role roleId;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public List<ExcelData.ErrorDetail> validateInformationDetailError(List<ExcelData.ErrorDetail> errorDetailList){
        if(!UserValidator.validateName(name)){
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(0).errMsg("Họ và tên không hợp lệ!").build());
        }
        if(!UserValidator.validateUsername(username)){
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(6).errMsg("Tên đăng nhập không hợp lệ!").build());
        }
        return errorDetailList;
    }

}