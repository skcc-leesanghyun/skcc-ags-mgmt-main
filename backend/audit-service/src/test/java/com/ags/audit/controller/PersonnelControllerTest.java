package com.skcc.ags.audit.controller;

import com.skcc.ags.audit.domain.Personnel;
import com.skcc.ags.audit.service.PersonnelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonnelController.class)
class PersonnelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    void getAllPersonnel_ShouldReturnPersonnelList() throws Exception {
        when(personnelService.getAllPersonnel()).thenReturn(Arrays.asList(testPersonnel));

        mockMvc.perform(get("/api/v1/personnel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[0].employeeId").value("EMP001"));

        verify(personnelService).getAllPersonnel();
    }

    @Test
    void getPersonnelByProjectId_ShouldReturnPersonnelList() throws Exception {
        when(personnelService.getPersonnelByProjectId(1L)).thenReturn(Arrays.asList(testPersonnel));

        mockMvc.perform(get("/api/v1/personnel/project/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[0].employeeId").value("EMP001"));

        verify(personnelService).getPersonnelByProjectId(1L);
    }

    @Test
    void getPersonnelById_WhenPersonnelExists_ShouldReturnPersonnel() throws Exception {
        when(personnelService.getPersonnelById(1L)).thenReturn(Optional.of(testPersonnel));

        mockMvc.perform(get("/api/v1/personnel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.employeeId").value("EMP001"));

        verify(personnelService).getPersonnelById(1L);
    }

    @Test
    void getPersonnelById_WhenPersonnelDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(personnelService.getPersonnelById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/personnel/1"))
                .andExpect(status().isNotFound());

        verify(personnelService).getPersonnelById(1L);
    }

    @Test
    void getPersonnelByEmployeeId_WhenPersonnelExists_ShouldReturnPersonnel() throws Exception {
        when(personnelService.getPersonnelByEmployeeId("EMP001")).thenReturn(Optional.of(testPersonnel));

        mockMvc.perform(get("/api/v1/personnel/employee/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.employeeId").value("EMP001"));

        verify(personnelService).getPersonnelByEmployeeId("EMP001");
    }

    @Test
    void createPersonnel_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/personnel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPersonnel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Personnel created successfully"));

        verify(personnelService).createPersonnel(any(Personnel.class));
    }

    @Test
    void updatePersonnel_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(put("/api/v1/personnel/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPersonnel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Personnel updated successfully"));

        verify(personnelService).updatePersonnel(any(Personnel.class));
    }

    @Test
    void deletePersonnel_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/personnel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Personnel deleted successfully"));

        verify(personnelService).deletePersonnel(1L);
    }
} 