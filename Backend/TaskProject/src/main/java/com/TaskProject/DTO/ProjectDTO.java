package com.TaskProject.DTO;

import com.TaskProject.Entity.ProjectUserRole;
import com.TaskProject.Entity.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProjectDTO {

    private Long Id;
    private String name;
    private String  description;
    private Set<Task> tasks = new HashSet<>();
    private Set<ProjectUserRole> members = new HashSet<>();

    public ProjectDTO(Long id, String name, String description, Set<Task> tasks, Set<ProjectUserRole> members) {
        Id = id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
        this.members = members;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                ", members=" + members +
                '}';
    }
}
