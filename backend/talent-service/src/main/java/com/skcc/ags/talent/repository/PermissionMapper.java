package com.ags.talent.repository;

import com.ags.talent.entity.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface PermissionMapper {
    
    @Select("SELECT * FROM permissions")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "permissionName", column = "permission_name"),
        @Result(property = "description", column = "description"),
        @Result(property = "category", column = "category"),
        @Result(property = "isSystem", column = "is_system"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<Permission> findAll();
    
    @Select("SELECT * FROM permissions WHERE id = #{id}")
    @ResultMap("permissionResultMap")
    Permission findById(Long id);
    
    @Select("SELECT * FROM permissions WHERE permission_name = #{permissionName}")
    @ResultMap("permissionResultMap")
    Permission findByPermissionName(String permissionName);
    
    @Select("SELECT p.* FROM permissions p " +
            "INNER JOIN role_permissions rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    @ResultMap("permissionResultMap")
    List<Permission> findByRoleId(Long roleId);
    
    @Insert("INSERT INTO permissions (permission_name, description, category, " +
            "is_system, status, created_at, updated_at) " +
            "VALUES (#{permissionName}, #{description}, #{category}, " +
            "#{isSystem}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Permission permission);
    
    @Update("UPDATE permissions SET permission_name = #{permissionName}, " +
            "description = #{description}, " +
            "category = #{category}, " +
            "status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(Permission permission);
    
    @Delete("DELETE FROM permissions WHERE id = #{id}")
    int delete(Long id);
    
    @Select("SELECT * FROM permissions WHERE category = #{category}")
    @ResultMap("permissionResultMap")
    List<Permission> findByCategory(String category);
    
    @Select("SELECT COUNT(*) FROM permissions WHERE category = #{category}")
    int countByCategory(String category);
    
    @Select("SELECT * FROM permissions WHERE status = #{status}")
    @ResultMap("permissionResultMap")
    List<Permission> findByStatus(String status);
    
    @Select("SELECT COUNT(*) FROM permissions WHERE status = #{status}")
    int countByStatus(String status);
    
    @Update("UPDATE permissions SET status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    
    @Select("SELECT DISTINCT category FROM permissions")
    List<String> findAllCategories();
    
    @Select("SELECT COUNT(*) FROM role_permissions WHERE permission_id = #{permissionId}")
    int countRolesByPermission(Long permissionId);
} 