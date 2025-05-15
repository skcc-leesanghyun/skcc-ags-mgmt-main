package com.ags.talent.repository;

import com.ags.talent.entity.Evaluation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EvaluationMapper {
    
    @Select("SELECT e.*, c.* FROM evaluations e " +
            "LEFT JOIN candidates c ON e.candidate_id = c.id")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "candidateId", column = "candidate_id"),
        @Result(property = "evaluatorId", column = "evaluator_id"),
        @Result(property = "technicalScore", column = "technical_score"),
        @Result(property = "communicationScore", column = "communication_score"),
        @Result(property = "experienceScore", column = "experience_score"),
        @Result(property = "overallScore", column = "overall_score"),
        @Result(property = "evaluationDate", column = "evaluation_date"),
        @Result(property = "candidate", column = "candidate_id", javaType = com.ags.talent.entity.Candidate.class,
                one = @One(select = "com.ags.talent.repository.CandidateMapper.findById"))
    })
    List<Evaluation> findAll();
    
    @Select("SELECT e.*, c.* FROM evaluations e " +
            "LEFT JOIN candidates c ON e.candidate_id = c.id " +
            "WHERE e.id = #{id}")
    @ResultMap("evaluationResultMap")
    Evaluation findById(Long id);
    
    @Select("SELECT e.*, c.* FROM evaluations e " +
            "LEFT JOIN candidates c ON e.candidate_id = c.id " +
            "WHERE e.candidate_id = #{candidateId}")
    @ResultMap("evaluationResultMap")
    List<Evaluation> findByCandidateId(Long candidateId);
    
    @Insert("INSERT INTO evaluations (candidate_id, evaluator_id, technical_score, " +
            "communication_score, experience_score, overall_score, comments, evaluation_date) " +
            "VALUES (#{candidateId}, #{evaluatorId}, #{technicalScore}, " +
            "#{communicationScore}, #{experienceScore}, #{overallScore}, " +
            "#{comments}, #{evaluationDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Evaluation evaluation);
    
    @Update("UPDATE evaluations SET technical_score = #{technicalScore}, " +
            "communication_score = #{communicationScore}, " +
            "experience_score = #{experienceScore}, " +
            "overall_score = #{overallScore}, " +
            "comments = #{comments}, " +
            "evaluation_date = #{evaluationDate} " +
            "WHERE id = #{id}")
    int update(Evaluation evaluation);
    
    @Delete("DELETE FROM evaluations WHERE id = #{id}")
    int delete(Long id);
} 