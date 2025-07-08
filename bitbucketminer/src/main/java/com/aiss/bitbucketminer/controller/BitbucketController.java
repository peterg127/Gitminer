package com.aiss.bitbucketminer.controller;

import com.aiss.bitbucketminer.model.Project;
import com.aiss.bitbucketminer.service.BitbucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/bitbucket")
public class BitbucketController {

    @Autowired
    private BitbucketService BitbucketProjectService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gitminer.url}")
    private String gitminerUrl;

    /**
     * Method to get the full project details from Bitbucket and send it to GitMiner
     */
    @PostMapping("/{owner}/{repoName}")
    public ResponseEntity<Project> getFullProject(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(name = "nCommits", defaultValue = "5") int nCommits,
            @RequestParam(name = "nIssues", defaultValue = "5") int nIssues,
            @RequestParam(name = "maxPages", defaultValue = "2") int maxPages
    ) {
        Project project = BitbucketProjectService.getFullProject(owner, repoName, nCommits, nIssues, maxPages);

        // Send the project to GitMiner
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(gitminerUrl, project, String.class);
            System.out.println("GitMiner response: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Failed to send project to GitMiner: " + e.getMessage());
        }

        return ResponseEntity.ok(project);
    }

    /**
     * Method to get the project details from Bitbucket without sending it to GitMiner.
     */
    @GetMapping("/{owner}/{repoName}")
    public ResponseEntity<Project> getProjectReadOnly(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(name = "nCommits", defaultValue = "5") int nCommits,
            @RequestParam(name = "nIssues", defaultValue = "5") int nIssues,
            @RequestParam(name = "maxPages", defaultValue = "2") int maxPages
    ) {
        // Get the project details from Bitbucket without sending it to GitMiner
        Project project = BitbucketProjectService.getFullProject(owner, repoName, nCommits, nIssues, maxPages);

        // Return the project data as response, without sending it to GitMiner
        return ResponseEntity.ok(project);
    }
}
