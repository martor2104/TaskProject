package com.TaskProject.Entity;

import com.TaskProject.DTO.UserDTO;
import com.TaskProject.auth.RegisterRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private SystemRole systemRole;

    @OneToMany(mappedBy = "user")
    private Set<ProjectUserRole> projectRole;

    public User(){

    }

    public User(Long id, String username, String email, String password, SystemRole systemRole, Set<ProjectUserRole> projectRole) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.systemRole = systemRole;
        this.projectRole = projectRole;
    }

    public User(RegisterRequest request){
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.systemRole = request.getSystemRole() != null ? request.getSystemRole() : SystemRole.USER;
    }

    public static UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getSystemRole(),
                user.getProjectRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(systemRole.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", systemRole='" + systemRole + '\'' +
                ", projectRole=" + projectRole +
                '}';
    }
}
