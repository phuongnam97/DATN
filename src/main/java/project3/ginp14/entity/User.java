package project3.ginp14.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotBlank(message = "Vui lòng nhập tên người dùng")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 5, message = "Mật khẩu quá ngắn, mật khẩu chứa ít nhất 5 ký tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập tên")
    @Column(name = "full_name")
    private String fullname;


    @Column(name = "gender")
    @NotNull(message = "Vui lòng lựa chọn giới tính")
    private int gender;

    @Column(name = "address")
    private String address;


    @Column(name = "email")
    @NotBlank(message = "Vui lòng nhập email")
    @Email(message = "Email không được nhập đúng")
    private String email;

    @Column(name = "dob")
    private String DOB;


    @Column(name = "telephone")
    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Size(max = 11,message = "Số điện thoại dài tối đa 11 ký tự")
    private String telephone;

    @OneToOne
    @JoinColumn(name="role_id")
    private Role role;

    @Column(name = "status")
    private int status;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updated_at;

    public User(@NotBlank(message = "Vui lòng nhập tên người dùng") String username, @NotBlank(message = "Vui lòng nhập mật khẩu") @Size(min = 5, message = "Mật khẩu quá ngắn, mật khẩu chứa ít nhất 5 ký tự") String password, @NotBlank(message = "Vui lòng nhập tên") String fullname, @NotNull(message = "Vui lòng lựa chọn giới tính") int gender, String address, @NotBlank(message = "Vui lòng nhập email") @Email(message = "Email không được nhập đúng") String email, String DOB, @NotBlank(message = "Vui lòng nhập số điện thoại") @Size(max = 11, message = "Số điện thoại dài tối đa 11 ký tự") String telephone, Role role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.DOB = DOB;
        this.telephone = telephone;
        this.role = role;
    }

    public User(@NotBlank(message = "Vui lòng nhập tên người dùng") String username, @NotBlank(message = "Vui lòng nhập mật khẩu") @Size(min = 5, message = "Mật khẩu quá ngắn, mật khẩu chứa ít nhất 5 ký tự") String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
