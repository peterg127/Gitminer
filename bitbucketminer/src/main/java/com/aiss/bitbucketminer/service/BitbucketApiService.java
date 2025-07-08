package com.aiss.bitbucketminer.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class BitbucketApiService {

    public static final String BASE_URL = "https://api.bitbucket.org/2.0";
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getResponseBody(String url) {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}
