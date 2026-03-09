package com.TaskProject.repository;

import com.TaskProject.Entity.ProjectUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserRoleRepository extends JpaRepository<ProjectUserRole, Long> {
}
