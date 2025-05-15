package com.skcc.ags.audit.service;

import com.skcc.ags.audit.domain.Project;
import com.skcc.ags.audit.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Optional<Project> getProjectByCode(String projectCode) {
        return projectRepository.findByProjectCode(projectCode);
    }

    @Transactional
    public void createProject(Project project) {
        projectRepository.save(project);
    }

    @Transactional
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.delete(id);
    }
} 