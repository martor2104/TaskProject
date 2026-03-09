package com.TaskProject.serviceIMPL;

import com.TaskProject.DTO.TaskDTO;
import com.TaskProject.Entity.Priority;
import com.TaskProject.Entity.Status;
import com.TaskProject.Entity.Task;
import com.TaskProject.repository.TaskRepository;
import com.TaskProject.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = new Task(taskDTO);
        Task saved = taskRepository.save(task);
        return Task.toDTO(saved);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return Task.toDTO(task);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByAssignedUser(Long userId) {
        return taskRepository.findByAssignedUserId(userId)
                .stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        existing.setTitle(taskDTO.getTitle());
        existing.setDescription(taskDTO.getDescription());
        existing.setDate(taskDTO.getDate());
        existing.setPriority(taskDTO.getPriority());
        existing.setStatus(taskDTO.getStatus());
        existing.setProject(taskDTO.getProject());
        existing.setAssignedUser(taskDTO.getAssignedUser());

        return Task.toDTO(taskRepository.save(existing));
    }

    @Override
    public TaskDTO updateTaskStatus(Long id, Status status) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        existing.setStatus(status);
        return Task.toDTO(taskRepository.save(existing));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
