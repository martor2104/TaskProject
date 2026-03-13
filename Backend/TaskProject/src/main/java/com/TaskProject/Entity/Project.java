package com.TaskProject.Entity;

import com.TaskProject.DTO.ProjectDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String  description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectUserRole> members = new HashSet<>();

    public Project(){}

    public Project(Long id, String name, String description, Set<Task> tasks, Set<ProjectUserRole> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.members = members;
    }

    public Project(ProjectDTO projectDTO){
        this.id = projectDTO.getId();
        this.name = projectDTO.getName();
        this.description = projectDTO.getDescription();
        this.tasks = projectDTO.getTasks();
        this.members = projectDTO.getMembers();
    }

    public static ProjectDTO toDTO(Project project){
        if(project == null){
            return null;
        }
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTasks(),
                project.getMembers()
        );
    }

    @Override
    public String toString() {
        return "Project{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                ", members=" + members +
                '}';
    }
}
