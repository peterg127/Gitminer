package com.aiss.githubminer.service;

import com.aiss.githubminer.model.Commit;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class GitHubCommitService {
    private final GitHubApiClient client;

    public GitHubCommitService(GitHubApiClient client) {
        this.client = client;
    }

    public List<Commit> fetchCommits(String owner, String repo, int sinceDays, int maxPages) {
        List<Commit> commits = new ArrayList<>();
        String sinceDate = Instant.now().minus(sinceDays, ChronoUnit.DAYS).toString();

        for (int page = 1; page <= maxPages; page++) {
            String url = "/repos/" + owner + "/" + repo + "/commits?since=" + sinceDate + "&per_page=30&page=" + page;
            List<Map<String, Object>> values = client.getList(url);
            for (Map<String, Object> json : values) {
                commits.add(parseCommit(json));
            }
            if (values.size() < 30) break;
        }
        return commits;
    }

    private Commit parseCommit(Map<String, Object> json) {
        Commit commit = new Commit();
        commit.setId((String) json.get("sha"));
        Map<String, Object> commitData = (Map<String, Object>) json.get("commit");
        String message = (String) commitData.get("message");
        String[] lines = message.split("\n", 2);
        commit.setTitle(lines[0]);
        commit.setMessage(lines.length > 1 ? lines[1].strip() : "");
        Map<String, Object> authorData = (Map<String, Object>) commitData.get("author");
        commit.setAuthor_name((String) authorData.get("name"));
        commit.setAuthor_email((String) authorData.get("email"));
        commit.setAuthored_date((String) authorData.get("date"));
        commit.setWeb_url((String) json.get("html_url"));
        return commit;
    }
}
