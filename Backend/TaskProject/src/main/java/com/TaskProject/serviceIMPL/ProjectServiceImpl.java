package com.TaskProject.serviceIMPL;

import com.TaskProject.DTO.ProjectDTO;
import com.TaskProject.Entity.*;
import com.TaskProject.repository.ProjectRepository;
import com.TaskProject.repository.ProjectUserRoleRepository;
import com.TaskProject.service.ProjectService;
import com.TaskProject.service.ProjectUserRoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUserRoleRepository projectUserRoleRepository;


    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO, User currentUser) {
        Project project = new Project(projectDTO);
        Project saved = projectRepository.save(project);

        ProjectUserRole adminRole = new ProjectUserRole();
        adminRole.setUser(currentUser);
        adminRole.setProject(saved);
        adminRole.setRole(Role.ADMIN);
        projectUserRoleRepository.save(adminRole);

        return Project.toDTO(saved);
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
        return Project.toDTO(project);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(Project::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getProjectsByUser(Long userId) {
        return projectUserRoleRepository.findByUserId(userId)
                .stream()
                .map(projectUserRole -> Project.toDTO(projectUserRole.getProject()))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO, User currentUser) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));

        validateEditorOrAdmin(id, currentUser);

        existing.setName(projectDTO.getName());
        existing.setDescription(projectDTO.getDescription());

        return Project.toDTO(projectRepository.save(existing));
    }

    @Override
    public void deleteProject(Long id, User currentUser) {

        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }

        validateAdmin(id, currentUser);

        projectRepository.deleteById(id);

    }


    private void validateAdmin(Long projectId, User currentUser) {
        // SystemRole.ADMIN puede siempre
        if (currentUser.getSystemRole().equals(SystemRole.ADMIN)) return;

        ProjectUserRole userRole = projectUserRoleRepository
                .findByUserIdAndProjectId(currentUser.getId(), projectId)
                .orElseThrow(() -> new AccessDeniedException("You are not a member of this project"));

        if (!userRole.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only a project ADMIN can perform this action");
        }
    }

    private void validateEditorOrAdmin(Long projectId, User currentUser) {
        // SystemRole.ADMIN puede siempre
        if (currentUser.getSystemRole().equals(SystemRole.ADMIN)) return;

        ProjectUserRole userRole = projectUserRoleRepository
                .findByUserIdAndProjectId(currentUser.getId(), projectId)
                .orElseThrow(() -> new AccessDeniedException("You are not a member of this project"));

        if (userRole.getRole().equals(Role.VIEWER)) {
            throw new AccessDeniedException("You need EDITOR or ADMIN role to perform this action");
        }
    }

}
