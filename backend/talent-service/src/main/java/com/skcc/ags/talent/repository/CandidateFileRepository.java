package com.ags.talent.repository;

import com.ags.talent.entity.CandidateFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateFileRepository extends JpaRepository<CandidateFile, Long> {
} 