package com.ags.talent.repository;

import com.ags.talent.entity.Performance;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.sql.Date;

@Mapper
public interface PerformanceMapper {
    
    @Select("SELECT p.*, pr.project_code, pr.name as project_name FROM performances p " +
            "LEFT JOIN projects pr ON p.project_id = pr.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "projectId", column = "project_id"),
        @Result(property = "projectCode", column = "project_code"),
        @Result(property = "projectName", column = "project_name"),
        @Result(property = "recordDate", column = "record_date"),
        @Result(property = "plannedManpower", column = "planned_manpower"),
        @Result(property = "actualManpower", column = "actual_manpower"),
        @Result(property = "utilization", column = "utilization"),
        @Result(property = "efficiency", column = "efficiency"),
        @Result(property = "comments", column = "comments"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<Performance> findAll();
    
    @Select("SELECT p.*, pr.project_code, pr.name as project_name FROM performances p " +
            "LEFT JOIN projects pr ON p.project_id = pr.id " +
            "WHERE p.id = #{id}")
    @ResultMap("performanceResultMap")
    Performance findById(Long id);
    
    @Select("SELECT p.*, pr.project_code, pr.name as project_name FROM performances p " +
            "LEFT JOIN projects pr ON p.project_id = pr.id " +
            "WHERE p.project_id = #{projectId}")
    @ResultMap("performanceResultMap")
    List<Performance> findByProjectId(Long projectId);
    
    @Select("SELECT p.*, pr.project_code, pr.name as project_name FROM performances p " +
            "LEFT JOIN projects pr ON p.project_id = pr.id " +
            "WHERE p.record_date BETWEEN #{startDate} AND #{endDate}")
    @ResultMap("performanceResultMap")
    List<Performance> findByDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
    @Select("SELECT p.*, pr.project_code, pr.name as project_name FROM performances p " +
            "LEFT JOIN projects pr ON p.project_id = pr.id " +
            "WHERE p.project_id = #{projectId} AND " +
            "p.record_date BETWEEN #{startDate} AND #{endDate}")
    @ResultMap("performanceResultMap")
    List<Performance> findByProjectIdAndDateRange(
            @Param("projectId") Long projectId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
    @Insert("INSERT INTO performances (project_id, record_date, planned_manpower, " +
            "actual_manpower, utilization, efficiency, comments, created_at, updated_at) " +
            "VALUES (#{projectId}, #{recordDate}, #{plannedManpower}, " +
            "#{actualManpower}, #{utilization}, #{efficiency}, #{comments}, " +
            "NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Performance performance);
    
    @Update("UPDATE performances SET planned_manpower = #{plannedManpower}, " +
            "actual_manpower = #{actualManpower}, " +
            "utilization = #{utilization}, " +
            "efficiency = #{efficiency}, " +
            "comments = #{comments}, " +
            "updated_at = NOW() " +
            "WHERE id = #{id}")
    int update(Performance performance);
    
    @Delete("DELETE FROM performances WHERE id = #{id}")
    int delete(Long id);
    
    @Select("SELECT AVG(utilization) FROM performances " +
            "WHERE project_id = #{projectId} AND " +
            "record_date BETWEEN #{startDate} AND #{endDate}")
    Double calculateAverageUtilization(
            @Param("projectId") Long projectId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
    @Select("SELECT AVG(efficiency) FROM performances " +
            "WHERE project_id = #{projectId} AND " +
            "record_date BETWEEN #{startDate} AND #{endDate}")
    Double calculateAverageEfficiency(
            @Param("projectId") Long projectId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
    @Select("SELECT SUM(actual_manpower) FROM performances " +
            "WHERE project_id = #{projectId} AND " +
            "record_date BETWEEN #{startDate} AND #{endDate}")
    Integer calculateTotalActualManpower(
            @Param("projectId") Long projectId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
    @Select("SELECT p.*, pr.project_code, pr.name as project_name FROM performances p " +
            "LEFT JOIN projects pr ON p.project_id = pr.id " +
            "WHERE p.record_date = #{date} " +
            "ORDER BY p.utilization DESC LIMIT 5")
    @ResultMap("performanceResultMap")
    List<Performance> findTopPerformingProjectsByDate(Date date);
} 