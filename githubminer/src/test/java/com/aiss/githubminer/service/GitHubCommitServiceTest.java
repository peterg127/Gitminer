package com.aiss.githubminer.service;

import com.aiss.githubminer.model.Commit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitHubCommitServiceTest {

    @Autowired
    GitHubCommitService service;

    @Test
    @DisplayName("Fetch real commits from GitHub with detailed assertions")
    void fetchCommitsFromRealRepo() {
        String owner = "spring-projects";
        String repo = "spring-framework";
        int sinceDays = 30;
        int maxPages = 1;

        List<Commit> commits = service.fetchCommits(owner, repo, sinceDays, maxPages);

        assertNotNull(commits, "Commit list is null");
        assertFalse(commits.isEmpty(), "No commits were fetched from the repository");

        for (Commit commit : commits) {
            assertNotNull(commit.getId(), "Commit ID should not be null");
            assertFalse(commit.getId().isBlank(), "Commit ID should not be blank");

            assertNotNull(commit.getTitle(), "Commit title should not be null");
            assertFalse(commit.getTitle().isBlank(), "Commit title should not be blank");

            assertNotNull(commit.getAuthor_name(), "Author name should not be null");
            assertFalse(commit.getAuthor_name().isBlank(), "Author name should not be blank");

            assertNotNull(commit.getAuthor_email(), "Author email should not be null");

            assertNotNull(commit.getAuthored_date(), "Authored date should not be null");

            assertNotNull(commit.getWeb_url(), "Web URL should not be null");
            assertTrue(commit.getWeb_url().startsWith("https://"), "Web URL should be a valid HTTPS link");
        }

        System.out.println("Number of commits: " + commits.size());
    }
}