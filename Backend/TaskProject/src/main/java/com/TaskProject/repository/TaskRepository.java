package com.TaskProject.repository;

import com.TaskProject.Entity.Priority;
import com.TaskProject.Entity.Status;
import com.TaskProject.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssignedUserId(Long userId);
    List<Task> findByStatus(Status status);
    List<Task> findByPriority(Priority priority);

}
