package com.aiss.githubminer.service;

import com.aiss.githubminer.model.Project;
import org.springframework.stereotype.Service;

@Service
public class GitHubService {
    private final GitHubProjectService projectService;
    private final GitHubCommitService commitService;
    private final GitHubIssueService issueService;

    public GitHubService(GitHubProjectService projectService, GitHubCommitService commitService, GitHubIssueService issueService) {
        this.projectService = projectService;
        this.commitService = commitService;
        this.issueService = issueService;
    }

    public Project getFullProject(String owner, String repo, int sinceCommits, int sinceIssues, int maxPages) {
        Project project = projectService.fetchProject(owner, repo);
        project.setCommits(commitService.fetchCommits(owner, repo, sinceCommits, maxPages));
        project.setIssues(issueService.fetchIssues(owner, repo, sinceIssues, 30, maxPages));
        return project;
    }
}