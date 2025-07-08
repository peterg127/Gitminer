package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * Get all comments.
     *
     * @return A list of all comments.
     */
    @Operation(summary = "Get all comments", description = "Retrieve a list of all comments in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of comments.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    /**
     * Get a comment by its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return The comment with the given ID, or null if not found.
     */
    @Operation(summary = "Get a comment by ID", description = "Retrieve a comment by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found for the given ID.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable String id) {
        return commentRepository.findById(id).orElse(null);
    }
}
