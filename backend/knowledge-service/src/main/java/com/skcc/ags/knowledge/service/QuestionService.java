package com.skcc.ags.knowledge.service;

import com.skcc.ags.knowledge.domain.Question;
import com.skcc.ags.knowledge.domain.QuestionCategory;
import com.skcc.ags.knowledge.dto.QuestionDTO;
import com.skcc.ags.knowledge.exception.QuestionNotFoundException;
import com.skcc.ags.knowledge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 질문 생성
    @Transactional
    public QuestionDTO createQuestion(QuestionDTO questionDTO, String username) {
        Question question = convertToEntity(questionDTO);
        question.setCreatedBy(username);
        question.setCreatedAt(LocalDateTime.now());
        question.setViewCount(0L);
        
        Question savedQuestion = questionRepository.save(question);
        return convertToDTO(savedQuestion);
    }

    // 질문 수정
    @Transactional
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO, String username) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id));
        
        // 작성자 검증
        if (!question.getCreatedBy().equals(username)) {
            throw new IllegalStateException("You are not authorized to update this question");
        }
        
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setCategory(questionDTO.getCategory());
        question.setUpdatedBy(username);
        question.setUpdatedAt(LocalDateTime.now());
        
        Question updatedQuestion = questionRepository.save(question);
        return convertToDTO(updatedQuestion);
    }

    // 질문 삭제
    @Transactional
    public void deleteQuestion(Long id, String username) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id));
        
        // 작성자 검증
        if (!question.getCreatedBy().equals(username)) {
            throw new IllegalStateException("You are not authorized to delete this question");
        }
        
        questionRepository.delete(question);
    }

    // 질문 조회 (조회수 증가)
    @Transactional
    public QuestionDTO getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id));
        
        question.setViewCount(question.getViewCount() + 1);
        Question updatedQuestion = questionRepository.save(question);
        return convertToDTO(updatedQuestion);
    }

    // 카테고리별 질문 목록 조회
    public Page<QuestionDTO> getQuestionsByCategory(QuestionCategory category, Pageable pageable) {
        return questionRepository.findByCategory(category, pageable)
                .map(this::convertToDTO);
    }

    // 키워드로 질문 검색
    public Page<QuestionDTO> searchQuestions(String keyword, Pageable pageable) {
        return questionRepository.searchByKeyword(keyword, pageable)
                .map(this::convertToDTO);
    }

    // 사용자가 작성한 질문 목록 조회
    public List<QuestionDTO> getQuestionsByUser(String username) {
        return questionRepository.findByCreatedByOrderByCreatedAtDesc(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 조회수 상위 N개 질문 조회
    public List<QuestionDTO> getTopQuestions(int limit) {
        return questionRepository.findTopByViewCount(Pageable.ofSize(limit))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 답변이 없는 질문 목록 조회
    public List<QuestionDTO> getUnansweredQuestions() {
        return questionRepository.findQuestionsWithNoAnswers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 채택된 답변이 있는 질문 목록 조회
    public List<QuestionDTO> getQuestionsWithAcceptedAnswers() {
        return questionRepository.findQuestionsWithAcceptedAnswers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 카테고리별 질문 수 조회
    public long getQuestionCountByCategory(QuestionCategory category) {
        return questionRepository.countByCategory(category);
    }

    // Entity -> DTO 변환
    private QuestionDTO convertToDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .category(question.getCategory())
                .viewCount(question.getViewCount())
                .createdBy(question.getCreatedBy())
                .createdAt(question.getCreatedAt())
                .updatedBy(question.getUpdatedBy())
                .updatedAt(question.getUpdatedAt())
                .build();
    }

    // DTO -> Entity 변환
    private Question convertToEntity(QuestionDTO questionDTO) {
        return Question.builder()
                .title(questionDTO.getTitle())
                .content(questionDTO.getContent())
                .category(questionDTO.getCategory())
                .build();
    }
} 