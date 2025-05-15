package com.skcc.ags.knowledge.mapper;

import com.skcc.ags.knowledge.domain.Answer;
import com.skcc.ags.knowledge.domain.Guide;
import com.skcc.ags.knowledge.domain.Question;
import com.skcc.ags.knowledge.dto.AnswerDTO;
import com.skcc.ags.knowledge.dto.GuideDTO;
import com.skcc.ags.knowledge.dto.QuestionDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KnowledgeMapper {

    // Guide 매핑 메서드
    public GuideDTO toGuideDTO(Guide guide) {
        if (guide == null) return null;
        
        return GuideDTO.builder()
                .id(guide.getId())
                .title(guide.getTitle())
                .content(guide.getContent())
                .filePath(guide.getFilePath())
                .fileName(guide.getFileName())
                .fileSize(guide.getFileSize())
                .fileType(guide.getFileType())
                .stage(guide.getStage())
                .version(guide.getVersion())
                .createdBy(guide.getCreatedBy())
                .createdAt(guide.getCreatedAt())
                .updatedBy(guide.getUpdatedBy())
                .updatedAt(guide.getUpdatedAt())
                .build();
    }

    public Guide toGuide(GuideDTO guideDTO) {
        if (guideDTO == null) return null;

        return Guide.builder()
                .id(guideDTO.getId())
                .title(guideDTO.getTitle())
                .content(guideDTO.getContent())
                .filePath(guideDTO.getFilePath())
                .fileName(guideDTO.getFileName())
                .fileSize(guideDTO.getFileSize())
                .fileType(guideDTO.getFileType())
                .stage(guideDTO.getStage())
                .version(guideDTO.getVersion())
                .createdBy(guideDTO.getCreatedBy())
                .createdAt(guideDTO.getCreatedAt())
                .updatedBy(guideDTO.getUpdatedBy())
                .updatedAt(guideDTO.getUpdatedAt())
                .build();
    }

    // Question 매핑 메서드
    public QuestionDTO toQuestionDTO(Question question) {
        if (question == null) return null;

        QuestionDTO dto = QuestionDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .viewCount(question.getViewCount())
                .category(question.getCategory())
                .createdBy(question.getCreatedBy())
                .createdAt(question.getCreatedAt())
                .updatedBy(question.getUpdatedBy())
                .updatedAt(question.getUpdatedAt())
                .build();

        if (question.getAnswers() != null) {
            dto.getAnswers().addAll(
                    question.getAnswers().stream()
                            .map(this::toAnswerDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Question toQuestion(QuestionDTO questionDTO) {
        if (questionDTO == null) return null;

        Question question = Question.builder()
                .id(questionDTO.getId())
                .title(questionDTO.getTitle())
                .content(questionDTO.getContent())
                .viewCount(questionDTO.getViewCount())
                .category(questionDTO.getCategory())
                .createdBy(questionDTO.getCreatedBy())
                .createdAt(questionDTO.getCreatedAt())
                .updatedBy(questionDTO.getUpdatedBy())
                .updatedAt(questionDTO.getUpdatedAt())
                .build();

        if (questionDTO.getAnswers() != null) {
            question.setAnswers(
                    questionDTO.getAnswers().stream()
                            .map(this::toAnswer)
                            .collect(Collectors.toList())
            );
        }

        return question;
    }

    // Answer 매핑 메서드
    public AnswerDTO toAnswerDTO(Answer answer) {
        if (answer == null) return null;

        return AnswerDTO.builder()
                .id(answer.getId())
                .questionId(answer.getQuestionId())
                .content(answer.getContent())
                .accepted(answer.isAccepted())
                .createdBy(answer.getCreatedBy())
                .createdAt(answer.getCreatedAt())
                .updatedBy(answer.getUpdatedBy())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }

    public Answer toAnswer(AnswerDTO answerDTO) {
        if (answerDTO == null) return null;

        return Answer.builder()
                .id(answerDTO.getId())
                .questionId(answerDTO.getQuestionId())
                .content(answerDTO.getContent())
                .accepted(answerDTO.isAccepted())
                .createdBy(answerDTO.getCreatedBy())
                .createdAt(answerDTO.getCreatedAt())
                .updatedBy(answerDTO.getUpdatedBy())
                .updatedAt(answerDTO.getUpdatedAt())
                .build();
    }

    // List 매핑 메서드
    public List<GuideDTO> toGuideDTOList(List<Guide> guides) {
        if (guides == null) return null;
        return guides.stream()
                .map(this::toGuideDTO)
                .collect(Collectors.toList());
    }

    public List<Guide> toGuideList(List<GuideDTO> guideDTOs) {
        if (guideDTOs == null) return null;
        return guideDTOs.stream()
                .map(this::toGuide)
                .collect(Collectors.toList());
    }

    public List<QuestionDTO> toQuestionDTOList(List<Question> questions) {
        if (questions == null) return null;
        return questions.stream()
                .map(this::toQuestionDTO)
                .collect(Collectors.toList());
    }

    public List<Question> toQuestionList(List<QuestionDTO> questionDTOs) {
        if (questionDTOs == null) return null;
        return questionDTOs.stream()
                .map(this::toQuestion)
                .collect(Collectors.toList());
    }

    public List<AnswerDTO> toAnswerDTOList(List<Answer> answers) {
        if (answers == null) return null;
        return answers.stream()
                .map(this::toAnswerDTO)
                .collect(Collectors.toList());
    }

    public List<Answer> toAnswerList(List<AnswerDTO> answerDTOs) {
        if (answerDTOs == null) return null;
        return answerDTOs.stream()
                .map(this::toAnswer)
                .collect(Collectors.toList());
    }
} 