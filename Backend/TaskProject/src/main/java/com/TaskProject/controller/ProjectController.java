package com.TaskProject.controller;

import com.TaskProject.DTO.ProjectDTO;
import com.TaskProject.Entity.User;
import com.TaskProject.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "Endpoints de gestión de proyectos")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Crear proyecto")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO,
                                                    @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectDTO, currentUser));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto por ID")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los proyectos")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener proyectos por usuario")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(projectService.getProjectsByUser(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proyecto")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id,
                                                    @RequestBody ProjectDTO projectDTO,
                                                    @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO, currentUser));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proyecto")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id,
                                              @AuthenticationPrincipal User currentUser) {
        projectService.deleteProject(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
