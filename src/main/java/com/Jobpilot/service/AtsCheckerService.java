package com.Jobpilot.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtsCheckerService {

    public List<String> checkResume(String rawText) {
        List<String> issues = new ArrayList<>();

        if (rawText == null || rawText.length() < 200) {
            issues.add("Resume text is very short — this may indicate the PDF is image-based and not machine-readable by ATS systems.");
        }

        if (!rawText.toLowerCase().contains("@")) {
            issues.add("No email address detected. Most ATS systems require a valid email to process applications.");
        }

        if (!rawText.matches(".*\\d{10}.*") && !rawText.matches(".*\\d{3}[-.\\s]\\d{3}[-.\\s]\\d{4}.*")) {
            issues.add("No phone number detected in a standard format.");
        }

        if (!rawText.toLowerCase().contains("experience") && !rawText.toLowerCase().contains("project")) {
            issues.add("No 'Experience' or 'Projects' section detected — ATS systems often look for these section headers explicitly.");
        }

        if (!rawText.toLowerCase().contains("skill")) {
            issues.add("No 'Skills' section detected — this is one of the most commonly parsed sections by ATS systems.");
        }

        boolean hasNumbers = rawText.matches(".*\\d+.*");
        if (!hasNumbers) {
            issues.add("No quantifiable metrics (numbers, percentages) found — resumes with measurable achievements rank higher in most ATS scoring systems.");
        }

        if (issues.isEmpty()) {
            issues.add("No major ATS issues detected. Resume appears well-structured for automated parsing.");
        }

        return issues;
    }
}