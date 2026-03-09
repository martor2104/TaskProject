package com.TaskProject.Entity;

import com.TaskProject.DTO.ProjectUserRoleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProjectUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Role role;

    public ProjectUserRole(Long id, User user, Project project, Role role) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.role = role;
    }

    public ProjectUserRole(ProjectUserRoleDTO projectUserRoleDTO){
        this.id = projectUserRoleDTO.getId();
        this.user = projectUserRoleDTO.getUser();
        this.project = projectUserRoleDTO.getProject();
        this.role = projectUserRoleDTO.getRole();
    }

    public static ProjectUserRoleDTO projectUserRoleDTO(ProjectUserRole projectUserRole){
        if (projectUserRole == null){
            return null;
        }
        return new ProjectUserRoleDTO(
                projectUserRole.getId(),
                projectUserRole.getUser(),
                projectUserRole.getProject(),
                projectUserRole.getRole()
        );
    }

    @Override
    public String toString() {
        return "ProjectUserRole{" +
                "id=" + id +
                ", user=" + user +
                ", project=" + project +
                ", role=" + role +
                '}';
    }
}
