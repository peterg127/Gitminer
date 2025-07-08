package com.aiss.githubminer.service;

import com.aiss.githubminer.model.Issue;
import com.aiss.githubminer.model.User;
import com.aiss.githubminer.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitHubIssueServiceTest {

    @Autowired
    GitHubIssueService service;

    @Test
    @DisplayName("Fetch issues from a repository")
    void fetchIssues() {
        String owner = "spring-projects";
        String repo = "spring-framework";

        List<Issue> issues = service.fetchIssues(owner, repo, 30, 10, 2);

        assertNotNull(issues, "The issue list should not be null");
        assertFalse(issues.isEmpty(), "The issue list should not be empty");

        Issue firstIssue = issues.get(0);
        assertNotNull(firstIssue.getId(), "Issue ID should not be null");
        assertNotNull(firstIssue.getTitle(), "Issue title should not be null");
        assertNotNull(firstIssue.getCreated_at(), "Issue creation date should not be null");

        // Check author
        User author = firstIssue.getAuthor();
        assertNotNull(author, "Author should not be null");
        assertNotNull(author.getUsername(), "Author username should not be null");

        // Check labels
        assertNotNull(firstIssue.getLabels(), "Labels should not be null");

        // Check assignee (optional)
        if (firstIssue.getAssignee() != null) {
            assertNotNull(firstIssue.getAssignee().getUsername(), "Assignee username should not be null");
        }

        // Check comments
        List<Comment> comments = firstIssue.getComments();
        if (comments != null && !comments.isEmpty()) {
            Comment firstComment = comments.get(0);
            assertNotNull(firstComment.getBody(), "Comment body should not be null");
            assertNotNull(firstComment.getCreated_at(), "Comment creation date should not be null");
            assertNotNull(firstComment.getAuthor(), "Comment author should not be null");
        }

        System.out.println("Fetched " + issues.size() + " issues from the repo.");
    }
}
