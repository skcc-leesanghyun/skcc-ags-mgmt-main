package com.skcc.ags.audit.repository;

import com.skcc.ags.audit.domain.Personnel;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PersonnelRepository {
    List<Personnel> findAll();
    List<Personnel> findByProjectId(Long projectId);
    Optional<Personnel> findById(Long id);
    Optional<Personnel> findByEmployeeId(String employeeId);
    void save(Personnel personnel);
    void update(Personnel personnel);
    void delete(Long id);
} 