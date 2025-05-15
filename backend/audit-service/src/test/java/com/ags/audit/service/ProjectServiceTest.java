package com.skcc.ags.audit.service;

import com.skcc.ags.audit.domain.Project;
import com.skcc.ags.audit.repository.ProjectRepository;
import com.skcc.ags.audit.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project testProject;

    @BeforeEach
    void setUp() {
        testProject = Project.builder()
                .id(1L)
                .name("Test Project")
                .description("Test Description")
                .createdAt(null)
                .build();
    }

    @Test
    void getAllProjects_ShouldReturnListOfProjects() {
        // Given
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectRepository.findAll()).thenReturn(expectedProjects);

        // When
        List<Project> actualProjects = projectService.getAllProjects();

        // Then
        assertThat(actualProjects).isEqualTo(expectedProjects);
        verify(projectRepository).findAll();
    }

    @Test
    void getProjectById_WhenProjectExists_ShouldReturnProject() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        // When
        Optional<Project> actualProject = projectService.getProjectById(1L);

        // Then
        assertThat(actualProject).isPresent();
        assertThat(actualProject.get()).isEqualTo(testProject);
        verify(projectRepository).findById(1L);
    }

    @Test
    void getProjectById_WhenProjectDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Project> actualProject = projectService.getProjectById(1L);

        // Then
        assertThat(actualProject).isEmpty();
        verify(projectRepository).findById(1L);
    }

    @Test
    void getProjectByCode_WhenProjectExists_ShouldReturnProject() {
        // Given
        when(projectRepository.findByProjectCode("TEST001")).thenReturn(Optional.of(testProject));

        // When
        Optional<Project> actualProject = projectService.getProjectByCode("TEST001");

        // Then
        assertThat(actualProject).isPresent();
        assertThat(actualProject.get()).isEqualTo(testProject);
        verify(projectRepository).findByProjectCode("TEST001");
    }

    @Test
    void createProject_ShouldCallRepositorySave() {
        // When
        projectService.createProject(testProject);

        // Then
        verify(projectRepository).save(testProject);
    }

    @Test
    void updateProject_ShouldCallRepositoryUpdate() {
        // When
        projectService.updateProject(testProject);

        // Then
        verify(projectRepository).update(testProject);
    }

    @Test
    void deleteProject_ShouldCallRepositoryDelete() {
        // When
        projectService.deleteProject(1L);

        // Then
        verify(projectRepository).delete(1L);
    }
} 