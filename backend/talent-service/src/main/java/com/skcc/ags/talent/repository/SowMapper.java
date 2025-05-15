package com.ags.talent.repository;

import com.ags.talent.entity.Sow;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SowMapper {
    
    @Select("SELECT s.*, c.* FROM sows s " +
            "LEFT JOIN candidates c ON s.candidate_id = c.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "candidateId", column = "candidate_id"),
        @Result(property = "clientId", column = "client_id"),
        @Result(property = "projectCode", column = "project_code"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "rate", column = "rate"),
        @Result(property = "status", column = "status"),
        @Result(property = "candidate", column = "candidate_id", javaType = com.ags.talent.entity.Candidate.class,
                one = @One(select = "com.ags.talent.repository.CandidateMapper.findById"))
    })
    List<Sow> findAll();
    
    @Select("SELECT s.*, c.* FROM sows s " +
            "LEFT JOIN candidates c ON s.candidate_id = c.id " +
            "WHERE s.id = #{id}")
    @ResultMap("sowResultMap")
    Sow findById(Long id);
    
    @Select("SELECT s.*, c.* FROM sows s " +
            "LEFT JOIN candidates c ON s.candidate_id = c.id " +
            "WHERE s.candidate_id = #{candidateId}")
    @ResultMap("sowResultMap")
    List<Sow> findByCandidateId(Long candidateId);
    
    @Select("SELECT s.*, c.* FROM sows s " +
            "LEFT JOIN candidates c ON s.candidate_id = c.id " +
            "WHERE s.project_code = #{projectCode}")
    @ResultMap("sowResultMap")
    List<Sow> findByProjectCode(String projectCode);
    
    @Select("SELECT s.*, c.* FROM sows s " +
            "LEFT JOIN candidates c ON s.candidate_id = c.id " +
            "WHERE s.status = #{status}")
    @ResultMap("sowResultMap")
    List<Sow> findByStatus(String status);
    
    @Insert("INSERT INTO sows (candidate_id, client_id, project_code, " +
            "start_date, end_date, rate, status) " +
            "VALUES (#{candidateId}, #{clientId}, #{projectCode}, " +
            "#{startDate}, #{endDate}, #{rate}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Sow sow);
    
    @Update("UPDATE sows SET client_id = #{clientId}, " +
            "project_code = #{projectCode}, " +
            "start_date = #{startDate}, " +
            "end_date = #{endDate}, " +
            "rate = #{rate}, " +
            "status = #{status} " +
            "WHERE id = #{id}")
    int update(Sow sow);
    
    @Delete("DELETE FROM sows WHERE id = #{id}")
    int delete(Long id);
} 