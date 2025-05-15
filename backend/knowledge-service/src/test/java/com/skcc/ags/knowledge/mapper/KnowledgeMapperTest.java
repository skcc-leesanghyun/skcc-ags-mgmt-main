package com.skcc.ags.knowledge.mapper;

import com.skcc.ags.knowledge.domain.Answer;
import com.skcc.ags.knowledge.domain.Guide;
import com.skcc.ags.knowledge.domain.GuideStage;
import com.skcc.ags.knowledge.domain.Question;
import com.skcc.ags.knowledge.domain.QuestionCategory;
import com.skcc.ags.knowledge.dto.AnswerDTO;
import com.skcc.ags.knowledge.dto.GuideDTO;
import com.skcc.ags.knowledge.dto.QuestionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KnowledgeMapperTest {

    private KnowledgeMapper mapper;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        mapper = new KnowledgeMapper();
        now = LocalDateTime.now();
    }

    @Test
    void toGuideDTO_ShouldMapAllFields() {
        // given
        Guide guide = Guide.builder()
                .id(1L)
                .title("가이드 제목")
                .content("가이드 내용")
                .filePath("/path/to/file")
                .fileName("guide.pdf")
                .fileSize(1024L)
                .fileType("application/pdf")
                .stage(GuideStage.PLANNING)
                .version("1.0.0")
                .createdBy("user1")
                .createdAt(now)
                .updatedBy("user2")
                .updatedAt(now)
                .build();

        // when
        GuideDTO dto = mapper.toGuideDTO(guide);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(guide.getId());
        assertThat(dto.getTitle()).isEqualTo(guide.getTitle());
        assertThat(dto.getContent()).isEqualTo(guide.getContent());
        assertThat(dto.getFilePath()).isEqualTo(guide.getFilePath());
        assertThat(dto.getFileName()).isEqualTo(guide.getFileName());
        assertThat(dto.getFileSize()).isEqualTo(guide.getFileSize());
        assertThat(dto.getFileType()).isEqualTo(guide.getFileType());
        assertThat(dto.getStage()).isEqualTo(guide.getStage());
        assertThat(dto.getVersion()).isEqualTo(guide.getVersion());
        assertThat(dto.getCreatedBy()).isEqualTo(guide.getCreatedBy());
        assertThat(dto.getCreatedAt()).isEqualTo(guide.getCreatedAt());
        assertThat(dto.getUpdatedBy()).isEqualTo(guide.getUpdatedBy());
        assertThat(dto.getUpdatedAt()).isEqualTo(guide.getUpdatedAt());
    }

    @Test
    void toQuestionDTO_ShouldMapAllFieldsIncludingAnswers() {
        // given
        Answer answer = Answer.builder()
                .id(1L)
                .questionId(1L)
                .content("답변 내용")
                .accepted(true)
                .createdBy("user1")
                .createdAt(now)
                .updatedBy("user2")
                .updatedAt(now)
                .build();

        Question question = Question.builder()
                .id(1L)
                .title("질문 제목")
                .content("질문 내용")
                .viewCount(10L)
                .category(QuestionCategory.TECHNICAL)
                .answers(Arrays.asList(answer))
                .createdBy("user1")
                .createdAt(now)
                .updatedBy("user2")
                .updatedAt(now)
                .build();

        // when
        QuestionDTO dto = mapper.toQuestionDTO(question);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(question.getId());
        assertThat(dto.getTitle()).isEqualTo(question.getTitle());
        assertThat(dto.getContent()).isEqualTo(question.getContent());
        assertThat(dto.getViewCount()).isEqualTo(question.getViewCount());
        assertThat(dto.getCategory()).isEqualTo(question.getCategory());
        assertThat(dto.getCreatedBy()).isEqualTo(question.getCreatedBy());
        assertThat(dto.getCreatedAt()).isEqualTo(question.getCreatedAt());
        assertThat(dto.getUpdatedBy()).isEqualTo(question.getUpdatedBy());
        assertThat(dto.getUpdatedAt()).isEqualTo(question.getUpdatedAt());
        
        assertThat(dto.getAnswers()).hasSize(1);
        AnswerDTO answerDTO = dto.getAnswers().get(0);
        assertThat(answerDTO.getId()).isEqualTo(answer.getId());
        assertThat(answerDTO.getQuestionId()).isEqualTo(answer.getQuestionId());
        assertThat(answerDTO.getContent()).isEqualTo(answer.getContent());
        assertThat(answerDTO.isAccepted()).isEqualTo(answer.isAccepted());
    }

    @Test
    void toAnswerDTO_ShouldMapAllFields() {
        // given
        Answer answer = Answer.builder()
                .id(1L)
                .questionId(1L)
                .content("답변 내용")
                .accepted(true)
                .createdBy("user1")
                .createdAt(now)
                .updatedBy("user2")
                .updatedAt(now)
                .build();

        // when
        AnswerDTO dto = mapper.toAnswerDTO(answer);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(answer.getId());
        assertThat(dto.getQuestionId()).isEqualTo(answer.getQuestionId());
        assertThat(dto.getContent()).isEqualTo(answer.getContent());
        assertThat(dto.isAccepted()).isEqualTo(answer.isAccepted());
        assertThat(dto.getCreatedBy()).isEqualTo(answer.getCreatedBy());
        assertThat(dto.getCreatedAt()).isEqualTo(answer.getCreatedAt());
        assertThat(dto.getUpdatedBy()).isEqualTo(answer.getUpdatedBy());
        assertThat(dto.getUpdatedAt()).isEqualTo(answer.getUpdatedAt());
    }

    @Test
    void listConversions_ShouldMapCorrectly() {
        // given
        Guide guide1 = Guide.builder().id(1L).title("가이드1").build();
        Guide guide2 = Guide.builder().id(2L).title("가이드2").build();
        List<Guide> guides = Arrays.asList(guide1, guide2);

        Question question1 = Question.builder().id(1L).title("질문1").build();
        Question question2 = Question.builder().id(2L).title("질문2").build();
        List<Question> questions = Arrays.asList(question1, question2);

        Answer answer1 = Answer.builder().id(1L).content("답변1").build();
        Answer answer2 = Answer.builder().id(2L).content("답변2").build();
        List<Answer> answers = Arrays.asList(answer1, answer2);

        // when
        List<GuideDTO> guideDTOs = mapper.toGuideDTOList(guides);
        List<QuestionDTO> questionDTOs = mapper.toQuestionDTOList(questions);
        List<AnswerDTO> answerDTOs = mapper.toAnswerDTOList(answers);

        // then
        assertThat(guideDTOs).hasSize(2);
        assertThat(questionDTOs).hasSize(2);
        assertThat(answerDTOs).hasSize(2);
    }

    @Test
    void nullInputs_ShouldReturnNull() {
        assertThat(mapper.toGuideDTO(null)).isNull();
        assertThat(mapper.toQuestionDTO(null)).isNull();
        assertThat(mapper.toAnswerDTO(null)).isNull();
        assertThat(mapper.toGuideDTOList(null)).isNull();
        assertThat(mapper.toQuestionDTOList(null)).isNull();
        assertThat(mapper.toAnswerDTOList(null)).isNull();
    }
} 