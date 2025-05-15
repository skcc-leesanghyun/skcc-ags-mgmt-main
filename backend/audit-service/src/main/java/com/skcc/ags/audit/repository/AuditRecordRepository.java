package com.skcc.ags.audit.repository;

import com.skcc.ags.audit.domain.AuditRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface AuditRecordRepository {
    List<AuditRecord> findAll();
    Optional<AuditRecord> findById(Long id);
    void save(AuditRecord auditRecord);
    void update(AuditRecord auditRecord);
    void delete(Long id);
} 