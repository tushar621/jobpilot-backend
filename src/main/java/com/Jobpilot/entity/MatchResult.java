package com.Jobpilot.entity;

import jakarta.persistence.*;

@Entity
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Resume resume;

    @ManyToOne
    private JobDescription jobDescription;

    private Double matchScore;

    @Lob
    private String missingSkills;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Resume getResume() { return resume; }
    public void setResume(Resume resume) { this.resume = resume; }
    public JobDescription getJobDescription() { return jobDescription; }
    public void setJobDescription(JobDescription jobDescription) { this.jobDescription = jobDescription; }
    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
    public String getMissingSkills() { return missingSkills; }
    public void setMissingSkills(String missingSkills) { this.missingSkills = missingSkills; }
}
