package com.Jobpilot.Controller;

import com.Jobpilot.entity.JobDescription;
import com.Jobpilot.entity.MatchResult;
import com.Jobpilot.entity.Resume;
import com.Jobpilot.repository.JobDescriptionRepository;
import com.Jobpilot.repository.MatchResultRepository;
import com.Jobpilot.repository.ResumeRepository;
import com.Jobpilot.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchService matchService;

    @PostMapping("/{resumeId}/{jdId}")
    public MatchResult matchResumeToJob(@PathVariable Long resumeId, @PathVariable Long jdId) {
        Resume resume = resumeRepository.findById(resumeId).orElseThrow();
        JobDescription jd = jobDescriptionRepository.findById(jdId).orElseThrow();

        double score = matchService.calculateMatchScore(resume.getRawText(), jd.getRawText());

        MatchResult matchResult = new MatchResult();
        matchResult.setResume(resume);
        matchResult.setJobDescription(jd);
        matchResult.setMatchScore(score * 100);

        matchResultRepository.save(matchResult);

        return matchResult;
    }
}
