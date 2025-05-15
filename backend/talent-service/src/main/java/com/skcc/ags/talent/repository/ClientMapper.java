package com.ags.talent.repository;

import com.ags.talent.entity.Client;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClientMapper {
    
    @Select("SELECT * FROM clients")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "companyName", column = "company_name"),
        @Result(property = "businessNumber", column = "business_number"),
        @Result(property = "contactPerson", column = "contact_person"),
        @Result(property = "contactEmail", column = "contact_email"),
        @Result(property = "contactPhone", column = "contact_phone"),
        @Result(property = "address", column = "address"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<Client> findAll();
    
    @Select("SELECT * FROM clients WHERE id = #{id}")
    @ResultMap("clientResultMap")
    Client findById(Long id);
    
    @Select("SELECT * FROM clients WHERE company_name ILIKE '%${keyword}%' OR " +
            "business_number LIKE '%${keyword}%'")
    @ResultMap("clientResultMap")
    List<Client> search(String keyword);
    
    @Select("SELECT * FROM clients WHERE status = #{status}")
    @ResultMap("clientResultMap")
    List<Client> findByStatus(String status);
    
    @Insert("INSERT INTO clients (company_name, business_number, contact_person, " +
            "contact_email, contact_phone, address, status, created_at, updated_at) " +
            "VALUES (#{companyName}, #{businessNumber}, #{contactPerson}, " +
            "#{contactEmail}, #{contactPhone}, #{address}, #{status}, " +
            "NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Client client);
    
    @Update("UPDATE clients SET company_name = #{companyName}, " +
            "business_number = #{businessNumber}, " +
            "contact_person = #{contactPerson}, " +
            "contact_email = #{contactEmail}, " +
            "contact_phone = #{contactPhone}, " +
            "address = #{address}, " +
            "status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(Client client);
    
    @Delete("DELETE FROM clients WHERE id = #{id}")
    int delete(Long id);
    
    @Select("SELECT c.* FROM clients c " +
            "INNER JOIN projects p ON c.id = p.client_id " +
            "WHERE p.id = #{projectId}")
    @ResultMap("clientResultMap")
    Client findByProjectId(Long projectId);
} 