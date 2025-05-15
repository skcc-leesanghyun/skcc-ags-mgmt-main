package com.skcc.ags.audit.service;

import com.skcc.ags.audit.domain.Personnel;
import com.skcc.ags.audit.repository.PersonnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelService {
    private final PersonnelRepository personnelRepository;

    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    public List<Personnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    public List<Personnel> getPersonnelByProjectId(Long projectId) {
        return personnelRepository.findByProjectId(projectId);
    }

    public Optional<Personnel> getPersonnelById(Long id) {
        return personnelRepository.findById(id);
    }

    public Optional<Personnel> getPersonnelByEmployeeId(String employeeId) {
        return personnelRepository.findByEmployeeId(employeeId);
    }

    @Transactional
    public void createPersonnel(Personnel personnel) {
        personnelRepository.save(personnel);
    }

    @Transactional
    public void updatePersonnel(Personnel personnel) {
        personnelRepository.update(personnel);
    }

    @Transactional
    public void deletePersonnel(Long id) {
        personnelRepository.delete(id);
    }
} 