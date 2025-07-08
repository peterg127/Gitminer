package com.aiss.githubminer.service;

import com.aiss.githubminer.model.Project;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GitHubProjectService {
    private final GitHubApiClient client;

    public GitHubProjectService(GitHubApiClient client) {
        this.client = client;
    }

    public Project fetchProject(String owner, String repo) {
        Map<String, Object> json = client.get("/repos/" + owner + "/" + repo);
        Project project = new Project();
        project.setId(json.get("id").toString());
        project.setName((String) json.get("name"));
        project.setWeb_url((String) json.get("html_url"));
        return project;
    }
}
