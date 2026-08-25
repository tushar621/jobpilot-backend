package com.Jobpilot.repository;

import com.Jobpilot.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {}
