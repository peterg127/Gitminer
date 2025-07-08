package com.aiss.bitbucketminer.service;

import com.aiss.bitbucketminer.model.*;
import org.springframework.stereotype.Service;

@Service
public class BitbucketService {

    private final BitbucketProjectService repositoryService;
    private final BitbucketCommitService commitService;
    private final BitbucketIssueService issueService;

    public BitbucketService(BitbucketProjectService repositoryService,
                            BitbucketCommitService commitService,
                            BitbucketIssueService issueService) {
        this.repositoryService = repositoryService;
        this.commitService = commitService;
        this.issueService = issueService;
    }

    public Project getFullProject(String workspace, String repoSlug, int nCommits, int nIssues, int maxPages) {
        Project project = repositoryService.fetchProjectInfo(workspace, repoSlug);
        project.setCommits(commitService.fetchCommits(workspace, repoSlug, nCommits, maxPages));
        project.setIssues(issueService.fetchIssues(workspace, repoSlug, nIssues, maxPages));
        return project;
    }
}
