package com.TaskProject.service;

import com.TaskProject.DTO.ProjectUserRoleDTO;
import com.TaskProject.Entity.Role;

import java.util.List;

public interface ProjectUserRoleService {

    ProjectUserRoleDTO assignRole(Long userId, Long projectId, Role role);
    ProjectUserRoleDTO updateRole(Long userId, Long projectId, Role role);
    void removeRole(Long userId, Long projectId);
    List<ProjectUserRoleDTO> getRolesByUser(Long userId);
    List<ProjectUserRoleDTO> getRolesByProject(Long projectId);
    ProjectUserRoleDTO getRoleByUserAndProject(Long userId, Long projectId);
}
