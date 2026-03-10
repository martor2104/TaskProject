package com.TaskProject.service;

import com.TaskProject.DTO.ProjectDTO;
import com.TaskProject.Entity.User;

import java.util.List;

public interface ProjectService {

    ProjectDTO createProject(ProjectDTO projectDTO, User currentUser);
    ProjectDTO getProjectById(Long id);
    List<ProjectDTO> getAllProjects();
    List<ProjectDTO> getProjectsByUser(Long userId);
    ProjectDTO updateProject(Long id, ProjectDTO projectDTO, User currentUser);
    void deleteProject(Long id, User currentUser);
}
