package com.ags.talent.repository;

import com.ags.talent.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface RoleMapper {
    
    @Select("SELECT r.*, p.permission_name FROM roles r " +
            "LEFT JOIN role_permissions rp ON r.id = rp.role_id " +
            "LEFT JOIN permissions p ON rp.permission_id = p.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "roleName", column = "role_name"),
        @Result(property = "description", column = "description"),
        @Result(property = "isSystem", column = "is_system"),
        @Result(property = "status", column = "status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "permissions", column = "id", javaType = Set.class,
                many = @Many(select = "findPermissionsByRoleId"))
    })
    List<Role> findAll();
    
    @Select("SELECT r.*, p.permission_name FROM roles r " +
            "LEFT JOIN role_permissions rp ON r.id = rp.role_id " +
            "LEFT JOIN permissions p ON rp.permission_id = p.id " +
            "WHERE r.id = #{id}")
    @ResultMap("roleResultMap")
    Role findById(Long id);
    
    @Select("SELECT r.*, p.permission_name FROM roles r " +
            "LEFT JOIN role_permissions rp ON r.id = rp.role_id " +
            "LEFT JOIN permissions p ON rp.permission_id = p.id " +
            "WHERE r.role_name = #{roleName}")
    @ResultMap("roleResultMap")
    Role findByRoleName(String roleName);
    
    @Select("SELECT p.permission_name FROM permissions p " +
            "INNER JOIN role_permissions rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    Set<String> findPermissionsByRoleId(Long roleId);
    
    @Insert("INSERT INTO roles (role_name, description, is_system, status, " +
            "created_at, updated_at) " +
            "VALUES (#{roleName}, #{description}, #{isSystem}, #{status}, " +
            "NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Role role);
    
    @Update("UPDATE roles SET role_name = #{roleName}, " +
            "description = #{description}, " +
            "status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(Role role);
    
    @Delete("DELETE FROM roles WHERE id = #{id}")
    int delete(Long id);
    
    @Insert("INSERT INTO role_permissions (role_id, permission_id) " +
            "VALUES (#{roleId}, #{permissionId})")
    int assignPermission(@Param("roleId") Long roleId, 
                        @Param("permissionId") Long permissionId);
    
    @Delete("DELETE FROM role_permissions " +
            "WHERE role_id = #{roleId} AND permission_id = #{permissionId}")
    int removePermission(@Param("roleId") Long roleId, 
                        @Param("permissionId") Long permissionId);
    
    @Select("SELECT r.*, p.permission_name FROM roles r " +
            "LEFT JOIN role_permissions rp ON r.id = rp.role_id " +
            "LEFT JOIN permissions p ON rp.permission_id = p.id " +
            "WHERE r.status = #{status}")
    @ResultMap("roleResultMap")
    List<Role> findByStatus(String status);
    
    @Select("SELECT COUNT(*) FROM roles WHERE status = #{status}")
    int countByStatus(String status);
    
    @Select("SELECT r.* FROM roles r " +
            "INNER JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    @ResultMap("roleResultMap")
    List<Role> findByUserId(Long userId);
    
    @Select("SELECT COUNT(*) FROM user_roles WHERE role_id = #{roleId}")
    int countUsersByRole(Long roleId);
    
    @Update("UPDATE roles SET status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    
    @Delete("DELETE FROM role_permissions WHERE role_id = #{roleId}")
    int removeAllPermissions(Long roleId);
    
    @Insert({
        "<script>",
        "INSERT INTO role_permissions (role_id, permission_id) VALUES ",
        "<foreach collection='permissionIds' item='permissionId' separator=','>",
        "(#{roleId}, #{permissionId})",
        "</foreach>",
        "</script>"
    })
    int assignPermissions(@Param("roleId") Long roleId, 
                         @Param("permissionIds") List<Long> permissionIds);
} 