package com.aiss.githubminer.model;

import java.util.List;

public class Issue {
    private String id;
    private String title;
    private String description;
    private String state;
    private String created_at;
    private String updated_at;
    private String closed_at;
    private List<Comment> comments;
    private List<String> labels;
    private User author;  // Add the reporter as a User object
    private User assignee;
    private Integer votes;

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for state
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    // Getter and Setter for closedAt
    public String getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(String closedAt) {
        this.closed_at = closedAt;
    }

    // Getter and Setter for votes
    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> kind) {
        this.labels = kind;
    }
}
