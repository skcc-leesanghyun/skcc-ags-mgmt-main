package com.skcc.ags.audit.controller;

import com.skcc.ags.audit.domain.Personnel;
import com.skcc.ags.audit.dto.ApiResponse;
import com.skcc.ags.audit.service.PersonnelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personnel")
public class PersonnelController {
    private static final Logger log = LoggerFactory.getLogger(PersonnelController.class);
    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Personnel>>> getAllPersonnel() {
        List<Personnel> personnelList = personnelService.getAllPersonnel();
        return ResponseEntity.ok(ApiResponse.success(personnelList));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<Personnel>>> getPersonnelByProjectId(@PathVariable Long projectId) {
        List<Personnel> personnelList = personnelService.getPersonnelByProjectId(projectId);
        return ResponseEntity.ok(ApiResponse.success(personnelList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Personnel>> getPersonnelById(@PathVariable Long id) {
        return personnelService.getPersonnelById(id)
                .map(personnel -> ResponseEntity.ok(ApiResponse.success(personnel)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<Personnel>> getPersonnelByEmployeeId(@PathVariable String employeeId) {
        return personnelService.getPersonnelByEmployeeId(employeeId)
                .map(personnel -> ResponseEntity.ok(ApiResponse.success(personnel)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPersonnel(@RequestBody Personnel personnel) {
        personnelService.createPersonnel(personnel);
        return ResponseEntity.ok(ApiResponse.success("Personnel created successfully", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePersonnel(@PathVariable Long id, @RequestBody Personnel personnel) {
        personnel.setId(id);
        personnelService.updatePersonnel(personnel);
        return ResponseEntity.ok(ApiResponse.success("Personnel updated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
        return ResponseEntity.ok(ApiResponse.success("Personnel deleted successfully", null));
    }
} 