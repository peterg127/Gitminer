package com.aiss.bitbucketminer.service;

import com.aiss.bitbucketminer.model.Commit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BitbucketCommitServiceTest {

    @Autowired
    BitbucketCommitService commitService;

    @Test
    @DisplayName("Fetch commits from a Bitbucket repository")
    void fetchCommits() {
        // Public Bitbucket repository
        String workspace = "gentlero";
        String repoSlug = "bitbucket-api";

        int nCommits = 5;
        int maxPages = 1;

        List<Commit> commits = commitService.fetchCommits(workspace, repoSlug, nCommits, maxPages);

        assertNotNull(commits, "Commits list should not be null");
        assertFalse(commits.isEmpty(), "Commits list should not be empty");
        assertEquals(5, commits.size(), "Should return exactly 5 commits");
        for (Commit commit : commits) {
            assertNotNull(commit.getId(), "Commit ID should not be null");
            assertNotNull(commit.getTitle(), "Commit title should not be null");
            assertNotNull(commit.getAuthor_name(), "Author name should not be null");
            assertNotNull(commit.getAuthor_email(), "Author email should not be null");
            assertNotNull(commit.getAuthored_date(), "Commit date should not be null");
        }

        System.out.println("Fetched " + commits.size() + " commits.");
    }
}
