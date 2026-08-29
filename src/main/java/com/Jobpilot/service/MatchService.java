package com.Jobpilot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    private LlmService llmService;

    public double calculateMatchScore(String resumeText, String jobDescriptionText) {
        List<Double> resumeVector = llmService.getEmbedding(resumeText);
        List<Double> jdVector = llmService.getEmbedding(jobDescriptionText);

        return cosineSimilarity(resumeVector, jdVector);
    }

    private double cosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}