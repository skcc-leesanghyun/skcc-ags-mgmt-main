package com.skcc.ags.audit.service;

import com.skcc.ags.audit.domain.Personnel;
import com.skcc.ags.audit.repository.PersonnelRepository;
import com.skcc.ags.audit.service.PersonnelService;
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
class PersonnelServiceTest {

    @Mock
    private PersonnelRepository personnelRepository;

    @InjectMocks
    private PersonnelService personnelService;

    private Personnel testPersonnel;

    @BeforeEach
    void setUp() {
        testPersonnel = Personnel.builder()
                .id(1L)
                .projectId(1L)
                .employeeId("EMP001")
                .name("John Doe")
                .role("Developer")
                .department("IT")
                .company("AGS")
                .startDate("2024-03-20")
                .endDate("2024-12-31")
                .pledgeStatus("Submitted")
                .pledgeSubmissionDate(java.time.LocalDate.parse("2024-03-20"))
                .pledgeFilePath("/pledges/EMP001.pdf")
                .createdBy("admin")
                .createdAt(null)
                .updatedBy(null)
                .updatedAt(null)
                .build();
    }

    @Test
    void getAllPersonnel_ShouldReturnListOfPersonnel() {
        // Given
        List<Personnel> expectedPersonnel = Arrays.asList(testPersonnel);
        when(personnelRepository.findAll()).thenReturn(expectedPersonnel);

        // When
        List<Personnel> actualPersonnel = personnelService.getAllPersonnel();

        // Then
        assertThat(actualPersonnel).isEqualTo(expectedPersonnel);
        verify(personnelRepository).findAll();
    }

    @Test
    void getPersonnelByProjectId_ShouldReturnListOfPersonnel() {
        // Given
        List<Personnel> expectedPersonnel = Arrays.asList(testPersonnel);
        when(personnelRepository.findByProjectId(1L)).thenReturn(expectedPersonnel);

        // When
        List<Personnel> actualPersonnel = personnelService.getPersonnelByProjectId(1L);

        // Then
        assertThat(actualPersonnel).isEqualTo(expectedPersonnel);
        verify(personnelRepository).findByProjectId(1L);
    }

    @Test
    void getPersonnelById_WhenPersonnelExists_ShouldReturnPersonnel() {
        // Given
        when(personnelRepository.findById(1L)).thenReturn(Optional.of(testPersonnel));

        // When
        Optional<Personnel> actualPersonnel = personnelService.getPersonnelById(1L);

        // Then
        assertThat(actualPersonnel).isPresent();
        assertThat(actualPersonnel.get()).isEqualTo(testPersonnel);
        verify(personnelRepository).findById(1L);
    }

    @Test
    void getPersonnelById_WhenPersonnelDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(personnelRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Personnel> actualPersonnel = personnelService.getPersonnelById(1L);

        // Then
        assertThat(actualPersonnel).isEmpty();
        verify(personnelRepository).findById(1L);
    }

    @Test
    void getPersonnelByEmployeeId_WhenPersonnelExists_ShouldReturnPersonnel() {
        // Given
        when(personnelRepository.findByEmployeeId("EMP001")).thenReturn(Optional.of(testPersonnel));

        // When
        Optional<Personnel> actualPersonnel = personnelService.getPersonnelByEmployeeId("EMP001");

        // Then
        assertThat(actualPersonnel).isPresent();
        assertThat(actualPersonnel.get()).isEqualTo(testPersonnel);
        verify(personnelRepository).findByEmployeeId("EMP001");
    }

    @Test
    void createPersonnel_ShouldCallRepositorySave() {
        // When
        personnelService.createPersonnel(testPersonnel);

        // Then
        verify(personnelRepository).save(testPersonnel);
    }

    @Test
    void updatePersonnel_ShouldCallRepositoryUpdate() {
        // When
        personnelService.updatePersonnel(testPersonnel);

        // Then
        verify(personnelRepository).update(testPersonnel);
    }

    @Test
    void deletePersonnel_ShouldCallRepositoryDelete() {
        // When
        personnelService.deletePersonnel(1L);

        // Then
        verify(personnelRepository).delete(1L);
    }
} 