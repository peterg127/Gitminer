package com.aiss.bitbucketminer.service;

import com.aiss.bitbucketminer.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BitbucketIssueService {

    private final BitbucketApiService apiService;

    public BitbucketIssueService(BitbucketApiService apiService) {
        this.apiService = apiService;
    }

    public List<Issue> fetchIssues(String workspace, String repoSlug, int nIssues, int maxPages) {
        String url = BitbucketApiService.BASE_URL + "/repositories/" + workspace + "/" + repoSlug + "/issues";
        String nextPageUrl = url + "?pagelen=" + nIssues;
        List<Issue> issues = new ArrayList<>();

        for (int pageCount = 0; nextPageUrl != null && pageCount < maxPages; pageCount++) {
            Map<String, Object> body = apiService.getResponseBody(nextPageUrl);
            List<Map<String, Object>> values = (List<Map<String, Object>>) body.get("values");
            for (Map<String, Object> json : values) {
                issues.add(parseIssue(json));
            }
            nextPageUrl = (String) body.get("next");
        }

        return issues;
    }

    private Issue parseIssue(Map<String, Object> json) {
        Issue issue = new Issue();
        issue.setId(json.get("id").toString());
        issue.setTitle((String) json.get("title"));

        Map<String, Object> content = (Map<String, Object>) json.get("content");
        issue.setDescription(content != null ? (String) content.get("raw") : null);

        List<String> kinds = new ArrayList<>();
        if (json.get("kind") instanceof String) {
            kinds.add((String) json.get("kind"));
        } else if (json.get("kind") instanceof List) {
            kinds = (List<String>) json.get("kind");
        }
        issue.setLabels(kinds);

        issue.setState((String) json.get("state"));
        issue.setCreated_at((String) json.get("created_on"));
        issue.setUpdated_at((String) json.get("updated_on"));
        issue.setClosed_at((String) json.get("closed_on"));
        issue.setVotes((Integer) json.get("votes"));
        issue.setAuthor(parseUser((Map<String, Object>) json.get("reporter")));

        if (json.get("assignee") != null) {
            issue.setAssignee(parseUser((Map<String, Object>) json.get("assignee")));
        }

        Map<String, Object> links = (Map<String, Object>) json.get("links");
        if (links != null && links.containsKey("comments")) {
            String commentsUrl = (String) ((Map<String, Object>) links.get("comments")).get("href");
            issue.setComments(fetchComments(commentsUrl));
        }

        return issue;
    }

    private List<Comment> fetchComments(String url) {
        List<Comment> comments = new ArrayList<>();
        String next = url;

        while (next != null) {
            Map<String, Object> body = apiService.getResponseBody(next);
            List<Map<String, Object>> values = (List<Map<String, Object>>) body.get("values");
            for (Map<String, Object> json : values) {
                comments.add(parseComment(json));
            }
            next = (String) body.get("next");
        }

        return comments;
    }

    private Comment parseComment(Map<String, Object> json) {
        Comment comment = new Comment();
        comment.setId(json.get("id").toString());

        Map<String, Object> content = (Map<String, Object>) json.get("content");
        comment.setBody(content != null && content.get("raw") != null ? (String) content.get("raw") : "-");
        comment.setCreated_at((String) json.get("created_on"));
        comment.setAuthor(parseUser((Map<String, Object>) json.get("user")));

        return comment;
    }

    private User parseUser(Map<String, Object> userMap) {
        User user = new User();
        user.setId((String) userMap.get("uuid"));
        user.setUsername((String) userMap.get("nickname"));
        user.setName((String) userMap.get("display_name"));

        Map<String, Object> links = (Map<String, Object>) userMap.get("links");
        user.setAvatar_url(((Map<String, Object>) links.get("avatar")).get("href").toString());
        user.setWeb_url(((Map<String, Object>) links.get("html")).get("href").toString());

        return user;
    }
}
