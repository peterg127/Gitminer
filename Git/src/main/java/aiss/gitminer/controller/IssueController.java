package aiss.gitminer.controller;

import aiss.gitminer.model.*;
import aiss.gitminer.repository.IssueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    /**
     * Get all issues.
     *
     * @return A list of all issues.
     */
    @Operation(summary = "Get all issues", description = "Retrieve a list of all issues in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    /**
     * Get an issue by its ID.
     *
     * @param id The ID of the issue to retrieve.
     * @return The issue with the given ID, or null if not found.
     */
    @Operation(summary = "Get an issue by ID", description = "Retrieve an issue by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", description = "Issue not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{id}")
    public Issue getIssueById(
            @Parameter(description = "ID of the issue to retrieve")
            @PathVariable String id) {
        return issueRepository.findById(id).orElse(null);
    }

    /**
     * Get issues by their state.
     *
     * @param state The state of the issues to retrieve.
     * @return A list of issues with the given state.
     */
    @Operation(summary = "Get issues by state", description = "Retrieve issues filtered by state.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issues found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/status/{state}")
    public List<Issue> getIssuesByState(
            @Parameter(description = "State of the issues to retrieve")
            @PathVariable String state) {
        return issueRepository.findByState(state);
    }

    /**
     * Get comments for a given issue.
     *
     * @param id The ID of the issue for which to retrieve comments.
     * @return A list of comments for the given issue.
     */
    @Operation(summary = "Get comments for a given issue", description = "Retrieve comments for a specific issue.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "404", description = "Issue not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsByIssueId(
            @Parameter(description = "ID of the issue for which to retrieve comments")
            @PathVariable String id) {
        Issue issue = issueRepository.findById(id).orElse(null);
        return issue != null ? issue.getComments() : List.of();
    }
}
