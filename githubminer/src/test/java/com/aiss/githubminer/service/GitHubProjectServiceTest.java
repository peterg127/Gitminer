package com.aiss.githubminer.service;

import com.aiss.githubminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitHubProjectServiceTest {

    @Autowired
    GitHubProjectService service;

    @Test
    @DisplayName("Fetch GitHub project details")
    void fetchProject() {
        String owner = "spring-projects";
        String repo = "spring-framework";

        Project project = service.fetchProject(owner, repo);

        assertNotNull(project, "Project should not be null");
        assertNotNull(project.getId(), "Project ID should not be null");
        assertEquals("spring-framework", project.getName(), "Project name should match the repository");
        assertTrue(project.getWeb_url().contains("github.com"), "Project URL should contain github.com");

        System.out.println("Good");
    }
}
