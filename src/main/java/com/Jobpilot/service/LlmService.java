package com.Jobpilot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LlmService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String extractStructuredResume(String rawText) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=" + apiKey;

        String prompt = """
            Extract the following fields from this resume text and return ONLY valid JSON,
            no explanation, no markdown formatting, no ```json fences, just the raw JSON object:
            {
              "name": "",
              "email": "",
              "phone": "",
              "skills": [],
              "education": [],
              "projects": [],
              "experience_years": ""
            }

            Resume text:
            """ + rawText;

        Map<String, Object> part = Map.of("text", prompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> body = Map.of("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        List<Map> candidates = (List<Map>) response.getBody().get("candidates");
        Map contentResponse = (Map) candidates.get(0).get("content");
        List<Map> parts = (List<Map>) contentResponse.get("parts");
        return (String) parts.get(0).get("text");
    }

    public String extractStructuredJobDescription(String rawText) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=" + apiKey;

        String prompt = """
        Extract the following fields from this job description and return ONLY valid JSON,
        no explanation, no markdown formatting, no ```json fences, just the raw JSON object:
        {
          "job_title": "",
          "required_skills": [],
          "preferred_skills": [],
          "experience_required": "",
          "responsibilities": [],
          "qualifications": []
        }

        Job description text:
        """ + rawText;

        Map<String, Object> part = Map.of("text", prompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> body = Map.of("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        List<Map> candidates = (List<Map>) response.getBody().get("candidates");
        Map contentResponse = (Map) candidates.get(0).get("content");
        List<Map> parts = (List<Map>) contentResponse.get("parts");
        return (String) parts.get(0).get("text");
    }
}
