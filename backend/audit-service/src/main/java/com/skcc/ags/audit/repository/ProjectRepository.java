package com.skcc.ags.audit.repository;

import com.skcc.ags.audit.domain.Project;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ProjectRepository {
    List<Project> findAll();
    Optional<Project> findById(Long id);
    Optional<Project> findByProjectCode(String projectCode);
    void save(Project project);
    void update(Project project);
    void delete(Long id);
} 