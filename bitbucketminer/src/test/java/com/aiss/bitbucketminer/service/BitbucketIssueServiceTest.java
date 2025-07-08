package com.aiss.bitbucketminer.service;

import com.aiss.bitbucketminer.model.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BitbucketIssueServiceTest {

    @Autowired
    private BitbucketIssueService issueService;

    @Test
    @DisplayName("Fetch issues from a Bitbucket repository")
    void fetchIssues() {
        // Use a Bitbucket repository that has public issues
        String workspace = "gentlero";
        String repoSlug = "bitbucket-api"; // Known to have public issues

        int nIssues = 5;
        int maxPages = 1;

        List<Issue> issues = issueService.fetchIssues(workspace, repoSlug, nIssues, maxPages);

        assertNotNull(issues, "Issues list should not be null");
        assertFalse(issues.isEmpty(), "Issues list should not be empty");
        assertEquals(5, issues.size(), "Should return exactly 5 issues");

        for (Issue issue : issues) {
            assertNotNull(issue.getId(), "Issue ID should not be null");
            assertNotNull(issue.getTitle(), "Issue title should not be null");
            assertNotNull(issue.getState(), "Issue state should not be null");
            assertNotNull(issue.getCreated_at(), "Issue creation date should not be null");
            assertNotNull(issue.getAuthor(), "Issue author should not be null");
        }

        System.out.println("Fetched " + issues.size() + " issues.");
    }
}
