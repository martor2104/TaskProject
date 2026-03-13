package com.TaskProject.Entity;

import com.TaskProject.DTO.TaskDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

    public Task(){}

    public Task(Long id, String title, String description, LocalDate date, Priority priority, Status status, Project project, User assignedUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.status = status;
        this.project = project;
        this.assignedUser = assignedUser;
    }

    public Task(TaskDTO taskDTO){
        this.id = taskDTO.getId();
        this.title = taskDTO.getTitle();
        this.description = taskDTO.getDescription();
        this.date = taskDTO.getDate();
        this.priority = taskDTO.getPriority();
        this.status = taskDTO.getStatus();
        this.project = taskDTO.getProject();
        this.assignedUser = taskDTO.getAssignedUser();
    }

    public static TaskDTO toDTO(Task task){
        if (task == null){
            return null;
        }
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDate(),
                task.getPriority(),
                task.getStatus(),
                task.getProject(),
                task.getAssignedUser()
        );
    }

    @Override
    public String toString() {
        return "Task{" +
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
