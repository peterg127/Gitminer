package com.aiss.githubminer.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GitHubApiClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://api.github.com";
    private final String TOKEN = "YOUR_GITHUB_TOKEN"; // Move to properties

    private HttpEntity<Void> createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        return new HttpEntity<>(headers);
    }

    public Map<String, Object> get(String path) {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                BASE_URL + path, HttpMethod.GET, createHeaders(), new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    public List<Map<String, Object>> getList(String path) {
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                BASE_URL + path, HttpMethod.GET, createHeaders(), new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }
}
