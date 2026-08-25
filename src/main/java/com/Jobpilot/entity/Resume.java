package com.Jobpilot.entity;

import jakarta.persistence.*;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String rawText;

    @Lob
    private String structuredJson;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getRawText() { return rawText; }
    public void setRawText(String rawText) { this.rawText = rawText; }
    public String getStructuredJson() { return structuredJson; }
    public void setStructuredJson(String structuredJson) { this.structuredJson = structuredJson; }
}