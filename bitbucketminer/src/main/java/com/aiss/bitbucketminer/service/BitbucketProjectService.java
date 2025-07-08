package com.aiss.bitbucketminer.service;

import com.aiss.bitbucketminer.model.Project;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BitbucketProjectService {

    private final BitbucketApiService apiService;

    public BitbucketProjectService(BitbucketApiService apiService) {
        this.apiService = apiService;
    }

    public Project fetchProjectInfo(String workspace, String repoSlug) {
        String url = BitbucketApiService.BASE_URL + "/repositories/" + workspace + "/" + repoSlug;
        Map<String, Object> body = apiService.getResponseBody(url);

        Project project = new Project();
        project.setId((String) body.get("uuid"));
        project.setName((String) body.get("name"));
        project.setWeb_url((String) body.get("website"));
        return project;
    }
}
