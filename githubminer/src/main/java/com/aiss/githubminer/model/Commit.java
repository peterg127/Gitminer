package com.aiss.githubminer.model;

public class Commit {
    private String id;
    private String title;
    private String message;
    private String author_name;
    private String author_email;
    private String authored_date;
    private String web_url;

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

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for authorName
    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String authorName) {
        this.author_name = authorName;
    }

    // Getter and Setter for authorEmail
    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String authorEmail) {
        this.author_email = authorEmail;
    }

    // Getter and Setter for authoredDate
    public String getAuthored_date() {
        return authored_date;
    }

    public void setAuthored_date(String authoredDate) {
        this.authored_date = authoredDate;
    }

    // Getter and Setter for webUrl
    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String webUrl) {
        this.web_url = webUrl;
    }
    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authorName='" + author_name + '\'' +
                ", authoredDate='" + authored_date + '\'' +
                ", webUrl='" + web_url + '\'' +
                '}';
    }
}
