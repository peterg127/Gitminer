package com.aiss.bitbucketminer.model;

public class Comment {
    private String id;
    private String body;
    private String created_at;
    private String updated_at;
    private User author;  // Use the User class for author instead of String

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for body
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // Getter and Setter for createdAt
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String createdAt) {
        this.created_at = createdAt;
    }

    // Getter and Setter for updatedAt
    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updatedAt) {
        this.updated_at = updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}