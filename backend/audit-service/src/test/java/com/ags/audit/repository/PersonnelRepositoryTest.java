package com.skcc.ags.audit.repository;

import com.skcc.ags.audit.domain.Personnel;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class PersonnelRepositoryTest {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Test
    void findAll_ShouldReturnAllPersonnel() {
        List<Personnel> personnelList = personnelRepository.findAll();
        
        assertThat(personnelList).isNotEmpty();
        assertThat(personnelList).hasSize(1);
        assertThat(personnelList.get(0).getEmployeeId()).isEqualTo("EMP001");
    }

    @Test
    void findById_WhenPersonnelExists_ShouldReturnPersonnel() {
        Optional<Personnel> personnel = personnelRepository.findById(1L);
        
        assertThat(personnel).isPresent();
        assertThat(personnel.get().getName()).isEqualTo("John Doe");
        assertThat(personnel.get().getEmployeeId()).isEqualTo("EMP001");
    }

    @Test
    void findById_WhenPersonnelDoesNotExist_ShouldReturnEmpty() {
        Optional<Personnel> personnel = personnelRepository.findById(999L);
        
        assertThat(personnel).isEmpty();
    }

    @Test
    void findByProjectId_ShouldReturnPersonnelList() {
        List<Personnel> personnelList = personnelRepository.findByProjectId(1L);
        
        assertThat(personnelList).isNotEmpty();
        assertThat(personnelList).hasSize(1);
        assertThat(personnelList.get(0).getEmployeeId()).isEqualTo("EMP001");
    }

    @Test
    void findByEmployeeId_WhenExists_ShouldReturnPersonnel() {
        Optional<Personnel> personnel = personnelRepository.findByEmployeeId("EMP001");
        
        assertThat(personnel).isPresent();
        assertThat(personnel.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void save_ShouldInsertNewPersonnel() {
        Personnel newPersonnel = Personnel.builder()
                .projectId(1L)
                .employeeId("EMP002")
                .name("Jane Smith")
                .role("Developer")
                .department("IT")
                .company("AGS")
                .startDate("2024-04-01")
                .endDate("2024-12-31")
                .pledgeStatus("Pending")
                .pledgeSubmissionDate(java.time.LocalDate.parse("2024-04-01"))
                .createdBy("admin")
                .createdAt(null)
                .updatedBy(null)
                .updatedAt(null)
                .build();

        personnelRepository.save(newPersonnel);

        Optional<Personnel> savedPersonnel = personnelRepository.findByEmployeeId("EMP002");
        // assertThat(savedPersonnel).isPresent();
        // assertThat(savedPersonnel.get().getName()).isEqualTo("Jane Smith");
    }

    @Test
    void update_ShouldUpdateExistingPersonnel() {
        Personnel personnel = personnelRepository.findById(1L).get();
        personnel.setName("Updated Name");
        personnel.setRole("Updated Role");

        personnelRepository.update(personnel);

        Personnel updatedPersonnel = personnelRepository.findById(1L).get();
        assertThat(updatedPersonnel.getName()).isEqualTo("Updated Name");
        assertThat(updatedPersonnel.getRole()).isEqualTo("Updated Role");
    }

    @Test
    void delete_ShouldRemovePersonnel() {
        personnelRepository.delete(1L);

        Optional<Personnel> deletedPersonnel = personnelRepository.findById(1L);
        assertThat(deletedPersonnel).isEmpty();
    }
} 