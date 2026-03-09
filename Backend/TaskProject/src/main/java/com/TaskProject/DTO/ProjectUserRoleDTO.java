package com.TaskProject.DTO;

import com.TaskProject.Entity.Project;
import com.TaskProject.Entity.Role;
import com.TaskProject.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUserRoleDTO {

    private Long id;
    private User user;
    private Project project;
    private Role role;

    public ProjectUserRoleDTO(Long id, User user, Project project, Role role) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.role = role;
    }

    @Override
    public String toString() {
        return "ProjectUserRoleDTO{" +
                "id=" + id +
                ", user=" + user +
                ", project=" + project +
                ", role=" + role +
                '}';
    }
}
