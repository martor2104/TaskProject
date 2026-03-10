package com.TaskProject.serviceIMPL;

import com.TaskProject.DTO.ProjectUserRoleDTO;
import com.TaskProject.Entity.Project;
import com.TaskProject.Entity.ProjectUserRole;
import com.TaskProject.Entity.Role;
import com.TaskProject.Entity.User;
import com.TaskProject.repository.ProjectRepository;
import com.TaskProject.repository.ProjectUserRoleRepository;
import com.TaskProject.repository.UserRepository;
import com.TaskProject.service.ProjectUserRoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectUserRoleServiceImpl implements ProjectUserRoleService {

    private final ProjectUserRoleRepository projectUserRoleRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public ProjectUserRoleDTO assignRole(Long userId, Long projectId, Role role) {
        if (projectUserRoleRepository.existsByUserIdAndProjectId(userId, projectId)) {
            throw new IllegalArgumentException("User already has a role in this project");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        ProjectUserRole projectUserRole = new ProjectUserRole();
        projectUserRole.setUser(user);
        projectUserRole.setProject(project);
        projectUserRole.setRole(role);

        return ProjectUserRole.projectUserRoleDTO(projectUserRoleRepository.save(projectUserRole));
    }

    @Override
    public ProjectUserRoleDTO updateRole(Long userId, Long projectId, Role role) {
        ProjectUserRole existing = projectUserRoleRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found for this user in this project"));

        existing.setRole(role);
        return ProjectUserRole.projectUserRoleDTO(projectUserRoleRepository.save(existing));
    }

    @Override
    public void removeRole(Long userId, Long projectId) {
        ProjectUserRole existing = projectUserRoleRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found for this user in this project"));

        projectUserRoleRepository.delete(existing);
    }

    @Override
    public List<ProjectUserRoleDTO> getRolesByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return projectUserRoleRepository.findByUserId(userId)
                .stream()
                .map(ProjectUserRole::projectUserRoleDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProjectUserRoleDTO> getRolesByProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
        return projectUserRoleRepository.findByProjectId(projectId)
                .stream()
                .map(ProjectUserRole::projectUserRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectUserRoleDTO getRoleByUserAndProject(Long userId, Long projectId) {
        return ProjectUserRole.projectUserRoleDTO(
                projectUserRoleRepository.findByUserIdAndProjectId(userId, projectId)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found for this user in this project"))
        );
    }

}
