package com.aiss.bitbucketminer.service;

import com.aiss.bitbucketminer.model.Commit;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BitbucketCommitService {

    private final BitbucketApiService apiService;

    public BitbucketCommitService(BitbucketApiService apiService) {
        this.apiService = apiService;
    }

    public List<Commit> fetchCommits(String workspace, String repoSlug, int nCommits, int maxPages) {
        String url = BitbucketApiService.BASE_URL + "/repositories/" + workspace + "/" + repoSlug + "/commits";
        String nextPageUrl = url + "?pagelen=" + nCommits;
        List<Commit> commits = new ArrayList<>();

        for (int pageCount = 0; nextPageUrl != null && pageCount < maxPages; pageCount++) {
            Map<String, Object> responseBody = apiService.getResponseBody(nextPageUrl);
            List<Map<String, Object>> values = (List<Map<String, Object>>) responseBody.get("values");
            for (Map<String, Object> json : values) {
                commits.add(parseCommit(json));
            }
            nextPageUrl = (String) responseBody.get("next");
        }

        return commits;
    }

    private Commit parseCommit(Map<String, Object> json) {
        Commit commit = new Commit();
        commit.setId((String) json.get("hash"));

        String message = (String) json.get("message");
        String[] lines = message.split("\n", 2);
        commit.setTitle(lines[0]);
        commit.setMessage(lines.length > 1 ? lines[1].strip() : "");

        Map<String, Object> author = (Map<String, Object>) json.get("author");
        commit.setAuthor_name(author.containsKey("user") ?
                (String) ((Map<String, Object>) author.get("user")).get("display_name") : "Unknown");
        String rawAuthor = (String) author.get("raw");
        commit.setAuthor_email(rawAuthor.substring(rawAuthor.indexOf("<") + 1, rawAuthor.indexOf(">")));
        commit.setAuthored_date((String) json.get("date"));

        Map<String, Object> links = (Map<String, Object>) json.get("links");
        Map<String, Object> html = (Map<String, Object>) links.get("html");
        commit.setWeb_url((String) html.get("href"));

        return commit;
    }
}
