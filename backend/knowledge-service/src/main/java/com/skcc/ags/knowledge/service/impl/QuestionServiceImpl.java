package com.skcc.ags.knowledge.service.impl;

import com.skcc.ags.knowledge.domain.Question;
import com.skcc.ags.knowledge.domain.QuestionCategory;
import com.skcc.ags.knowledge.dto.QuestionDTO;
import com.skcc.ags.knowledge.exception.QuestionNotFoundException;
import com.skcc.ags.knowledge.mapper.QuestionMapper;
import com.skcc.ags.knowledge.repository.QuestionRepository;
import com.skcc.ags.knowledge.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        log.debug("Creating new question: {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(savedQuestion);
    }

    @Override
    @Transactional
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        log.debug("Updating question with id {}: {}", id, questionDTO);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        
        question.setTitle(questionDTO.getTitle());
        question.setContent(questionDTO.getContent());
        question.setCategory(questionDTO.getCategory());
        question.setUpdatedBy(questionDTO.getUpdatedBy());
        question.setUpdatedAt(LocalDateTime.now());

        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(updatedQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        log.debug("Deleting question with id: {}", id);
        if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        questionRepository.deleteById(id);
    }

    @Override
    public QuestionDTO getQuestion(Long id) {
        log.debug("Fetching question with id: {}", id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        return questionMapper.toDTO(question);
    }

    @Override
    public Page<QuestionDTO> getAllQuestions(Pageable pageable) {
        log.debug("Fetching all questions with pagination");
        return questionRepository.findAll(pageable)
                .map(questionMapper::toDTO);
    }

    @Override
    public Page<QuestionDTO> getQuestionsByCategory(QuestionCategory category, Pageable pageable) {
        log.debug("Fetching questions by category: {}", category);
        return questionRepository.findByCategory(category, pageable)
                .map(questionMapper::toDTO);
    }

    @Override
    public Page<QuestionDTO> searchQuestions(String keyword, Pageable pageable) {
        log.debug("Searching questions with keyword: {}", keyword);
        return questionRepository.searchByKeyword(keyword, pageable)
                .map(questionMapper::toDTO);
    }

    @Override
    public List<QuestionDTO> getQuestionsByUser(String username) {
        log.debug("Fetching questions by user: {}", username);
        return questionRepository.findByCreatedByOrderByCreatedAtDesc(username)
                .stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDTO> getTopViewedQuestions(int limit) {
        log.debug("Fetching top {} viewed questions", limit);
        return questionRepository.findTopByViewCount(Pageable.ofSize(limit))
                .stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countQuestionsByCategory(QuestionCategory category) {
        log.debug("Counting questions in category: {}", category);
        return questionRepository.countByCategory(category);
    }

    @Override
    public List<QuestionDTO> getQuestionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Fetching questions between {} and {}", startDate, endDate);
        return questionRepository.findByDateRange(startDate, endDate)
                .stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDTO> getQuestionsWithNoAnswers() {
        log.debug("Fetching questions with no answers");
        return questionRepository.findQuestionsWithNoAnswers()
                .stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDTO> getQuestionsWithAcceptedAnswers() {
        log.debug("Fetching questions with accepted answers");
        return questionRepository.findQuestionsWithAcceptedAnswers()
                .stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        log.debug("Incrementing view count for question with id: {}", id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        question.setViewCount(question.getViewCount() + 1);
        questionRepository.save(question);
    }
} 