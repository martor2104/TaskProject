package com.TaskProject.repository;

import com.TaskProject.Entity.ProjectUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectUserRoleRepository extends JpaRepository<ProjectUserRole, Long> {

    Optional<ProjectUserRole> findByUserIdAndProjectId(Long userId, Long projectId);
    List<ProjectUserRole> findByUserId(Long userId);
    List<ProjectUserRole> findByProjectId(Long projectId);
    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

}
