package com.TaskProject.Entity;

import com.TaskProject.DTO.UserDTO;
import com.TaskProject.auth.RegisterRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<ProjectUserRole> projectRole;

    public User(Long id, String username, String email, String password, Set<ProjectUserRole> projectRole) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.projectRole = projectRole;
    }

    public User(RegisterRequest request){
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }

    public static UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getProjectRole()
        );
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", projectRole=" + projectRole +
                '}';
    }
}
