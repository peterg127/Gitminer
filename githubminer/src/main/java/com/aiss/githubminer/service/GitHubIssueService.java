package com.aiss.githubminer.service;

import com.aiss.githubminer.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GitHubIssueService {
    private final GitHubApiClient client;

    public GitHubIssueService(GitHubApiClient client) {
        this.client = client;
    }

    public List<Issue> fetchIssues(String owner, String repo, int daysAgo, int perPage, int maxPages) {
        String since = LocalDateTime.now().minusDays(daysAgo).format(DateTimeFormatter.ISO_DATE_TIME);
        List<Issue> issues = new ArrayList<>();
        for (int page = 1; page <= maxPages; page++) {
            String url = String.format("/repos/%s/%s/issues?since=%s&state=all&per_page=%d&page=%d",
                    owner, repo, since, perPage, page);
            List<Map<String, Object>> response = client.getList(url);
            if (response == null || response.isEmpty()) break;
            for (Map<String, Object> json : response) {
                issues.add(parseIssue(json));
            }
        }
        return issues;
    }

    private Issue parseIssue(Map<String, Object> json) {
        Issue issue = new Issue();
        issue.setId(json.get("id").toString());
        issue.setTitle((String) json.get("title"));
        issue.setDescription((String) json.get("body"));
        issue.setState((String) json.get("state"));
        issue.setCreated_at((String) json.get("created_at"));
        issue.setUpdated_at((String) json.get("updated_at"));
        issue.setClosed_at((String) json.get("closed_at"));

        Map<String, Object> user = (Map<String, Object>) json.get("user");
        if (user != null) {
            User author = new User();
            String login = (String) user.get("login");
            author.setUsername(login);
            author.setWeb_url((String) user.get("html_url"));
            author.setAvatar_url((String) user.get("avatar_url"));
            author.setId(user.get("id").toString());
            Map<String, Object> userDetails = client.get("/users/" + login);
            author.setName((String) userDetails.get("name"));
            issue.setAuthor(author);
        }

        List<Map<String, Object>> labelList = (List<Map<String, Object>>) json.get("labels");
        if (labelList != null) {
            List<String> labelIds = new ArrayList<>();
            for (Map<String, Object> labelJson : labelList) {
                labelIds.add(labelJson.get("id").toString());
            }
            issue.setLabels(labelIds);
        }

        Map<String, Object> reactions = (Map<String, Object>) json.get("reactions");
        if (reactions != null && reactions.get("total_count") != null) {
            issue.setVotes(((Number) reactions.get("total_count")).intValue());
        }

        Map<String, Object> assigneeJson = (Map<String, Object>) json.get("assignee");
        if (assigneeJson != null) {
            User assignee = new User();
            assignee.setId(json.get("id").toString());
            assignee.setUsername((String) assigneeJson.get("login"));
            assignee.setWeb_url((String) assigneeJson.get("html_url"));
            assignee.setAvatar_url((String) assigneeJson.get("avatar_url"));
            issue.setAssignee(assignee);
        }

        String commentsUrl = (String) json.get("comments_url");
        if (commentsUrl != null) {
            issue.setComments(fetchComments(commentsUrl));
        }

        return issue;
    }

    private List<Comment> fetchComments(String commentsUrl) {
        List<Map<String, Object>> response = client.getList(commentsUrl.replace("https://api.github.com", ""));
        List<Comment> comments = new ArrayList<>();

        if (response != null) {
            for (Map<String, Object> json : response) {
                Comment comment = new Comment();
                comment.setId(json.get("id").toString());
                comment.setBody((String) json.get("body"));
                comment.setCreated_at((String) json.get("created_at"));
                comment.setUpdated_at((String) json.get("updated_at"));
                Map<String, Object> userJson = (Map<String, Object>) json.get("user");
                if (userJson != null) {
                    User author = new User();
                    String login = (String) userJson.get("login");
                    author.setUsername(login);
                    author.setWeb_url((String) userJson.get("html_url"));
                    author.setAvatar_url((String) userJson.get("avatar_url"));
                    author.setId(userJson.get("id").toString());
                    comment.setAuthor(author);
                    Map<String, Object> userDetails = client.get("/users/" + login);
                    author.setName((String) userDetails.get("name"));
                }
                comments.add(comment);
            }
        }
        return comments;
    }
}