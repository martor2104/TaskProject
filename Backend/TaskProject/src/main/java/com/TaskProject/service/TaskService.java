package com.TaskProject.service;

import com.TaskProject.DTO.TaskDTO;
import com.TaskProject.Entity.Priority;
import com.TaskProject.Entity.Status;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    List<TaskDTO> getAllTasks();

    List<TaskDTO> getTasksByProject(Long projectId);

    List<TaskDTO> getTasksByAssignedUser(Long userId);

    List<TaskDTO> getTasksByStatus(Status status);

    List<TaskDTO> getTasksByPriority(Priority   priority);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    TaskDTO updateTaskStatus(Long id, Status status);

    void deleteTask(Long id);
}
