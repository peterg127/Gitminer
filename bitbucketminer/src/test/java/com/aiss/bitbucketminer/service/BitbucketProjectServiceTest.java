package com.aiss.bitbucketminer.service;

import com.aiss.bitbucketminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BitbucketProjectServiceTest {

    @Autowired
    BitbucketProjectService service;

    @Test
    @DisplayName("Fetch Bitbucket project info")
    void fetchProjectInfo() {
        // Replace with a valid public repo if needed
        String workspace = "gentlero";
        String repoSlug = "bitbucket-api";

        Project project = service.fetchProjectInfo(workspace, repoSlug);

        assertNotNull(project, "Project should not be null");
        assertNotNull(project.getId(), "Project ID should not be null");
        assertNotNull(project.getName(), "Project name should not be null");
        assertTrue(project.getWeb_url() == null || project.getWeb_url().startsWith("http"), "Web URL should be a valid URL or null");

        System.out.println("Good");
    }
}
