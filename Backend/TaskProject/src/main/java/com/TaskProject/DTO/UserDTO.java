package com.TaskProject.DTO;

import com.TaskProject.Entity.ProjectUserRole;
import com.TaskProject.Entity.SystemRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private SystemRole systemRole;
    private Set<ProjectUserRole> projectUserRole;

    public UserDTO(Long id, String name, String email, String password, SystemRole systemRole, Set<ProjectUserRole> projectUserRole){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.systemRole = systemRole;
        this.projectUserRole = projectUserRole;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", systemRole='" + systemRole + '\'' +
                ", projectUserRole=" + projectUserRole +
                '}';
    }
}
