package aiss.gitminer.controller;

import aiss.gitminer.model.*;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    /**
     * Save a project.
     *
     * @param project The project to save.
     * @return The saved project.
     */
    @Operation(summary = "Save a project", description = "Save a new project to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project saved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid project.", content = @Content)
    })
    @PostMapping("/gitminer/projects")
    public ResponseEntity<Project> save_project(
            @RequestBody Project project) {
        if (project == null) {
            return ResponseEntity.badRequest().build();
        }
        Project saved = projectRepository.save(project);
        return ResponseEntity.ok(saved);
    }

    /**
     * Get all projects.
     *
     * @return A list of all projects.
     */
    @Operation(summary = "Get all projects", description = "Retrieve a list of all projects in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all projects retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/gitminer/projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Get a project by its ID.
     *
     * @param id The ID of the project to retrieve.
     * @return The project with the given ID, or 404 if not found.
     */
    @Operation(summary = "Get a project by ID", description = "Retrieve a project by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/gitminer/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
