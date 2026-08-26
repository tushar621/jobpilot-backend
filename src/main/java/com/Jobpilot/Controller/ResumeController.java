package com.Jobpilot.Controller;

import com.Jobpilot.entity.Resume;
import com.Jobpilot.repository.ResumeRepository;
import com.Jobpilot.service.ResumeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeParserService resumeParserService;

    @Autowired
    private ResumeRepository resumeRepository;

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            String extractedText = resumeParserService.extractText(file);

            Resume resume = new Resume();
            resume.setRawText(extractedText);
            resumeRepository.save(resume);

            return "Resume uploaded successfully. Extracted " + extractedText.length() + " characters.";
        } catch (Exception e) {
            return "Error processing resume: " + e.getMessage();
        }
    }
}