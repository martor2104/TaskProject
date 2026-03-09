package com.TaskProject.DTO;

import com.TaskProject.Entity.ProjectUserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<ProjectUserRole> projectUserRole;

    public UserDTO(Long id, String username, String email, String password, Set<ProjectUserRole> projectUserRole){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.projectUserRole = projectUserRole;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", projectUserRole=" + projectUserRole +
                '}';
    }
}
