package com.aiss.bitbucketminer.model;

public class User {
    private String id;
    private String username;
    private String name;
    private String avatar_url;
    private String web_url;

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for avatarUrl
    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatarUrl) {
        this.avatar_url = avatarUrl;
    }

    // Getter and Setter for webUrl
    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String webUrl) {
        this.web_url = webUrl;
    }
}
