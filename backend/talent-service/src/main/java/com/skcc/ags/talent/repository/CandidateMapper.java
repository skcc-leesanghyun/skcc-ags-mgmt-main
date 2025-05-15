package com.ags.talent.repository;

import com.ags.talent.entity.Candidate;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CandidateMapper {
    
    @Select("SELECT * FROM candidates")
    List<Candidate> findAll();
    
    @Select("SELECT * FROM candidates WHERE id = #{id}")
    Candidate findById(Long id);
    
    @Select("SELECT * FROM candidates WHERE name ILIKE '%${keyword}%' OR " +
            "#{keyword} = ANY(skills) OR " +
            "background ILIKE '%${keyword}%'")
    List<Candidate> search(String keyword);
    
    @Insert("INSERT INTO candidates (name, email, phone, skills, experience_years, " +
            "availability_date, background, created_at, updated_at) " +
            "VALUES (#{name}, #{email}, #{phone}, #{skills}, #{experienceYears}, " +
            "#{availabilityDate}, #{background}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Candidate candidate);
    
    @Update("UPDATE candidates SET name = #{name}, email = #{email}, " +
            "phone = #{phone}, skills = #{skills}, experience_years = #{experienceYears}, " +
            "availability_date = #{availabilityDate}, background = #{background}, " +
            "updated_at = NOW() WHERE id = #{id}")
    int update(Candidate candidate);
    
    @Delete("DELETE FROM candidates WHERE id = #{id}")
    int delete(Long id);
    
    @Select("SELECT * FROM candidates WHERE " +
            "experience_years >= #{minExperience} AND " +
            "#{requiredSkill} = ANY(skills)")
    List<Candidate> findByExperienceAndSkill(
            @Param("minExperience") Integer minExperience,
            @Param("requiredSkill") String requiredSkill);
} 