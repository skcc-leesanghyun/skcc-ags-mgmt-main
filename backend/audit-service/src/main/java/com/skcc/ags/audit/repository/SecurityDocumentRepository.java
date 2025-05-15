package com.skcc.ags.audit.repository;

import com.skcc.ags.audit.domain.SecurityDocument;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface SecurityDocumentRepository {
    List<SecurityDocument> findAll();
    Optional<SecurityDocument> findById(Long id);
    List<SecurityDocument> findByAuditRecordId(Long auditRecordId);
    void save(SecurityDocument securityDocument);
    void update(SecurityDocument securityDocument);
    void delete(Long id);
} 