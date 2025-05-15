package com.ags.talent.repository;

import com.ags.talent.entity.Document;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DocumentMapper {
    
    @Select("SELECT d.*, p.project_code FROM documents d " +
            "LEFT JOIN projects p ON d.project_id = p.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "projectId", column = "project_id"),
        @Result(property = "projectCode", column = "project_code"),
        @Result(property = "documentType", column = "document_type"),
        @Result(property = "fileName", column = "file_name"),
        @Result(property = "fileSize", column = "file_size"),
        @Result(property = "filePath", column = "file_path"),
        @Result(property = "mimeType", column = "mime_type"),
        @Result(property = "uploadedBy", column = "uploaded_by"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<Document> findAll();
    
    @Select("SELECT d.*, p.project_code FROM documents d " +
            "LEFT JOIN projects p ON d.project_id = p.id " +
            "WHERE d.id = #{id}")
    @ResultMap("documentResultMap")
    Document findById(Long id);
    
    @Select("SELECT d.*, p.project_code FROM documents d " +
            "LEFT JOIN projects p ON d.project_id = p.id " +
            "WHERE d.project_id = #{projectId}")
    @ResultMap("documentResultMap")
    List<Document> findByProjectId(Long projectId);
    
    @Select("SELECT d.*, p.project_code FROM documents d " +
            "LEFT JOIN projects p ON d.project_id = p.id " +
            "WHERE d.document_type = #{documentType}")
    @ResultMap("documentResultMap")
    List<Document> findByDocumentType(String documentType);
    
    @Select("SELECT d.*, p.project_code FROM documents d " +
            "LEFT JOIN projects p ON d.project_id = p.id " +
            "WHERE d.project_id = #{projectId} AND d.document_type = #{documentType}")
    @ResultMap("documentResultMap")
    List<Document> findByProjectIdAndType(
            @Param("projectId") Long projectId,
            @Param("documentType") String documentType);
    
    @Insert("INSERT INTO documents (project_id, document_type, file_name, " +
            "file_size, file_path, mime_type, uploaded_by, status, " +
            "created_at, updated_at) " +
            "VALUES (#{projectId}, #{documentType}, #{fileName}, " +
            "#{fileSize}, #{filePath}, #{mimeType}, #{uploadedBy}, " +
            "#{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Document document);
    
    @Update("UPDATE documents SET file_name = #{fileName}, " +
            "file_size = #{fileSize}, " +
            "file_path = #{filePath}, " +
            "mime_type = #{mimeType}, " +
            "status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(Document document);
    
    @Delete("DELETE FROM documents WHERE id = #{id}")
    int delete(Long id);
    
    @Select("SELECT COUNT(*) FROM documents " +
            "WHERE project_id = #{projectId} AND document_type = #{documentType}")
    int countByProjectIdAndType(
            @Param("projectId") Long projectId,
            @Param("documentType") String documentType);
    
    @Select("SELECT d.*, p.project_code FROM documents d " +
            "LEFT JOIN projects p ON d.project_id = p.id " +
            "WHERE d.uploaded_by = #{userId}")
    @ResultMap("documentResultMap")
    List<Document> findByUploadedBy(Long userId);
    
    @Update("UPDATE documents SET status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
} 