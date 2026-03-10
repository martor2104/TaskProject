package com.TaskProject.controller;

import com.TaskProject.DTO.ProjectUserRoleDTO;
import com.TaskProject.Entity.Role;
import com.TaskProject.service.ProjectUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-roles")
@RequiredArgsConstructor
@Tag(name = "Project Roles", description = "Endpoints de gestión de roles por proyecto")
public class ProjectUserRoleController {

    private final ProjectUserRoleService projectUserRoleService;

    @PostMapping("/assign")
    @Operation(summary = "Asignar rol a usuario en proyecto")
    public ResponseEntity<ProjectUserRoleDTO> assignRole(@RequestParam Long userId,
                                                         @RequestParam Long projectId,
                                                         @RequestParam Role role) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectUserRoleService.assignRole(userId, projectId, role));
    }

    @PatchMapping("/update")
    @Operation(summary = "Actualizar rol de usuario en proyecto")
    public ResponseEntity<ProjectUserRoleDTO> updateRole(@RequestParam Long userId,
                                                         @RequestParam Long projectId,
                                                         @RequestParam Role role) {
        return ResponseEntity.ok(projectUserRoleService.updateRole(userId, projectId, role));
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Eliminar rol de usuario en proyecto")
    public ResponseEntity<Void> removeRole(@RequestParam Long userId,
                                           @RequestParam Long projectId) {
        projectUserRoleService.removeRole(userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener todos los roles de un usuario")
    public ResponseEntity<List<ProjectUserRoleDTO>> getRolesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(projectUserRoleService.getRolesByUser(userId));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Obtener todos los miembros de un proyecto")
    public ResponseEntity<List<ProjectUserRoleDTO>> getRolesByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectUserRoleService.getRolesByProject(projectId));
    }

    @GetMapping
    @Operation(summary = "Obtener rol de un usuario en un proyecto")
    public ResponseEntity<ProjectUserRoleDTO> getRoleByUserAndProject(@RequestParam Long userId,
                                                                      @RequestParam Long projectId) {
        return ResponseEntity.ok(projectUserRoleService.getRoleByUserAndProject(userId, projectId));
    }
}
