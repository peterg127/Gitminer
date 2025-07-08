package com.aiss.githubminer.controller;

import com.aiss.githubminer.model.Project;
import com.aiss.githubminer.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    private GitHubService GitHubService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gitminer.url}")
    private String gitminerUrl;
    @PostMapping("/{owner}/{repoName}")
    public ResponseEntity<Project> getFullProject(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(name = "sinceCommits", defaultValue = "2") int sinceCommits,
            @RequestParam(name = "sinceIssues", defaultValue = "20") int sinceIssues,
            @RequestParam(name = "maxPages", defaultValue = "2") int maxPages
    ) {
        Project project = GitHubService.getFullProject(owner, repoName, sinceCommits, sinceIssues, maxPages);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(gitminerUrl, project, String.class);
            System.out.println("GitMiner response: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Failed to send project to GitMiner: " + e.getMessage());
        }

        return ResponseEntity.ok(project);
    }

    @GetMapping("/{owner}/{repoName}")
    public ResponseEntity<Project> getProjectReadOnly(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(name = "sinceCommits", defaultValue = "2") int sinceCommits,
            @RequestParam(name = "sinceIssues", defaultValue = "20") int sinceIssues,
            @RequestParam(name = "maxPages", defaultValue = "2") int maxPages
    ) {
        Project project = GitHubService.getFullProject(owner, repoName, sinceCommits, sinceIssues, maxPages);
        return ResponseEntity.ok(project);
    }
}
