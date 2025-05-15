package com.ags.talent.repository;

import com.ags.talent.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {
    
    @Select("SELECT u.*, r.role_name FROM users u " +
            "LEFT JOIN user_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN roles r ON ur.role_id = r.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "email", column = "email"),
        @Result(property = "fullName", column = "full_name"),
        @Result(property = "department", column = "department"),
        @Result(property = "position", column = "position"),
        @Result(property = "phoneNumber", column = "phone_number"),
        @Result(property = "status", column = "status"),
        @Result(property = "lastLoginAt", column = "last_login_at"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "roles", column = "id", javaType = Set.class,
                many = @Many(select = "findRolesByUserId"))
    })
    List<User> findAll();
    
    @Select("SELECT u.*, r.role_name FROM users u " +
            "LEFT JOIN user_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN roles r ON ur.role_id = r.id " +
            "WHERE u.id = #{id}")
    @ResultMap("userResultMap")
    User findById(Long id);
    
    @Select("SELECT u.*, r.role_name FROM users u " +
            "LEFT JOIN user_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN roles r ON ur.role_id = r.id " +
            "WHERE u.username = #{username}")
    @ResultMap("userResultMap")
    User findByUsername(String username);
    
    @Select("SELECT u.*, r.role_name FROM users u " +
            "LEFT JOIN user_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN roles r ON ur.role_id = r.id " +
            "WHERE u.email = #{email}")
    @ResultMap("userResultMap")
    User findByEmail(String email);
    
    @Select("SELECT r.role_name FROM roles r " +
            "INNER JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    Set<String> findRolesByUserId(Long userId);
    
    @Insert("INSERT INTO users (username, email, full_name, department, " +
            "position, phone_number, status, created_at, updated_at) " +
            "VALUES (#{username}, #{email}, #{fullName}, #{department}, " +
            "#{position}, #{phoneNumber}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Update("UPDATE users SET email = #{email}, " +
            "full_name = #{fullName}, " +
            "department = #{department}, " +
            "position = #{position}, " +
            "phone_number = #{phoneNumber}, " +
            "status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(User user);
    
    @Delete("DELETE FROM users WHERE id = #{id}")
    int delete(Long id);
    
    @Insert("INSERT INTO user_roles (user_id, role_id) " +
            "VALUES (#{userId}, #{roleId})")
    int assignRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    @Delete("DELETE FROM user_roles WHERE user_id = #{userId} AND role_id = #{roleId}")
    int removeRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    @Update("UPDATE users SET last_login_at = NOW() WHERE id = #{id}")
    int updateLastLogin(Long id);
    
    @Select("SELECT u.*, r.role_name FROM users u " +
            "LEFT JOIN user_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN roles r ON ur.role_id = r.id " +
            "WHERE u.department = #{department}")
    @ResultMap("userResultMap")
    List<User> findByDepartment(String department);
    
    @Select("SELECT COUNT(*) FROM users WHERE department = #{department}")
    int countByDepartment(String department);
    
    @Select("SELECT u.*, r.role_name FROM users u " +
            "LEFT JOIN user_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN roles r ON ur.role_id = r.id " +
            "WHERE r.role_name = #{roleName}")
    @ResultMap("userResultMap")
    List<User> findByRole(String roleName);
    
    @Update("UPDATE users SET status = #{status}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
} 