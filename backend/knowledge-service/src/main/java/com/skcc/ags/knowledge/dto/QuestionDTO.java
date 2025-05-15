package com.skcc.ags.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skcc.ags.knowledge.domain.QuestionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    private Long id;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;

    private Long viewCount;

    @NotNull(message = "카테고리는 필수 입력값입니다.")
    private QuestionCategory category;

    @Valid
    @Builder.Default
    private List<AnswerDTO> answers = new ArrayList<>();

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void addAnswer(AnswerDTO answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(AnswerDTO answer) {
        this.answers.remove(answer);
    }
} 