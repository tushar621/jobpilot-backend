package com.Jobpilot.Controller;

import com.Jobpilot.entity.JobDescription;
import com.Jobpilot.repository.JobDescriptionRepository;
import com.Jobpilot.service.LlmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/jobdescription")
public class JobDescriptionController {

    @Autowired
    private JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    private LlmService llmService;

    @PostMapping("/add")
    public JobDescription addJobDescription(@RequestBody Map<String, String> requestBody) {
        String rawText = requestBody.get("rawText");

        JobDescription jd = new JobDescription();
        jd.setRawText(rawText);
        jobDescriptionRepository.save(jd);

        return jd;
    }

    @PostMapping("/parse/{id}")
    public String parseJobDescription(@PathVariable Long id) {
        JobDescription jd = jobDescriptionRepository.findById(id).orElseThrow();
        String structuredData = llmService.extractStructuredJobDescription(jd.getRawText());
        jd.setStructuredJson(structuredData);
        jobDescriptionRepository.save(jd);
        return structuredData;
    }
}