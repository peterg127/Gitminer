package com.aiss.bitbucketminer.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.aiss.bitbucketminer.model.Commit;
import java.util.List;

public class Project {
    private String id;
    private List<Commit> commits;
    private List<Issue> issues;


    private String name;

    @JsonProperty("web_url")
    private String web_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for web_url
    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }
    public List<Commit> getCommits() {
        return commits;
    }
    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
    public List<Issue> getIssues() {
        return issues;
    }
}
