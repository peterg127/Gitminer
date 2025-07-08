package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

    @Autowired
    private CommitRepository commitRepository;

    /**
     * Get all commits.
     *
     * @return A list of all commits.
     */
    @Operation(summary = "Get all commits", description = "Retrieve a list of all commits in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of commits.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Commit.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping
    public List<Commit> getAllCommits() {
        return commitRepository.findAll();
    }

    /**
     * Get a commit by its ID.
     *
     * @param id The ID of the commit to retrieve.
     * @return The commit with the given ID, or null if not found.
     */
    @Operation(summary = "Get a commit by ID", description = "Retrieve a commit by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the commit.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Commit.class))),
            @ApiResponse(responseCode = "404", description = "Commit not found for the given ID.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{id}")
    public Commit getCommitById(@PathVariable String id) {
        return commitRepository.findById(id).orElse(null);
    }
}
