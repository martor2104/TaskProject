package com.TaskProject.DTO;

import com.TaskProject.Entity.Priority;
import com.TaskProject.Entity.Project;
import com.TaskProject.Entity.Status;
import com.TaskProject.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private Priority priority;
    private Status status;
    private Project project;
    private User assignedUser;

    public TaskDTO(Long id, String title, String description, LocalDate date, Priority priority, Status status, Project project, User assignedUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.status = status;
        this.project = project;
        this.assignedUser = assignedUser;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", priority=" + priority +
                ", status=" + status +
                ", project=" + project +
                ", assignedUser=" + assignedUser +
                '}';
    }
}
